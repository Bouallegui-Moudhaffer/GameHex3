/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamehex.dao;

import com.gamehex.entity.Product;
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
 *
 * @author Dr.Green
 */
public class ProductDAO implements Dao<Product> {
    
    PreparedStatement statement;
    private Statement st;
    private ResultSet rs;
    private Connection cnx;

    public ProductDAO() {
        cnx = MyConnection.getInstance().getCnx();
    }

    @Override
    public void Add(Product p) {
        String req = "insert into product (ref,name,description,price,review,state,supplier_id) values ('" + p.getRef()+ "','" + p.getName() + "','" + p.getDescription() + "','" + p.getPrice() + "','" + p.getReview() + "','" + p.getState() + "','" + p.getSupplier_id() + "')";
        try {
            //st.executeUpdate(req);
            statement = cnx.prepareStatement(req);
            statement.executeUpdate();
            System.out.println("Item added");
        } catch (SQLException ex) {
            System.out.println("not working");
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void Update(Product p) {
        String qry = "UPDATE product SET ref = '" + p.getRef()+ "', name = '" + p.getName()+  "', description = '" + p.getDescription() + "',price = '" + p.getPrice() + "',review = '" + p.getReview() + "',state = '" + p.getState() + "',supplier_id = '" + p.getSupplier_id() + "' WHERE id = " + p.getId();

        try {
            statement = cnx.prepareStatement(qry);

            if (statement.executeUpdate(qry) > 0) {
                statement.executeUpdate();
                System.out.println("updated");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("update Failed");

        }
    }

    @Override
    public void Delete(Product p) {
        String req = "delete from product where id=" + p.getId();
        Product p1 = displayById(p);

        if (p != null) {
            try {
                statement = cnx.prepareStatement(req);
                statement.executeUpdate(req);

            } catch (SQLException ex) {
                Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("n'existe pas");
        }
    }

    @Override
    public ObservableList<Product> ViewAll() {
        ObservableList<Product> ProductsList = FXCollections.observableArrayList();
        
        
        String req = "select * from product";
        ObservableList<Product> l2 = FXCollections.observableArrayList();

        try {
            statement = cnx.prepareStatement(req);
            rs = statement.executeQuery(req);
            while (rs.next()) {
                Product p = new Product();

                p.setId(rs.getInt(1));
                p.setRef(rs.getInt("ref"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setPrice(rs.getInt("price"));
                p.setReview(rs.getInt("review"));
                p.setState(rs.getString("state"));
                p.setSupplier_id(rs.getInt("supplier_id"));

                l2.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l2;
        
        
    }

    //DONE BUT THE ID NEEDS TO BE DYNAMIC
    @Override
    public Product displayById(Product p) {
        String req = "select * from product where id=" + p.getId();
        Product p1 = new Product();
        try {
            statement = cnx.prepareStatement(req);
            rs = statement.executeQuery(req);
            // while(rs.next()){
            rs.next();
            p1.setId(rs.getInt("id"));
            p1.setRef(rs.getInt("ref"));
            p1.setName(rs.getString("name"));
            p1.setDescription(rs.getString("description"));
            p1.setPrice(rs.getInt("price"));
            p1.setReview(rs.getInt("review"));
            p1.setState(rs.getString("state"));
            p1.setSupplier_id(rs.getInt("supplier_id"));
            System.out.println(p1);
//}  
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    @Override
    public Product displayByName(String name) {
        String req = "select * from product where name ='" + name +"'";
        Product p1 = new Product();
        try {
            statement = cnx.prepareStatement(req);
            rs = statement.executeQuery(req);
            // while(rs.next()){
            rs.next();
            p1.setId(rs.getInt("id"));
            p1.setRef(rs.getInt("ref"));
            p1.setName(rs.getString("name"));
            p1.setDescription(rs.getString("description"));
            p1.setPrice(rs.getInt("price"));
            p1.setReview(rs.getInt("review"));
            p1.setState(rs.getString("state"));
            p1.setSupplier_id(rs.getInt("supplier_id"));
            System.out.println(p1);
//}  
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p1;
        }
    
    
    
}
