/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamehex.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.gamehex.entity.Coaches;
import com.gamehex.entity.Session;
import com.gamehex.entity.User;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.gamehex.utils.MyConnection;
import com.jfoenix.controls.JFXComboBox;
import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Queue;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.Timer;
import org.controlsfx.control.Notifications;

/**
 *
 * @passwd Moudhaffer
 */
public class Coach_UIController implements Initializable {

    static int seconds = 10;

    @FXML
    private Button btn_insert;
    @FXML
    private Button btn_delete;

    //Singleton connection
    Connection cnx;
    @FXML
    private Label label;
    @FXML
    private JFXTextField keywordTextField;
    @FXML
    private Label label_warning;
    @FXML
    private JFXButton btn_back;
    @FXML
    private Label id_content;
    @FXML
    private Label username_content;
    @FXML
    private Label lastName_content;
    @FXML
    private Label type_content;
    @FXML
    private JFXTextField txt_summoner;
    @FXML
    private JFXComboBox<String> jfxcb_region;
    @FXML
    private JFXTextField txt_motto;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btn_insert) {
            if (validateMotto()) {
                if (jfxcb_region.getSelectionModel().isEmpty()) {
                    new animatefx.animation.Shake(jfxcb_region).play();
                } else {
                    jfxcb_region.setStyle(null);
                    if (insertCoach()) {
                        id_content.setVisible(true);
                        username_content.setVisible(true);
                        lastName_content.setVisible(true);
                        type_content.setVisible(true);
                        Stage stage = new Stage();
                        Parent root;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/com/gamehex/view/rateMyApp.fxml"));
                            Scene scene = new Scene(root);
                            stage.setTitle("Rating");
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(Coach_UIController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        if (event.getSource() == btn_delete) {
            deleteCoach();
        }
    }

    User user = Session.StartSession().getSessionUser();
    int UID = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillJFXComboBox();
        id_content.setVisible(false);
        username_content.setVisible(false);
        lastName_content.setVisible(false);
        type_content.setVisible(false);
    }

    //Getters & Setters
    public Connection getCnx() {
        return cnx;
    }

    public void setCnx(Connection cnx) {
        this.cnx = cnx;
    }

    public Coach_UIController() {
        this.UID = user.getUserID();
        System.out.println("userId:" + this.user.getUserID());
        cnx = MyConnection.getInstance().getCnx();
    }

    public User getUserById(int id) {
        ObservableList<Coaches> coachList = FXCollections.observableArrayList();
        String query = "SELECT * FROM user where id=" + id + "";

        User user = new User();
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                user.setUserID(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setCIN(rs.getInt("CIN"));
                user.setPhone(rs.getInt("phone"));
                user.setDate(rs.getString("date"));
                user.setEmail(rs.getString("email"));
                user.setPwd(cryptWithMD5(rs.getString("pwd")));
            }
        } catch (SQLException ex) {
        }
        return user;
    }

    private static MessageDigest md;

    public static String cryptWithMD5(String pass) {
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] passBytes = pass.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digested.length; i++) {
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
        }
        return null;
    }

    public void showCoaches() {

        //ObservableList<Coaches> list = getCoachList();
        //Setting ID field value
        id_content.setText("" + this.user.getUserID());
        id_content.setStyle("-fx-background-color:#273B56;");
        //Setting Username field value
        username_content.setText("" + this.user.getName());
        username_content.setStyle("-fx-background-color:#273B56;");
        //Setting Email field value
        lastName_content.setText("" + this.user.getLastName());
        lastName_content.setStyle("-fx-background-color:#273B56;");
        //Setting profileInfo field
        type_content.setText("" + getTier());
        type_content.setStyle("-fx-background-color:#273B56;");
    }

    public boolean insertCoach() {

        String query = "INSERT INTO coach(user_id, rating, tier, image_url, motto) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, this.user.getUserID());
            pst.setInt(2, 0);
            if (!txt_summoner.getText().equals("")) {
                String rank = getTier();
                if (rank.toUpperCase().equals("Diamond".toUpperCase())
                        || rank.toUpperCase().equals("GrandMaster".toUpperCase())
                        || rank.toUpperCase().equals("Master".toUpperCase())
                        || rank.toUpperCase().equals("Challenger".toUpperCase())) {
                    pst.setString(2, rank);
                } else {
                    Notifications.create()
                            .title("Rank Not High Enough")
                            .text("We only accept Coaches with a SoloQ Rank of Diamond or Higher!")
                            .threshold(3, Notifications.create().title("Collapsed Notification"))
                            .showWarning();
                }
            }
            pst.executeUpdate();
            System.out.println("User Added sucessfully");
        } catch (SQLException ex) {
        }
        String request = "UPDATE user SET role = 'COACH' WHERE `id` = ?";
        System.out.println(this.user.getUserID());
        try {
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setInt(1, this.user.getUserID());
            pst.executeUpdate();
            System.out.println("User Added sucessfully");
        } catch (SQLException ex) {
        }
        showCoaches();
        return true;
    }

    public boolean giveRating() {

        String request = "select AVG(rating) from session where coach_id ="
                + " (select id from coach where user_id = ?);";
        Coaches coach = new Coaches();
        try {
            PreparedStatement psst = cnx.prepareCall(request);
            psst.setInt(1, this.user.getUserID());
            ResultSet rs = psst.executeQuery(request);
            while (rs.next()) {
                coach.setRating(rs.getDouble("rating"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(Coach_UIController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        String query = "UPDATE coach SET rating = ? WHERE id = ?";
        try {
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setDouble(1, coach.getRating());
            pst.setInt(2, UID);
            pst.executeUpdate();
            System.out.println("User Added sucessfully");
        } catch (SQLException ex) {
        }
        showCoaches();
        return true;
    }

    private void setStyle(JFXTextField e) {
        new animatefx.animation.Shake(e).play();
        InnerShadow in = new InnerShadow();
        in.setColor(Color.web("#f80000"));
        e.setEffect(in);
    }

    private boolean validateUsername() {
        Pattern p = Pattern.compile("[a-zA-Z0-9_]+");
        Matcher m = p.matcher(txt_summoner.getText());
        if ((txt_summoner.getText().length() != 0) && (m.find() && m.group().equals(txt_summoner.getText()))) {
            txt_summoner.setEffect(null);
            return true;
        } else {
            setStyle(txt_summoner);
            return false;
        }
    }

    private boolean validateMotto() {
        Pattern p = Pattern.compile("[a-zA-Z0-9_]+");
        Matcher m = p.matcher(txt_motto.getText());
        if ((txt_motto.getText().length() != 0)) {
            txt_motto.setEffect(null);
            return true;
        } else {
            setStyle(txt_motto);
            return false;
        }
    }

    public void deleteCoach() {
        String query = "DELETE FROM coach WHERE id=?";
        try {
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, this.user.getUserID());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        showCoaches();
    }

    @FXML
    private void handleBackBtn(ActionEvent event) {
        if (event.getSource() == btn_back) {
            try {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/com/gamehex/view/Home_Screen.fxml"));
                Scene scene = new Scene(root);
                stage.setTitle("Home Screen");
                stage.setScene(scene);
                stage.show();
                Stage stage2 = (Stage) btn_back.getScene().getWindow();
                stage2.close();

            } catch (IOException ex) {
                Logger.getLogger(getSummoner.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getTier() {
        Summoner summ = getSummoner();
        String tier = "";
        try {
            tier = summ.getLeague(Queue.RANKED_SOLO).getTier().toString();
        } catch (NullPointerException ex) {
            tier = "Unranked";
        }
        return tier;
    }

    private Summoner getSummoner() {
        Orianna.setRiotAPIKey("RGAPI-e6be4734-0ccc-4fe4-9792-12b62e112f68");
        Orianna.setDefaultRegion(Region.valueOf(jfxcb_region.getValue()));
        if (validateUsername()) {
            Summoner summoner = Summoner.named(txt_summoner.getText()).get();
            return summoner;
        }
        return null;
    }

    private void fillJFXComboBox() {
        List<String> listRegions = new ArrayList();
        listRegions.add("NORTH_AMERICA");
        listRegions.add("BRAZIL");
        listRegions.add("EUROPE_NORTH_EAST");
        listRegions.add("EUROPE_WEST");
        listRegions.add("JAPAN");
        listRegions.add("KOREA");
        listRegions.add("LATIN_AMERICA_NORTH");
        listRegions.add("LATIN_AMERICA_SOUTH");
        listRegions.add("NORTH_AMERICA");
        listRegions.add("OCEANIA");
        listRegions.add("RUSSIA");
        listRegions.add("TURKEY");
        ObservableList<String> items = FXCollections.observableArrayList(listRegions);
        jfxcb_region.setItems(items);
    }
}
