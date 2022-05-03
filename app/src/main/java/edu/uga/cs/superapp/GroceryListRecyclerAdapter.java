package edu.uga.cs.superapp;

import static androidx.core.content.ContextCompat.startActivity;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an adapter class for the RecyclerView to show all grocery items.
 */

public class GroceryListRecyclerAdapter extends RecyclerView.Adapter<GroceryListRecyclerAdapter.GroceryListHolder> {

    public static final String DEBUG_TAG = "GrocListRecyclerAdapter";

    List<GroceryItem> groceryList;


    public GroceryListRecyclerAdapter(List<GroceryItem> groceryList) {
        this.groceryList = groceryList;
    }

    @Override
    public GroceryListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_item, parent, false);
        return new GroceryListHolder(view).linkAdapter(this);
    }

    // This method fills in the values of the Views to show a Grocery item
    @Override
    public void onBindViewHolder(GroceryListHolder holder, int position) {
        GroceryItem groceryItem = groceryList.get(position);

        Log.d(DEBUG_TAG, "onBindViewHolder: " + groceryItem);

        holder.itemName.setText(groceryItem.getItemName());
        holder.price.setText(groceryItem.getPrice());
        holder.quantity.setText(groceryItem.getQuantity());
        holder.itemId.setText(groceryItem.getItemId());

    }

    @Override
    public int getItemCount() {
        return groceryList.size();
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    public class GroceryListHolder extends RecyclerView.ViewHolder {

        EditText itemName;
        EditText price;
        EditText quantity;
        TextView itemId;
        String newName;
        String newPrice;
        String newQuan;
        //        TextView comments;
        private GroceryListRecyclerAdapter adapter;

        public GroceryListHolder(View itemView) {
            super(itemView);

            itemName = (EditText) itemView.findViewById(R.id.itemName);
            price = (EditText) itemView.findViewById(R.id.price);
            quantity = (EditText) itemView.findViewById(R.id.quantity);
            itemId = (TextView) itemView.findViewById(R.id.ItemId);

            itemView.findViewById(R.id.EditButton).setOnClickListener(view -> {
                newName = itemName.getText().toString();
                newPrice = price.getText().toString();
                newQuan = quantity.getText().toString();
                edit();
                Context context = view.getContext();
                CharSequence text = "Edited";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            });
            itemView.findViewById(R.id.DeleteButton).setOnClickListener(view -> {
                System.out.println(itemName.getText() + " is being removed.");
                System.out.println(getAdapterPosition()+ " is the adapter position.");
                delete();
                adapter.groceryList.remove(getAdapterPosition());
                adapter.notifyItemRemoved(getAdapterPosition());
                Context context = view.getContext();
                CharSequence text = "Deleted";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            });
            itemView.findViewById(R.id.addToCartButton).setOnClickListener(view -> {
                newPrice = price.getText().toString();
                if (!(newPrice.equals("0.00"))) {
                    adapter.groceryList.remove(getAdapterPosition());
                    adapter.notifyItemRemoved(getAdapterPosition());
                    addToCart();
                    Context context = view.getContext();
                    CharSequence text = "Added to Cart";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    Context context = view.getContext();
                    CharSequence text = "Add Price";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            });

        }
        public GroceryListHolder linkAdapter(GroceryListRecyclerAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        private void addToCart() {
            DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference groRef = FirebaseDatabase.getInstance().getReference("GroceryList");
            GroceryItem groceryItem = new GroceryItem( itemName.getText().toString(), newPrice,
                    quantity.getText().toString(), itemId.getText().toString());

            cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot ) {
                    for( DataSnapshot snap: dataSnapshot.getChildren() ) {
                        cartRef.child("Cart").child(itemId.getText().toString()).setValue(groceryItem);
                        groRef.child(itemId.getText().toString()).removeValue();
                        notifyDataSetChanged();
                        Log.d( DEBUG_TAG, "ReviewGroceryListActivity.onCreate(): edit: " + snap.getRef().toString() );
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        private void delete() {
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("GroceryList");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot ) {
                    // remove the value at reference
                    for( DataSnapshot snap: dataSnapshot.getChildren() ) {
                        myRef.child(itemId.getText().toString()).removeValue();
                        notifyDataSetChanged();
                        Log.d( DEBUG_TAG, "ReviewGroceryListActivity.onCreate(): added: " + snap.getRef().toString() );
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        private void edit() {
            // creating a variable for our Database
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("GroceryList");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot ) {
                    // remove the value at reference
                    for( DataSnapshot snap: dataSnapshot.getChildren() ) {
                        myRef.child(itemId.getText().toString()).child("itemName").setValue(newName);
                        myRef.child(itemId.getText().toString()).child("price").setValue(newPrice);
                        myRef.child(itemId.getText().toString()).child("quantity").setValue(newQuan);
                        Log.d( DEBUG_TAG, "ReviewGroceryListActivity.onCreate(): edit: " + snap.getRef().toString() );
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }


}








