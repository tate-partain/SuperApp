package edu.uga.cs.superapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainScreenManagerActivity extends AppCompatActivity{

    private static final String DEBUG_TAG = "ManagementActivity";

    private Button addItemButton;
    private Button reviewListButton;
    private TextView signedInTextView;
    private Button signOutButton;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_management);

        Log.d( DEBUG_TAG, "MainScreenManagerActivity.onCreate()" );

        addItemButton = findViewById( R.id.button1 );
        reviewListButton = findViewById( R.id.button2 );
        signedInTextView = findViewById( R.id.textView3 );
        signOutButton = findViewById(R.id.signOutButton);

        addItemButton.setOnClickListener( new AddItemButtonClickListener() );
        reviewListButton.setOnClickListener( new ReviewListButtonClickListener() );
        signOutButton.setOnClickListener(new SignOutButtonClickListener());

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
