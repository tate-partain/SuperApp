package edu.uga.cs.superapp;

import android.content.Context;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * This is an adapter class for the RecyclerView to show all grocery items.
 */

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.CartHolder> {

    public static final String DEBUG_TAG = "CartRecyclerAdapter";

    private List<GroceryItem> cart;

    public CartRecyclerAdapter(List<GroceryItem> cart ) {
        this.cart = cart;
    } //Todo: may need to change this to get the cart from the cart class

    // The adapter must have a ViewHolder class to "hold" one item to show.

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.purchased_item, parent, false ); //Todo: this is a grocery item holder but it works for what we need it for
        return new CartHolder( view ).linkAdapter(this);
    }

    // This method fills in the values of the Views to show a JobLead
    @Override
    public void onBindViewHolder( CartHolder holder, int position ) {
        GroceryItem groceryItem = cart.get( position );

        Log.d( DEBUG_TAG, "onBindViewHolder: " + groceryItem );

        holder.itemName.setText( groceryItem.getItemName());
        holder.price.setText( groceryItem.getPrice() );
        holder.quantity.setText( groceryItem.getQuantity() );
        holder.itemId.setText( groceryItem.getItemId());
//        holder.comments.setText( jobLead.getComments() );
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    public class CartHolder extends RecyclerView.ViewHolder {

        EditText itemName;
        EditText price;
        EditText quantity;
        TextView itemId;
        String newName;
        String newPrice;
        String newQuan;
        //        TextView comments;

        private CartRecyclerAdapter adapter;

        public CartHolder(View itemView) {
            super(itemView);

            itemName = (EditText) itemView.findViewById(R.id.ItemName2);
            price = (EditText) itemView.findViewById(R.id.price2);
            quantity = (EditText) itemView.findViewById(R.id.quantity2);
            itemId = (TextView) itemView.findViewById(R.id.ItemId2);
            itemView.findViewById(R.id.Settle).setOnClickListener( view -> {
                User user = new User();
                user.addAmount(Double.parseDouble(price.getText().toString()));
                adapter.cart.remove(getAdapterPosition());
                adapter.notifyItemRemoved(getAdapterPosition());
                settle();
                Context context = view.getContext();
                CharSequence text = "Added to Balances";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            });
            itemView.findViewById(R.id.EditButton2).setOnClickListener(view -> {
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
            itemView.findViewById(R.id.Cancel_Button).setOnClickListener(view -> {
                adapter.cart.remove(getAdapterPosition());
                adapter.notifyItemRemoved(getAdapterPosition());
                cancel();
                Context context = view.getContext();
                CharSequence text = "Purchase Cancelled";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            });


        }

        private void cancel() {
            DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference groRef = FirebaseDatabase.getInstance().getReference("Cart");
            GroceryItem groceryItem = new GroceryItem( itemName.getText().toString(), price.getText().toString(),
                    quantity.getText().toString(), itemId.getText().toString());

            cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot ) {
                    for( DataSnapshot snap: dataSnapshot.getChildren() ) {
                        cartRef.child("GroceryList").child(itemId.getText().toString()).setValue(groceryItem);
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

        private void edit() {
            // creating a variable for our Database
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Cart");
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

        private void settle() {
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Cart");
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

        public CartRecyclerAdapter.CartHolder linkAdapter(CartRecyclerAdapter adapter) {
            this.adapter = adapter;
            return this;
        }


    }

}
