/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamehex.controller;

import com.gamehex.dao.ProductDAO;
import com.gamehex.dao.SupplierDAO;
import com.gamehex.entity.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import com.gamehex.entity.Supplier;
import com.gamehex.utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * FXML Controller class
 *
 * @author Dr.Green
 */
public class SupplierController implements Initializable {

    
      @FXML
    private TextField filterText;

    @FXML
    private Button exportToPdf;

    @FXML
    private Button exportToExcel;

    @FXML
    private TextField name;

    @FXML
    private TableView<?> tvsuppliers;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TextField keywordTextField;

    @FXML
    private TextField ID;
            SupplierDAO p1 = new SupplierDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 
        fillTable();
    }    
    
    
    public void fillTable() {

        //ComboBox<Product> topMenu = new ComboBox<Product>(FXCollections.observableList(p1.ViewAll()));
        ObservableList list = p1.ViewAll();
        colID.setCellValueFactory(new PropertyValueFactory<>("id_supplier"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));               

        tvsuppliers.setItems(list);
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
        sortedData.comparatorProperty().bind(tvsuppliers.comparatorProperty());
        tvsuppliers.setItems(sortedData);System.out.println(list);
    }
    
    
    public Supplier ReplaceObj() {
        Supplier prod1 = new Supplier();
        prod1.setName(name.getText());

        return prod1;
    }

    public Supplier ReplaceObj2() {
        Supplier prod1 = new Supplier();
        prod1.setId_supplier(Integer.parseInt(ID.getText()));
        prod1.setName(name.getText());

        return prod1;
    }
    
    public Supplier ReplaceObj3() {
        Supplier prod1 = new Supplier();
        prod1.setId_supplier(Integer.parseInt(ID.getText()));
        prod1.setName("aa");

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
