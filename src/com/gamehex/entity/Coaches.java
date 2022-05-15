/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamehex.entity;

import java.util.Objects;

/**
 *
 * @author Moudhaffer
 */
public class Coaches {
    private int coachId;
    private double rating; 
    private int userID;

    public Coaches() {
    }
    
    

    public Coaches(int coachId, double rating, User user) {
        this.coachId = coachId;
        this.rating = rating;

    }

    public Coaches(int coachId, double rating) {
        this.coachId = coachId;
        this.rating = rating;
    }
    

    public int getCoachId() {
        return coachId;
    }

    public double getRating() {
        return rating;
    }

   
    public void setCoachId(int coachId) {
        this.coachId = coachId;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Coaches{" + "coachId=" + coachId + ", rating=" + rating + '}';
    }

    

   

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.coachId;
        hash = 83 * hash + Objects.hashCode(this.rating);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coaches other = (Coaches) obj;
        if (this.coachId != other.coachId) {
            return false;
        }
        if (!Objects.equals(this.rating, other.rating)) {
            return false;
        }
        
        return true;
    }

    
    
}
