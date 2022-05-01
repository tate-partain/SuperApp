package edu.uga.cs.superapp;

import com.google.firebase.database.Exclude;

/**
 * This class represents a single job lead, including the company name,
 * phone number, URL, and some comments.
 */

public class GroceryItem {
    private String itemName;
    private String price;
    private String quantity;
    private String purchasedBy;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    private String itemId;

    public GroceryItem() {
        this.itemName = null;
        this.price = "0.00";
        this.quantity = null;
        this.purchasedBy = null;
        this.itemId = null;
    }

    public GroceryItem( String itemName, String price, String quantity, String itemId ) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() { return quantity; }

    public void setQuantity(String quantity) { this.quantity = quantity; }

    public String getPurchasedBy() { return purchasedBy; }

    public void setPurchasedBy(String purchasedBy) { this.purchasedBy = purchasedBy; }

    public String toString() { return itemName + " " + price + " " + quantity; }
}
