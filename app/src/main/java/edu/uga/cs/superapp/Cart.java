package edu.uga.cs.superapp;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    public static final String TAG = "CartClass";

    private static ArrayList<GroceryItem> cart;
    private List<GroceryItem> groceryList;


    public Cart() {
        cart = new ArrayList<GroceryItem>();
    }

    public void addToCart(GroceryItem item) {
        cart.add(item);
    }

    public void clearCart() {
        cart.clear();
    }

    public void purchaseCart() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("GroceryList");

        groceryList = new ArrayList<GroceryItem>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Once we have a DataSnapshot object, knowing that this is a list,
                // we need to iterate over the elements and place them on a List.
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    GroceryItem groceryItem = postSnapshot.getValue(GroceryItem.class);
                    groceryList.add(groceryItem);
                    Log.d(TAG, "Cart Class: added: " + groceryItem);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

        for (int i = 0; i < cart.size(); i++) { //Todo: I think this number is right because its num of items but be sure
            GroceryItem cartItem = cart.get(i);
            for (int c = 0; c < groceryList.size(); c++) {
                GroceryItem groceryItem = groceryList.get(c); //check if theyre equal and then get the total and fake add it to the persons total
                if (cartItem.getItemName().equals(groceryItem.getItemName())) {
                    groceryList.remove(c);
                    double totalPrice = Double.parseDouble(groceryItem.getPrice()) * Integer.parseInt(groceryItem.getQuantity()); //Todo: be sure of this too
                    //Todo: add the total to the users purchased total
                    break;
                }
            }
        }
        cart.clear();
    }


    public double priceOfCart() {
        double total = 0;
        for (int i = 0; i < cart.size(); i++) {
            GroceryItem groceryItem = cart.get(i);
            total += (Double.parseDouble(groceryItem.getPrice()) * Integer.parseInt(groceryItem.getQuantity())); //Todo: be sure of this too
        }
        return total;
    }

    public static ArrayList<GroceryItem> getCart() {
        return cart;
    }





}
