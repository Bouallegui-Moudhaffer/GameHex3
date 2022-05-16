/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamehex.controller;

import com.gamehex.dao.ProductDAO;
import com.gamehex.entity.Product;
import com.gamehex.utils.MyConnection;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.text.Font;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gamehex.entity.Product;
import com.gamehex.entity.ShoppingCart;
import com.gamehex.utils.MyConnection;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dr.Green
 */
public class LoadProductsController implements Initializable {
@FXML
    private GridPane productGrid2;

    @FXML
    private Button btnFilter;
    @FXML
    private TextField filterText;

     @FXML
    private Button exportToPdf;

    @FXML
    private Button exportToExcel;
    
    private List<Product> PList;
    ProductDAO p1 = new ProductDAO();
    /**
     * Initializes the controller class.
     */
    
            private Connection cnx;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //exportToExcel = new Button ('Export to excel'); 
        cnx = MyConnection.getInstance().getCnx();

        exportToExcel.setOnAction((actionEvent -> {
            exportToExcel.setFont(Font.font("Sansserif", 15));
            
            ObservableList<Product> hotelList = FXCollections.observableArrayList();
            Product hotel = new Product();
            String query = "select * from product ";
            try {

                Statement pst = cnx.createStatement();
                
                
                
                ResultSet rs = pst.executeQuery(query);

                //Apache POI Jar Link
                //https://www.apache.org/dyn/closer.lua/poi/release/bin/poi-bin-5.2.0-20220106.tgz
                XSSFWorkbook wb = new XSSFWorkbook();
                XSSFSheet sheet = wb.createSheet("Product Details");
                XSSFRow header = sheet.createRow(0);
                header.createCell(0).setCellValue("Product ID");
                header.createCell(1).setCellValue("Product Name");
                header.createCell(2).setCellValue("Product ref");
                header.createCell(3).setCellValue("Product description");
                header.createCell(4).setCellValue("Product price");
                header.createCell(5).setCellValue("Product review");

                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.setColumnWidth(3, 256 * 25);
                sheet.setColumnWidth(4, 256 * 25);
                sheet.setColumnWidth(5, 256 * 25);//256-character width 

                sheet.setZoom(150); //scale(150%

                int index = 1;
                while (rs.next()) {
                    XSSFRow row = sheet.createRow(index);
                    row.createCell(0).setCellValue(rs.getString("id"));
                    row.createCell(1).setCellValue(rs.getString("name"));
                    row.createCell(2).setCellValue(rs.getString("ref"));
                    row.createCell(3).setCellValue(rs.getString("description"));
                    row.createCell(4).setCellValue(rs.getString("price"));
                    row.createCell(5).setCellValue(rs.getString("review"));

                    index++;

                }

                String file_name = "ProductDetails.xlsx";

                FileOutputStream fileOut = new FileOutputStream(file_name);
                wb.write(fileOut);
                fileOut.close();

                } catch (SQLException ex) {
                    Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Product details exported to excel Sheet");
                alert.show();
                //pst.close();
                //rs.close();

            }));
        
   
     exportToPdf.setOnAction(actionEvent -> {
        cnx = MyConnection.getInstance().getCnx();
    
        PreparedStatement pst=null;
        ResultSet rs=null;
         
        String guery = " select * from product";
            try {   
               
        pst= cnx.prepareStatement(guery);
        rs= pst.executeQuery();
         
        String file_name = "ProductDetails.pdf";
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(file_name));
        
        doc.open();
       
       com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance("A:\\JavaIntegration\\GameHexPush\\src\\com\\gamehex\\assets\\Amine.jpg");
       img.scaleAbsoluteWidth(600);
       img.scaleAbsoluteHeight(92);
       img.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
       doc.add(img);
       
       doc.add(new Paragraph(" "));
       doc.add(new Paragraph("Products list",FontFactory.getFont(FontFactory.TIMES_BOLD,20,BaseColor.LIGHT_GRAY)));
       doc.add(new Paragraph(" "));

       PdfPTable table = new PdfPTable(3);
       table.setWidthPercentage(100);
       PdfPCell cell;
       cell = new PdfPCell (new Phrase("name", FontFactory.getFont("Comic Sans MS",12)));
       cell.setHorizontalAlignment(Element.ALIGN_CENTER);
       cell.setBackgroundColor(BaseColor.GRAY);
       table.addCell(cell);
       
       cell = new PdfPCell (new Phrase("Phone", FontFactory.getFont("Comic Sans MS",12)));
       cell.setHorizontalAlignment(Element.ALIGN_CENTER);
       cell.setBackgroundColor(BaseColor.GRAY);
       table.addCell(cell);
       
       cell = new PdfPCell (new Phrase("Location", FontFactory.getFont("Comic Sans MS",12)));
       cell.setHorizontalAlignment(Element.ALIGN_CENTER);
       cell.setBackgroundColor(BaseColor.GRAY);
       table.addCell(cell);
       
       cell = new PdfPCell (new Phrase("Phone", FontFactory.getFont("Comic Sans MS",12)));
       cell.setHorizontalAlignment(Element.ALIGN_CENTER);
       cell.setBackgroundColor(BaseColor.GRAY);
       
       /////////////////////////////////////////////////////////////////////////////////
       while(rs.next()) {
           cell = new PdfPCell (new Phrase(rs.getString("id").toString(), FontFactory.getFont("Arial",12)));
       cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        
       table.addCell(cell);
       
       cell = new PdfPCell (new Phrase(rs.getString("name").toString(), FontFactory.getFont("Arial",12)));
       cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      
       table.addCell(cell);
       
       cell = new PdfPCell (new Phrase(rs.getString("ref").toString(), FontFactory.getFont("Arial",12)));
       cell.setHorizontalAlignment(Element.ALIGN_CENTER);
       
       table.addCell(cell);
       
       cell = new PdfPCell (new Phrase(rs.getString("description").toString(), FontFactory.getFont("Arial",12)));
       cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      
       table.addCell(cell);
        
       }
      
       doc.add(table);
       
        System.out.println("done");
                doc.close();
                
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Product details exported to PDF Sheet");
            alert.show();

         });
    
        populateWithcart();
    }    
    
    
    private VBox productView(Product p){
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        Label prodname = new Label(p.getName());
        Label pricfe = new Label(Integer.toString(p.getPrice()));
        Button addButton = new Button("Add to cart");
        addButton.setUserData(p.getName());
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Node sourceComponent = (Node)event.getSource();
                String productName = (String)sourceComponent.getUserData();
                
                System.out.println(productName);
                ShoppingCart scart = ShoppingCart.getInstance();
                scart.addProduct(productName);
                
            }
        });
        layout.getChildren().addAll(prodname,pricfe,addButton);
        return layout;
    }

    public void Clickbut() {
        int columns = 0;
        int rows = 1;
        PList = new ArrayList(p1.ViewAll());
        System.out.println(PList.get(1));

        try {
            for (int i = 0; i < PList.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Product.fxml"));
                VBox productBox = fxmlLoader.load();
                PList.get(i);
                System.out.println(PList.get(i).getName());
                //setData(PList.get(i));
                if (columns == 3) {
                    columns = 0;
                    ++rows;
                }
                productGrid2.add(productBox, columns++, rows);
                GridPane.setMargin(productBox, new Insets(3));
            }

        } catch (IOException ex) {
        }
    }

    @FXML
    void FilterList(MouseEvent event) {
        /*ObservableList list = p1.ViewAll();
        FilteredList<Product> filteredData = new FilteredList<>(list, (Product p) -> p.getName().equalsIgnoreCase("hello"));
        System.out.println("filtered");
        PList = filteredData;
        System.out.println(filteredData);*/

        productGrid2.getChildren().clear();
        int columns = 0;
        int rows = 1;
        PList = new ArrayList(p1.ViewAll());
        System.out.println(PList.get(1));

            for (int i = 0; i < PList.size(); i++) {
                if (PList.get(i).getPrice() <= Integer.valueOf(filterText.getText())) {

                   VBox pd1 = productView(PList.get(i));
                   productGrid2.add(pd1, i, 50);
                }
            }
        
        

    }
    
    public void populateWithcart(){
        int columns = 0;
        int rows = 1;
        PList = new ArrayList(p1.ViewAll());
        System.out.println(PList);
        PList = new ArrayList(p1.ViewAll());
        System.out.println(PList.get(1));

            for (int i = 0; i < PList.size(); i++) {
                   VBox pd1 = productView(PList.get(i));
                   productGrid2.add(pd1, i, 50);
            }

        }
    
    
    @FXML
    void GoToShop(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent = FXMLLoader.load(getClass().getResource("/com/gamehex/view/ShopHomepage.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setTitle("Home");
                //stage.getIcons().add(new Image("com/gamehex/assets/NotePad.png"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
    }
    
    

       
}
