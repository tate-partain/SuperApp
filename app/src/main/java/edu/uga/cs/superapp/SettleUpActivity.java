package edu.uga.cs.superapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SettleUpActivity extends AppCompatActivity {

    User user;
    String email;
//    ListView lv;
    TextView emailText;
    TextView amountText;
    Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle_up);

        emailText = findViewById(R.id.emailText);
        amountText = findViewById(R.id.amountText);
        resetButton = findViewById(R.id.resetButton);

        user = new User();
        emailText.setText(user.getEmail());
        amountText.setText(String.valueOf(user.getAmount()));
        resetButton.setOnClickListener(new SettleUpActivity.ResetButtonClickListener());
    }


    private class ResetButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            user.resetAmount(user.getId());
        }
    }
}