package edu.uga.cs.superapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainScreenManagerActivity extends AppCompatActivity{

    private static final String DEBUG_TAG = "ManagementActivity";

    private Button addItemButton;
    private Button reviewListButton;
    private TextView signedInTextView;
    private Button signOutButton;
    private Button viewCartButton;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private List<User> userList;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userList = new ArrayList<User>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_management);

        Log.d( DEBUG_TAG, "MainScreenManagerActivity.onCreate()" );

        addItemButton = findViewById( R.id.button1 );
        reviewListButton = findViewById( R.id.button2 );
        signedInTextView = findViewById( R.id.textView3 );
        signOutButton = findViewById(R.id.signOutButton);
        viewCartButton = findViewById(R.id.button4);

        addItemButton.setOnClickListener( new AddItemButtonClickListener() );
        reviewListButton.setOnClickListener( new ReviewListButtonClickListener() );
        signOutButton.setOnClickListener(new SignOutButtonClickListener());
        viewCartButton.setOnClickListener(new ReviewCartButtonClickListener());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot ) {
//                showData(snapshot);
                for( DataSnapshot postSnapshot: snapshot.getChildren() ) { //Todo: determine if .child("users") works and is needed
                    User singleUser = postSnapshot.getValue(User.class);
                    userList.add(singleUser);
                    Log.d( DEBUG_TAG, "MainScreenManagerActivity.onCreate(): added user to list");
                }
                //top
                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    boolean isUser = false;
                    try {
                        for (int i = 0; i < userList.size(); i++) {
                            if ((currentUser.getUid()).equals((userList.get(i)).getId())) {
                                isUser = true;
                                user = userList.get(i);
                                Log.d(DEBUG_TAG, "Previous user id found. No new user to be created.");
                                break;
                            }
                        }
                    } catch(Exception e) {
                        //keep going. isUser is already set properly
                    }
                    if (isUser == false) { //no user with the current users uid (new user)
                        Log.d(DEBUG_TAG, "No user with specified UID found. Creating new user");
                        User newUser = new User(currentUser.getUid(), 0);
                        myRef.push().setValue( newUser )
                                .addOnSuccessListener( new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Show a quick confirmation
                                        Toast.makeText(getApplicationContext(), "New user created ",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener( new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText( getApplicationContext(), "Failed to create new user",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                } else {
                    Log.d(DEBUG_TAG, "Error getting current user."); //Todo: may want to add a handler for this error
                    // No user is signed in
                }
                //bottom
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        } );

        // Setup a listener for a change in the sign in status (authentication status change)
        // when it is invoked, check if a user is signed in and update the UI text view string,
        // as needed.
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if( currentUser != null ) {
                    // User is signed in
                    Log.d(DEBUG_TAG, "onAuthStateChanged:signed_in:" + currentUser.getUid());
                    String userEmail = currentUser.getEmail();
                    signedInTextView.setText( "Signed in as: " + userEmail );
                } else {
                    // User is signed out
                    Log.d( DEBUG_TAG, "onAuthStateChanged:signed_out" );
                    signedInTextView.setText( "Signed in as: not signed in" );
                }
            }
        });
    }

    private class AddItemButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), NewGroceryItemActivity.class);
            view.getContext().startActivity( intent );
        }
    }

    private class ReviewListButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ReviewGroceryListActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    private class ReviewCartButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ReviewCartActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    private class SignOutButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//Todo: make sure this prohibits the user from going back but also works
            view.getContext().startActivity(intent);
        }
    }

    // These activity callback methods are not needed and are for educational purposes only
    @Override
    protected void onStart() {
        Log.d( DEBUG_TAG, "MainScreenManagerActivity.onStart()" );
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d( DEBUG_TAG, "MainScreenManagerActivity.onResume()" );
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d( DEBUG_TAG, "MainScreenManagerActivity.onPause()" );
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d( DEBUG_TAG, "MainScreenManagerActivity.onStop()" );
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d( DEBUG_TAG, "MainScreenManagerActivity.onDestroy()" );
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d( DEBUG_TAG, "MainScreenManagerActivity.onRestart()" );
        super.onRestart();
    }
}
