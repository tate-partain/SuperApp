package edu.uga.cs.superapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * This class represents a single user of the app, including the person's name,
 * and amount paid.
 */


public class User {
    private static User user = null;
    private String userName;
    private String uid;
    private double amountPaid;

    private User() {
        this.userName = "";
        this.uid = "";
        this.amountPaid = 0;
    }

    private User(String uid, String userName, double amountPaid) {
        this.userName = userName;
        this.amountPaid = amountPaid;
    }

    public static User getInstance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public static User getInstance(String uid, String userName) {
        if (user == null) {
            user = new User(uid, userName, 0);
        }
        return user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void addAmountPaid(double amountPaid) {
        this.amountPaid += amountPaid;
    }

    public void settledAmountPaid() { amountPaid = 0; }

    public void setUid(String uid) {this.uid = uid; }

    public String getUid() { return uid; }

    public String toString() {
        return userName + " " + amountPaid;
    }

}

