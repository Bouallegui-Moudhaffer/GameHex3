/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamehex.dao;

import com.gamehex.entity.Product;
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
 *
 * @author Dr.Green
 */
public class SupplierDAO implements Dao<Supplier>{
  PreparedStatement statement;
    private Statement st;
    private ResultSet rs;
    private Connection cnx;

    public SupplierDAO() {
        cnx = MyConnection.getInstance().getCnx();
    }
    @Override
    public void Add(Supplier p) {
        String req = "insert into supplier (name) values ('" + p.getName() + "')";
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
    public void Update(Supplier p) {
         String qry = "UPDATE supplier SET name = '" + p.getName()+ "'";

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
    public void Delete(Supplier p) {
        String req = "delete from supplier where id_supplier=" + p.getId_supplier();
        Supplier p1 = displayById(p);

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
    public ObservableList<Supplier> ViewAll() {
        ObservableList<Supplier> ProductsList = FXCollections.observableArrayList();
        
        
        String req = "select * from supplier";
        ObservableList<Supplier> l2 = FXCollections.observableArrayList();

        try {
            statement = cnx.prepareStatement(req);
            rs = statement.executeQuery(req);
            while (rs.next()) {
                Supplier p = new Supplier();

                p.setId_supplier(rs.getInt("id_supplier"));
                p.setName(rs.getString("name"));
               
                l2.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l2;
    }

    @Override
    public Supplier displayById(Supplier p) {
        String req = "select * from supplier where id_supplier=" + p.getId_supplier();
        Supplier p1 = new Supplier();
        try {
            statement = cnx.prepareStatement(req);
            rs = statement.executeQuery(req);
            // while(rs.next()){
            rs.next();
            p1.setId_supplier(rs.getInt("id_supplier"));
            p1.setName(rs.getString("name"));
            System.out.println(p1);
//}  
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    @Override
    public Supplier displayByName(String name) {
        String req = "select * from supplier where name ='" + name +"'";
        Supplier p1 = new Supplier();
        try {
            statement = cnx.prepareStatement(req);
            rs = statement.executeQuery(req);
            // while(rs.next()){
            rs.next();
            p1.setId_supplier(rs.getInt("id_supplier"));
            p1.setName(rs.getString("name"));
            System.out.println(p1);
//}  
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p1;
        }
    }
    

