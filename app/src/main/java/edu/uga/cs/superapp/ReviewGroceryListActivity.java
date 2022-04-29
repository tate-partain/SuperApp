package edu.uga.cs.superapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
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

public class ReviewGroceryListActivity
         extends AppCompatActivity
        implements AddGroceryItemDialogFragment.AddGroceryItemDialogListener {

    public static final String DEBUG_TAG = "ReviewGrocListActivity";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recyclerAdapter;
    private ListView listView;
    private List<GroceryItem> groceryList;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        Log.d( DEBUG_TAG, "ReviewGroceryListActivity.onCreate()" );

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_review_grocery_list );
        //listView = findViewById(R.id.list_view);
        groceryList = new ArrayList<GroceryItem>();
        recyclerView = (RecyclerView) findViewById( R.id.recyclerView );

        FloatingActionButton floatingButton = findViewById(R.id.floatingActionButton);
        floatingButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new AddGroceryItemDialogFragment();
                showDialogFragment( newFragment );
            }
        });
        // use a linear layout manager for the recycler view
        layoutManager = new LinearLayoutManager(this );
        recyclerView.setLayoutManager( layoutManager );
        recyclerAdapter = new GroceryListRecyclerAdapter( groceryList );
        recyclerView.setAdapter( recyclerAdapter );

        // get a Firebase DB instance reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("GroceryList");

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
                    groceryList.add(groceryItem);
                    Log.d( DEBUG_TAG, "ReviewGroceryListActivity.onCreate(): added: " + groceryItem );
                }
                Log.d( DEBUG_TAG, "ReviewGroceryListActivity.onCreate(): setting recyclerAdapter" );

                recyclerAdapter = new GroceryListRecyclerAdapter( groceryList );
                recyclerView.setAdapter( recyclerAdapter );
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }


        } );
    }

//    private void showData(DataSnapshot snapshot) {
//        for (DataSnapshot post : snapshot.getChildren()) {
//            GroceryItem items = post.getValue(GroceryItem.class);
//            groceryList.add(items);
//            Log.d( DEBUG_TAG, "ReviewGroceryListActivity.onCreate(): added: " + items );
//            Log.d( DEBUG_TAG, "ReviewGroceryListActivity.onCreate(): setting recyclerAdapter" );
//            recyclerAdapter = new GroceryListRecyclerAdapter( groceryList );
//            recyclerView.setAdapter( recyclerAdapter );
//
//        }
//    }

    // this is our own callback for a DialogFragment which adds a new grocery item.
    public void onFinishNewGroceryItemDialog(GroceryItem groceryItem) {
        // add the new item
        // Add a new element (GroceryItem) to the list of items in Firebase.
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("GroceryList");
        ///int itemId = groceryList.size();
        // First, a call to push() appends a new node to the existing list (one is created
        // if this is done for the first time).  Then, we set the value in the newly created
        // list node to store the new grocery item.
        // This listener will be invoked asynchronously, as no need for an AsyncTask, as in
        // the previous apps to maintain job leads.
        myRef.push().setValue( groceryItem )
                .addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        // Update the recycler view to include the new job lead
                        groceryList.add( groceryItem );
                        recyclerAdapter.notifyItemInserted(groceryList.size() - 1);

                        Log.d( DEBUG_TAG, "Grocery item saved: " + groceryItem );
                        // Show a quick confirmation
                        Toast.makeText(getApplicationContext(), "Grocery Item created for " + groceryItem.getItemName(),
                                Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText( getApplicationContext(), "Failed to create a grocery item for " + groceryItem.getItemName(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void showDialogFragment( DialogFragment newFragment ) {
        newFragment.show( getSupportFragmentManager(), null);
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
