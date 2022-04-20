package edu.uga.cs.superapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class SettleUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle_up);

//        ListUsersPage page = FirebaseAuth.getInstance().listUsers(null);
//        while (page != null) {
//            for (ExportedUserRecord user : page.getValues()) {
//                System.out.println("User: " + user.getUid());
//            }
//            page = page.getNextPage();
//        }
    }
}