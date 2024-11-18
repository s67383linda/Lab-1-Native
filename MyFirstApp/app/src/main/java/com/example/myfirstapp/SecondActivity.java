package com.example.myfirstapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Set up a TextView in the layout to display a message
        TextView textView = findViewById(R.id.textViewSecond);

        // Get the message from the Intent if it's passed
        String message = getIntent().getStringExtra("EXTRA_MESSAGE");

        if (message != null) {
            // If the message exists, set the text of the TextView
            textView.setText(message);
        } else {
            // If no message is passed, set a default message
            textView.setText("Welcome to the Second Activity!");
        }

        // Initialize the 'Go Back' button and set the listener
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> finish());  // Close the current activity
    }
}
