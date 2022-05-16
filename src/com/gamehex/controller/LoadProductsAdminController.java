/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamehex.controller;

import com.gamehex.dao.ProductDAO;
import com.gamehex.entity.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Dr.Green
 */
public class LoadProductsAdminController implements Initializable {

    /**
     * Initializes the controller class.
     */
        @FXML
    private TableView<?> tvProducts;
        
     @FXML
    private TextField filterText;

    @FXML
    private Button exportToPdf;

    @FXML
    private Button exportToExcel;

    @FXML
    private TextField name;

    @FXML
    private TextField description;

    @FXML
    private TextField state;

    @FXML
    private TextField review;

    @FXML
    private TextField price;

    @FXML
    private TextField supplierID;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colState;

    @FXML
    private TableColumn<?, ?> colPrice;
  @FXML
    private TextField keywordTextField;
  
@FXML
    private TextField ID;  
  @FXML
    private TableColumn<?, ?> colReview;
        ProductDAO p1 = new ProductDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fillTable();
    }    
    
    public void fillTable() {

        //ComboBox<Product> topMenu = new ComboBox<Product>(FXCollections.observableList(p1.ViewAll()));
        ObservableList list = p1.ViewAll();
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
                colState.setCellValueFactory(new PropertyValueFactory<>("state"));

        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colReview.setCellValueFactory(new PropertyValueFactory<>("review"));
        tvProducts.setItems(list);
        //System.out.println("list");
        System.out.println(list);
        
        FilteredList<Product> filteredData = new FilteredList<>(list,b-> true);
        keywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Product productItem) -> {
            
            if(newValue.isEmpty()){
                return true;
            }
            
            String searchKeyboard = newValue.toLowerCase();
            if(productItem.getName().toLowerCase().indexOf(searchKeyboard)> -1){
                return true;
            } else if (productItem.getDescription().toLowerCase().indexOf(searchKeyboard) > -1) {
                    return true;
                }
            
            return false;
            }); 
        });
        
        SortedList sortedData  = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvProducts.comparatorProperty());
        tvProducts.setItems(sortedData);
    }
    
    public Product ReplaceObj() {
        Product prod1 = new Product();
        prod1.setName(name.getText());
        prod1.setDescription(description.getText());
        prod1.setPrice(Integer.parseInt(price.getText()));
        prod1.setReview(Integer.parseInt(review.getText()));
        prod1.setRef(11);

        return prod1;
    }

    public Product ReplaceObj2() {
        Product prod1 = new Product();
        prod1.setId(Integer.parseInt(ID.getText()));
        prod1.setName(name.getText());
        prod1.setDescription(description.getText());
        prod1.setPrice(Integer.parseInt(price.getText()));
        prod1.setReview(Integer.parseInt(review.getText()));
        prod1.setState(state.getText());

        prod1.setRef(11);

        return prod1;
    }
    
    public Product ReplaceObj3() {
        Product prod1 = new Product();
        prod1.setId(Integer.parseInt(ID.getText()));
        prod1.setName("aa");
        prod1.setDescription("aa");
        prod1.setPrice(Integer.parseInt("44"));
        prod1.setReview(Integer.parseInt("5"));
        prod1.setRef(11);

        return prod1;
    }
    
    @FXML
    void DeleteProduct(MouseEvent event) {
p1.Delete(ReplaceObj3());
        fillTable();
    }

    @FXML
    void InsertProduct(MouseEvent event) {
 p1.Add(ReplaceObj());
        fillTable();
    }

    @FXML
    void UpdateProduct(MouseEvent event) {
 p1.Update(ReplaceObj2());
        fillTable();
    }
    
}
