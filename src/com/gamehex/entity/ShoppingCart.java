


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamehex.entity;

import com.gamehex.dao.ProductDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dr.Green
 */
public class ShoppingCart {
    ProductDAO p1 = new ProductDAO();

    private Map<String, CartEntry> entries;
    
    public static ShoppingCart INSTANCE;
    public static ShoppingCart getInstance(){
        if(INSTANCE == null){
            INSTANCE  = new ShoppingCart();
        }
        return INSTANCE;
    }
    
    private ShoppingCart() {
        this.entries = new HashMap<>();
    }

    public void addProduct(String productName) {
        CartEntry productEntry = entries.get(productName.toUpperCase());
        if (productEntry != null){
            productEntry.increaseQtn();
        } else {
            //Product product1 = new Product();
            //product1.setName(productName);
            Product prod1 = new Product();
            prod1 = p1.displayByName(productName);
            CartEntry entry = new CartEntry(prod1, 1);
            entries.put(productName.toUpperCase(), entry);
        }
    }

    public List<CartEntry> getEntries() {
        return new ArrayList<>(entries.values());
    }
    
    
    public void removeProduct(String productName){
        CartEntry productEntry = entries.get(productName.toUpperCase());
        if(productEntry!=null) {
            productEntry.decreaseQtn();
        }
    }
    
    public int getQuantity(String productName){
        CartEntry entry = entries.get(productName.toUpperCase());
        if(entry != null){
            return entry.getQuantity();
        }
        return 0;
    }
    
    public float calculateTotal(){
        float total = 0;
        for (CartEntry entry:entries.values()){
            float entryCost = entry.getProduct().getPrice()*entry.getQuantity();
            total = total + entryCost;
        }
        return total;
    }
    
}
