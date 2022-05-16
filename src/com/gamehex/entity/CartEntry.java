/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamehex.entity;

/**
 *
 * @author Dr.Green
 */
public class CartEntry {

    private Product product;
    private int quantity;
    private int id_cart;

    public CartEntry(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    
    public CartEntry(){
        
    }
    
    public void increaseQtn(){
        this.quantity++;
    }
    
    public void decreaseQtn(){
        
        if(this.quantity > 0) {
        this.quantity--;    
        }
        
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getId_cart() {
        return id_cart;
    }

    public void setId_cart(int id_cart) {
        this.id_cart = id_cart;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartEntry{" + "product=" + product + ", quantity=" + quantity + ", id_cart=" + id_cart + '}';
    }
    
    
    
    
    
    

}
