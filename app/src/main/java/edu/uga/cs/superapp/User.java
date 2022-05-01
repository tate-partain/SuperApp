package edu.uga.cs.superapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This class represents a single user of the app, including the person's name,
 * and amount paid.
 */


public class User {
    private static String id;
    private static double amount;
    private static String email;

    public User() {
    }

    public User(String id, String email, double amountPaid) {
        this.id = id;
        this.amount = amountPaid;
        this.email = email;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amountPaid) { this.amount = amountPaid; } //for data snapshot. Not for actually setting the amount paid

    public void addAmount(double amountPaid) { //basically this is the set amount but it adds it to the total
        this.amount += amountPaid;
        //write to database updating the users amount variable
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        User user = new User(id, email, amount);
        myRef.child(id).setValue(user);
    }

    public void resetAmount(String uid) {
        amount = 0;
        //write to database updating the users amount variable
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        User user = new User(id, email, amount);
        myRef.child(id).setValue(user);
    }

    public void setId(String id) {this.id = id; }

    public String getId() { return id; }

    public void setEmail(String email) { this.email = email; }

    public String getEmail() { return email; }

    public String toString() {
        return id + " value: " + amount;
    }

}

