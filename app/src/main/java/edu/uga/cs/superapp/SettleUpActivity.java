package edu.uga.cs.superapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

public class SettleUpActivity extends AppCompatActivity {

    User user;
    String email;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle_up);

//        user = new User();
//        email = user.getEmail();
//        lv = findViewById(R.id.listview);
//        ArrayAdapter<GroceryItem> arr = new ArrayAdapter<GroceryItem>(this, R.layout.support_simple_spinner_dropdown_item, Cart.getCart());
//        lv.setAdapter(arr);



    }
}