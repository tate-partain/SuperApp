package edu.uga.cs.superapp;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    }

    @Override
    public int getItemCount() {
        return groceryList.size();
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    public class GroceryListHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView price;
        TextView quantity;
        Button editButton;
        Button deleteButton;
        Button addToCart;
        //        TextView comments;
        private GroceryListRecyclerAdapter adapter;

        public GroceryListHolder(View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.itemName);
            price = (TextView) itemView.findViewById(R.id.price);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            itemView.findViewById(R.id.EditButton).setOnClickListener(view -> {
                System.out.println(getItemCount());
            });
            itemView.findViewById(R.id.DeleteButton).setOnClickListener(view -> {

                System.out.println(itemName.getText() + " is being removed.");
                System.out.println(getAdapterPosition()+ " is the adapter position.");
                adapter.groceryList.remove(getAdapterPosition());
                adapter.notifyItemRemoved(getAdapterPosition());
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("GroceryList");
//                delete(getAdapterPosition());
//                adapter.notifyDataSetChanged();

//                adapter.notifyItemRangeRemoved(getAdapterPosition(),groceryList.size());
            });
            addToCart = (Button) itemView.findViewById(R.id.addToCartButton);

        }

        public GroceryListHolder linkAdapter(GroceryListRecyclerAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        private void delete(int position) {
            // creating a variable for our Database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("GroceryList");

//            myRef.child("lists").child(itemName).child(itemsList.get(position).getName()).setValue(itemsList.get(position));

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot ) {
                    // remove the value at reference
                    GroceryItem item = new GroceryItem();
                    List<GroceryItem> items = new ArrayList<>();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
}








