/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamehex.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.gamehex.entity.Team_mates;
import com.gamehex.utils.MyConnection;

/**
 *
 * @author Yasmine Daly
 */
public class GetPlayerByMail {

    public static final String ACCOUNT_SID = "AC90ac083ebfd6f6485124fb25d08fbbb0";
    public static final String AUTH_TOKEN = "9b9845a946c52aee9b451be913eb03b0";

    public Integer GetuserBytel(String email) {
        int number = 0;
        try {
            Team_mates captain = null;

            String requete = "Select member_phone from team_mates where member_mail = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, email);
            ResultSet rs;
            rs = pst.executeQuery();
            if (rs.next()) {
                number = rs.getInt("member_phone");
                captain = new Team_mates(
                        rs.getInt("member_phone"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return number;
    }

}
