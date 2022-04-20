package edu.uga.cs.superapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * This class represents a single job lead, including the company name,
 * phone number, URL, and some comments.
 */


public class User {
    private String userName;
    private double amountPaid;

    public User() {
        this.userName = "";
        this.amountPaid = 0;
    }

    public User(String userName, double amountPaid) {
        this.userName = userName;
        this.amountPaid = amountPaid;
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

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String toString() {
        return userName + " " + amountPaid;
    }

}

