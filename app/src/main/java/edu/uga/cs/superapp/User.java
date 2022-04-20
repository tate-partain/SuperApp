package edu.uga.cs.superapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * This class represents a single user of the app, including the person's name,
 * and amount paid.
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

    public void addAmountPaid(double amountPaid) {
        this.amountPaid += amountPaid;
    }

    public void settledAmountPaid() {
        amountPaid = 0;
    }

    public String toString() {
        return userName + " " + amountPaid;
    }

}

