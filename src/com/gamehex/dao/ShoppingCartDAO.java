/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamehex.dao;

import com.gamehex.utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Dr.Green
 */
public class ShoppingCartDAO {
      PreparedStatement statement;
    private Statement st;
    private ResultSet rs;
    private Connection cnx;

    public ShoppingCartDAO() {
        cnx = MyConnection.getInstance().getCnx();
    }
    
    
}
