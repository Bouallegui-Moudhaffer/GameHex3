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
public class Product {
     private int id;
    public String name;
    private int ref;
    private String description;
    private int price;
    private int review;
    private String state;
    private int supplier_id;
    
    public  Product(String c, int i, String c0, int i0, int i1, String c1, int i2) {
        this.ref = i;
        this.name = c;
        this.description = c0;
        this.review = i0;
        this.price = i1;
        this.state = c1;
        this.supplier_id = i2;
    }

   public Product(){}
    
   
    
      public void Product(int id, String name, int ref, String description,int price, int review, String state, int supplier_id){
        this.id = id;
        this.ref = ref;
        this.name = name;
        this.description = description;
        this.review = review;
        this.price = price;
        this.state = state;
        this.supplier_id = supplier_id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRef() {
        return ref;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getReview() {
        return review;
    }

    public String getState() {
        return state;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", ref=" + ref + ", description=" + description + ", price=" + price + ", review=" + review + ", state=" + state + ", supplier_id=" + supplier_id + '}';
    }
      
      
}
