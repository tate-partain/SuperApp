package edu.uga.cs.superapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

//Todo: make sure items deleted from the grocery list are removed from the cart
    //Todo: items will not be allowed to be deleted or edited from the cart but it does need to update to reflect changes to the cart
//Todo: create a new layout for cart items since it wont have the same buttons

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.CartHolder> {

    public static final String DEBUG_TAG = "CartRecyclerAdapter";

    private List<GroceryItem> cart;

    public CartRecyclerAdapter(List<GroceryItem> cart ) {
        this.cart = cart;
    } //Todo: may need to change this to get the cart from the cart class

    // The adapter must have a ViewHolder class to "hold" one item to show.

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.grocery_item, parent, false ); //Todo: this is a grocery item holder but it works for what we need it for
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
//        holder.comments.setText( jobLead.getComments() );
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    public class CartHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView price;
        TextView quantity;
        TextView itemId;
        Button editButton;
        Button deleteButton;
        Button removeFromCart;
        //        TextView comments;

        private CartRecyclerAdapter adapter;

        public CartHolder(View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.itemName);
            price = (TextView) itemView.findViewById(R.id.price);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            itemId = (TextView) itemView.findViewById(R.id.ItemId);
//            itemView.findViewById(R.id.EditButton).setOnClickListener(view -> {
//                System.out.println(getItemCount());
//            });
//            itemView.findViewById(R.id.DeleteButton).setOnClickListener(view -> {
//
//                System.out.println(itemName.getText() + " is being removed.");
//                System.out.println(getAdapterPosition()+ " is the adapter position.");
//                delete();
//                adapter.cart.remove(getAdapterPosition());
//                adapter.notifyItemRemoved(getAdapterPosition());
//            });
            removeFromCart = (Button) itemView.findViewById(R.id.addToCartButton);

        }

        public CartRecyclerAdapter.CartHolder linkAdapter(CartRecyclerAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

//        private void delete() {
//            // creating a variable for our Database
//            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("GroceryList");
//            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot ) {
//                    // remove the value at reference
//                    for( DataSnapshot snap: dataSnapshot.getChildren() ) {
//                        myRef.child(itemId.getText().toString()).removeValue();
//                        notifyDataSetChanged();
//                        Log.d( DEBUG_TAG, "ReviewGroceryListActivity.onCreate(): added: " + snap.getRef().toString() );
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }

    }

}
