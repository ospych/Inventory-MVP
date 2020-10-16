package com.example.inventorymvp.Data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "InventoryDB")
public class Inventory {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String supplier;
    private int quantity;
    private double price;
    private String image;

//    public Inventory(String title, String supplier, double price, int quantity) {
//        this.title = title;
//        this.supplier = supplier;
//        this.price = price;
//        this.quantity = quantity;
//    }

    public Inventory(String title, String supplier, double price, int quantity, String image) {
        this.title = title;
        this.supplier = supplier;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSupplier() {
        return supplier;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
