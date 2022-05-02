package edu.uga.cs.superapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "MainActivity";

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode( AppCompatDelegate.MODE_NIGHT_NO );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d( DEBUG_TAG, "Grocery Item: MainActivity.onCreate()" );

        Button signInButton = findViewById( R.id.button1 );
        Button registerButton = findViewById( R.id.button2 );

        signInButton.setOnClickListener( new SignInButtonClickListener() );
        registerButton.setOnClickListener( new RegisterButtonClickListener() );

    }

    // A button listener class to start a Firebase sign-in process
    private class SignInButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick( View v ) {
            // This is an example of how to use the AuthUI activity for signing in to Firebase.
            // Here, we are just using email/password sign in.
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build()
            );

            Log.d( DEBUG_TAG, "MainActivity.SignInButtonClickListener: Signing in started" );

            // Create an Intent to singin to Firebese.
            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)

                       .setTheme(R.style.Theme_SuperApp) //Todo: create style here.
                    .build();
            signInLauncher.launch(signInIntent);
        }
    }

    // The ActivityResultLauncher class provides a new way to invoke an activity
    // for some result.  It is a replacement for the deprecated method startActivityForResult.
    //
    // The signInLauncher variable is a launcher to start the AuthUI's logging in process that
    // should return to the MainActivity when completed.  The overridden onActivityResult
    // is then called when the Firebase logging-in process is finished.
    private ActivityResultLauncher<Intent> signInLauncher =
            registerForActivityResult(
                    new FirebaseAuthUIActivityResultContract(),
                    new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                        @Override
                        public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                            onSignInResult(result);
                        }
                    }
            );

    // This method is called (above) once the Firebase sign-in activity returns (completes).
    // The current (logged-in) Firebase user can be obtained.
    // Then, there is a transition to the JobLeadManagementActivity.
    private void onSignInResult( FirebaseAuthUIAuthenticationResult result ) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            if( response != null ) {
                Log.d( DEBUG_TAG, "MainActivity.onSignInResult: response.getEmail(): " + response.getEmail() );
            }
            final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser user = firebaseAuth.getCurrentUser();

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
////            mDatabase.child("users").child(user.getUid()).setValue(displayName);
//
//            User newUser = User.getInstance();
//            newUser.setUid(user.getUid());
////            String username = mDatabase.child("users").child(user.getUid()).get  //Todo: get rid of this if you can
////            newUser.setUserName(mDatabase.child("users").child(user.getUid()).get());
//            //Log.d( DEBUG_TAG, "MainActivity.onSignInResult: Signed in as: " + user.getEmail() );

            // after a successful sign in, start the job leads management activity
            Intent intent = new Intent( this, MainScreenManagerActivity.class );
            startActivity( intent );
        }
        else {
            Log.d( DEBUG_TAG, "MainActivity.onSignInResult: Failed to sign in" );
            // Sign in failed. If response is null the user canceled the
            Toast.makeText( getApplicationContext(),
                    "Sign in failed",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private class RegisterButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // start the user registration activity
            Intent intent = new Intent(view.getContext(), RegisterActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    // These activity callback methods are not needed and are for educational purposes only
    @Override
    protected void onStart() {
        Log.d( DEBUG_TAG, "GroceryItem: MainActivity.onStart()" );
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d( DEBUG_TAG, "GroceryItem: MainActivity.onResume()" );
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d( DEBUG_TAG, "GroceryItem: MainActivity.onPause()" );
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d( DEBUG_TAG, "GroceryItem: MainActivity.onStop()" );
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d( DEBUG_TAG, "GroceryItem: MainActivity.onDestroy()" );
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d( DEBUG_TAG, "GroceryItem: MainActivity.onRestart()" );
        super.onRestart();
    }
}




//onDataChange is needed to get a full list of the data
    //its not necessarily called only when data changes

//need to be able to log out
//need user registration