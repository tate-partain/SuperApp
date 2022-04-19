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

public class GroceryListRecyclerAdapter extends RecyclerView.Adapter<GroceryListRecyclerAdapter.GroceryListHolder> {

    public static final String DEBUG_TAG = "GrocListRecyclerAdapter";

    private List<GroceryItem> groceryList;

    public GroceryListRecyclerAdapter( List<GroceryItem> groceryList ) {
        this.groceryList = groceryList;
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    class GroceryListHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView price;
        TextView quantity;
//        TextView comments;

        public GroceryListHolder(View itemView ) {
            super(itemView);

            itemName = (TextView) itemView.findViewById( R.id.itemName );
            price = (TextView) itemView.findViewById( R.id.price );
            quantity = (TextView) itemView.findViewById( R.id.quantity );

//            url = (TextView) itemView.findViewById( R.id.url );
//            comments = (TextView) itemView.findViewById( R.id.comments );
        }
    }

    @Override
    public GroceryListHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.grocery_item, parent, false );
        return new GroceryListHolder( view );
    }

    // This method fills in the values of the Views to show a JobLead
    @Override
    public void onBindViewHolder( GroceryListHolder holder, int position ) {
        GroceryItem groceryItem = groceryList.get( position );

        Log.d( DEBUG_TAG, "onBindViewHolder: " + groceryItem );

        holder.itemName.setText( groceryItem.getItemName());
        holder.price.setText( groceryItem.getPrice() );
        holder.quantity.setText(groceryItem.getQuantity());
//        holder.url.setText( jobLead.getUrl() );
//        holder.comments.setText( jobLead.getComments() );
    }

    @Override
    public int getItemCount() {
        return groceryList.size();
    }
}
