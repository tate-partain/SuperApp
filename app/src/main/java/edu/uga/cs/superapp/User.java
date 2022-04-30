package edu.uga.cs.superapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * This class represents a single user of the app, including the person's name,
 * and amount paid.
 */


public class User {
    private static String id;
    private static double amount;

    public User() {
        id = null;
        amount = 0;
    }

    public User(String id, double amountPaid) {
        this.id = id;
        this.amount = amountPaid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amountPaid) { this.amount = amountPaid; }

    public void addAmount(double amountPaid) {
        this.amount += amountPaid;
    }

    public void resetAmount() { amount = 0; }

    public void setId(String id) {this.id = id; }

    public String getId() { return id; }

    public String toString() {
        return id + " value: " + amount;
    }

}

