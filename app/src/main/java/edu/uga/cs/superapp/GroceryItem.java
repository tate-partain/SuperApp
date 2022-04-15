package edu.uga.cs.superapp;

/**
 * This class represents a single job lead, including the company name,
 * phone number, URL, and some comments.
 */

public class GroceryItem {
    private String itemName;
    private String price;
//    might want a "purchased" boolean or "purchasedBy" String
//    private String url;
//    private String comments;

    public GroceryItem()
    {
        this.itemName = null;
        this.price = null;
//        this.url = null;
//        this.comments = null;
    }

    public GroceryItem( String itemName, String price ) {
        this.itemName = itemName;
        this.price = price;
//        this.url = url;
//        this.comments = comments;
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

//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public String getComments() {
//        return comments;
//    }
//
//    public void setComments(String comments) {
//        this.comments = comments;
//    }

    public String toString() {
        return itemName + " " + price;
    }
}
