/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamehex.controller;

import com.gamehex.dao.CartDAO;
import com.gamehex.dao.OrderDAO;
import com.gamehex.dao.ProductDAO;
import com.gamehex.dao.ShoppingCartDAO;
import com.gamehex.entity.CartEntry;
import com.gamehex.entity.ShoppingCart;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Dr.Green
 */
public class CartViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private VBox cartpaneaa;
    @FXML
    private Label TotalpriceLabel;
    ProductDAO p1 = new ProductDAO();
    CartDAO cd1 = new CartDAO();
    OrderDAO oDao1 = new OrderDAO();
    @FXML
    private TextField cartID;

    @FXML
    private TableView<?> tvCart;

    @FXML
    private TableColumn<?, ?> tvID;

    @FXML
    private TableColumn<?, ?> tvTotal;

    List<CartEntry> entries = ShoppingCart.getInstance().getEntries();

    ShoppingCartDAO c1 = new ShoppingCartDAO();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cartpaneaa.getChildren().clear();

        if (entries.isEmpty()) {
            Label emptyLabel = new Label("Empty Cart");
            cartpaneaa.getChildren().add(emptyLabel);
            System.out.println("no elem");
        } else {
            Label shoppingCartTitle = new Label("Shopping Cart");
            cartpaneaa.getChildren().add(shoppingCartTitle);

            for (CartEntry cartEntry : entries) {
                HBox hbox = cartEntryView(cartEntry);
                //Label productName = new Label(cartEntry.getProduct().getName());
                //hbox.getChildren().add(productName);
                cartpaneaa.getChildren().add(hbox);
            }

            Separator sep = new Separator();
            sep.setOrientation(Orientation.HORIZONTAL);
            cartpaneaa.getChildren().add(sep);

            HBox totalview = totalView(ShoppingCart.getInstance().calculateTotal());
            cartpaneaa.getChildren().addAll(totalview);
            System.out.println("elements exist");
        }
    }


private HBox totalView(float totalPrice) {
        HBox layout = new HBox();
        layout.setAlignment(Pos.CENTER);
        Label total = new Label("Total : ");
        total.setStyle("--fx-font-size:15pt;");
        this.TotalpriceLabel = new Label(String.valueOf(totalPrice));
        layout.getChildren().addAll(total, this.TotalpriceLabel);
        return layout;
    }

    private HBox cartEntryView(CartEntry cartEntry) {
        HBox layout = new HBox();
        Label prodName = new Label(cartEntry.getProduct().getName());
        prodName.setPrefWidth(100);
        prodName.setStyle("-fx-padding-5px");
        Label quantity = new Label(String.valueOf(cartEntry.getQuantity()));
        quantity.setPrefWidth(100);
        quantity.setStyle("-fx-padding-5px");

        Button plusBut = new Button("+");
        plusBut.setStyle("-fx-padding-5px");
        plusBut.setUserData(cartEntry.getProduct().getName());
        plusBut.setOnAction(e -> {
            String name = (String) ((Node) e.getSource()).getUserData();
            ShoppingCart.getInstance().addProduct(name);
            quantity.setText(String.valueOf(ShoppingCart.getInstance().getQuantity(name)));
            this.TotalpriceLabel.setText(String.valueOf(ShoppingCart.getInstance().calculateTotal()));
        });

        Button minusBut = new Button("-");
        minusBut.setStyle("-fx-padding-5px");
        minusBut.setUserData(cartEntry.getProduct().getName());
        minusBut.setOnAction(e -> {
            String name = (String) ((Node) e.getSource()).getUserData();
            ShoppingCart.getInstance().removeProduct(name);
            quantity.setText(String.valueOf(ShoppingCart.getInstance().getQuantity(name)));
            this.TotalpriceLabel.setText(String.valueOf(ShoppingCart.getInstance().calculateTotal()));
        });

        Label price = new Label(String.valueOf("$ " + cartEntry.getProduct().getPrice()));
        price.setPrefWidth(100);
        price.setStyle("-fx-padding-5px");

        layout.getChildren().addAll(prodName, quantity, plusBut, minusBut, price);
        return layout;

    }

    public void DisplayItems() {
        int i;
        System.out.println(entries.getClass());

        for (i = 0; i < entries.size(); i++) {
            System.out.println("entries");
            System.out.println(entries.get(i).getProduct().getId());

        }

        if (!entries.isEmpty()) {
            //c1.Add(ShoppingCart.getInstance());
        }
    }
    
    
}
