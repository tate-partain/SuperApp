package edu.uga.cs.superapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an activity controller class for listing the current grocery list.
 * The current items are listed as a RecyclerView.
 */

public class ReviewCartActivity
        extends AppCompatActivity {

    public static final String DEBUG_TAG = "ReviewCartActivity";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recyclerAdapter;

    private List<GroceryItem> cart;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        Log.d( DEBUG_TAG, "ReviewCartActivity.onCreate()" );

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_review_cart );
        cart = new ArrayList<GroceryItem>();
        recyclerView = (RecyclerView) findViewById( R.id.recyclerView2 );
        layoutManager = new LinearLayoutManager(this );
        recyclerView.setLayoutManager( layoutManager );
        recyclerAdapter = new CartRecyclerAdapter( cart );
        recyclerView.setAdapter( recyclerAdapter );

        // get a Firebase DB instance reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Cart");
        // Set up a listener (event handler) to receive a value for the database reference, but only one time.
        // This type of listener is called by Firebase once by immediately executing its onDataChange method.
        // We can use this listener to retrieve the current list of GroceryItems.
        // Other types of Firebase listeners may be set to listen for any and every change in the database
        // i.e., receive notifications about changes in the data in real time (hence the name, Realtime database).
        // This listener will be invoked asynchronously, as no need for an AsyncTask, as in the previous apps
        // to maintain job leads.
        myRef.addListenerForSingleValueEvent( new ValueEventListener() {

            @Override
            public void onDataChange( DataSnapshot snapshot ) {
                // Once we have a DataSnapshot object, knowing that this is a list,
                // we need to iterate over the elements and place them on a List.
//                showData(snapshot);
                // Now, create a JobLeadRecyclerAdapter to populate a ReceyclerView to display the job leads.
                for( DataSnapshot postSnapshot: snapshot.getChildren() ) {
                    GroceryItem groceryItem = postSnapshot.getValue(GroceryItem.class);
                    cart.add(groceryItem);
                    Log.d( DEBUG_TAG, "ReviewGroceryListActivity.onCreate(): added: " + groceryItem );
                }
                Log.d( DEBUG_TAG, "ReviewGroceryListActivity.onCreate(): setting recyclerAdapter" );

                recyclerAdapter = new CartRecyclerAdapter( cart );
                recyclerView.setAdapter( recyclerAdapter );
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }


        } );
    }

    @Override
    protected void onResume() {
        Log.d( DEBUG_TAG, "ReviewGroceryListActivity.onResume()" );
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d( DEBUG_TAG, "ReviewGroceryListActivity.onPause()" );
        super.onPause();
    }

    // These activity callback methods are not needed and are for edational purposes only
    @Override
    protected void onStart() {
        Log.d( DEBUG_TAG, "ReviewGroceryListActivity.onStart()" );
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d( DEBUG_TAG, "ReviewGroceryListActivity.onStop()" );
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d( DEBUG_TAG, "ReviewGroceryListActivity.onDestroy()" );
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d( DEBUG_TAG, "ReviewGroceryListActivity.onRestart()" );
        super.onRestart();
    }
}
