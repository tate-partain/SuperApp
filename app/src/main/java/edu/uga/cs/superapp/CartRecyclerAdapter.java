package edu.uga.cs.superapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

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
    class CartHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView price;
        TextView quantity;

        public CartHolder(View itemView ) {
            super(itemView);

            itemName = (TextView) itemView.findViewById( R.id.itemName );
            price = (TextView) itemView.findViewById( R.id.price );
            quantity = (TextView) itemView.findViewById( R.id.quantity );
        }
    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.grocery_item, parent, false ); //Todo: this is a grocery item holder but it works for what we need it for
        return new CartHolder( view );
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
}
