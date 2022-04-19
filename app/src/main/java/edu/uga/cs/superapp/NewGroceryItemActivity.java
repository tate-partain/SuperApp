package edu.uga.cs.superapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This class is an activity to add an item to the grocery list.
 */

public class NewGroceryItemActivity extends AppCompatActivity{

    public static final String DEBUG_TAG = "NewGroceryItemActivity";

    private EditText itemNameView;
    private EditText priceView;
    private EditText quantityView;
//    private EditText commentsView;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_grocery_item);

        itemNameView = (EditText) findViewById( R.id.editText1 );
        priceView = (EditText) findViewById( R.id.editText2 );
        quantityView = (EditText) findViewById( R.id.editText3 );
//        commentsView = (EditText) findViewById( R.id.editText4 );
        saveButton = (Button) findViewById( R.id.button );

        saveButton.setOnClickListener( new ButtonClickListener());
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String itemName = itemNameView.getText().toString();
            String price = priceView.getText().toString();
            String quantity = quantityView.getText().toString();
//            String url = urlView.getText().toString();
//            String comments = commentsView.getText().toString();
            final GroceryItem groceryItem = new GroceryItem( itemName, price, quantity);

            // Add a new element (GroceryItem) to the list of items in Firebase.
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("GroceryList");

            // First, a call to push() appends a new node to the existing list (one is created
            // if this is done for the first time).  Then, we set the value in the newly created
            // list node to store the new grocery item.
            // This listener will be invoked asynchronously, as no need for an AsyncTask, as in
            // the previous apps to maintain grocery items.
            myRef.push().setValue( groceryItem )
                    .addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Show a quick confirmation
                            Toast.makeText(getApplicationContext(), "Grocery item created for " + groceryItem.getItemName(),
                                    Toast.LENGTH_SHORT).show();

                            // Clear the EditTexts for next use.
                            itemNameView.setText("");
                            priceView.setText("");
                            quantityView.setText("");
//                            commentsView.setText("");
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
    }

    @Override
    protected void onResume() {
        Log.d( DEBUG_TAG, "NewGroceryItemActivity.onResume()" );
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d( DEBUG_TAG, "NewGroceryItemActivity.onPause()" );
        super.onPause();
    }

    // The following activity callback methods are not needed and are for educational purposes only
    @Override
    protected void onStart() {
        Log.d( DEBUG_TAG, "NewGroceryItemActivity.onStart()" );
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d( DEBUG_TAG, "NewGroceryItemActivity.onStop()" );
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d( DEBUG_TAG, "NewGroceryItemActivity.onDestroy()" );
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d( DEBUG_TAG, "NewGroceryItemActivity.onRestart()" );
        super.onRestart();
    }
}
