/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamehex.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Moudhaffer
 */
public class coachingSession {
    private SimpleIntegerProperty sessionId;
    private LocalDate start;
    private SimpleStringProperty coachAttendee;
    private SimpleStringProperty playerAttendee;
   

    public coachingSession(int sessionId, LocalDate start, String coachAttendee, String playerAttendee) {
        this.sessionId = new SimpleIntegerProperty(sessionId);
        this.start = start;
        this.coachAttendee = new SimpleStringProperty(coachAttendee);
        this.playerAttendee = new SimpleStringProperty(playerAttendee);
    }
    
    public coachingSession() {
        
    }

    public int getSessionId() {
        return sessionId.get();
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public void setSessionId(SimpleIntegerProperty sessionId) {
        this.sessionId = sessionId;
    }

    public String getCoachAttendee() {
        return coachAttendee.get();
    }

    public void setCoachAttendee(SimpleStringProperty coachAttendee) {
        this.coachAttendee = coachAttendee;
    }

    public String getPlayerAttendee() {
        return playerAttendee.get();
    }

    public void setPlayerAttendee(SimpleStringProperty playerAttendee) {
        this.playerAttendee = playerAttendee;
    }
    
}
