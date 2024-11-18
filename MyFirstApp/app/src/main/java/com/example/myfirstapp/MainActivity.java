package com.example.myfirstapp;

import android.content.Intent;
import android.graphics.Color;  // <-- Add this import for the Color class
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the text of TextView
                textView.setText("Button Clicked!");

                // Change the text color to RED
                textView.setTextColor(Color.RED);

                // Create an Intent to start the SecondActivity
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                // Add the extra message before starting the activity
                intent.putExtra("EXTRA_MESSAGE", "Hello from MainActivity!");

                // Start SecondActivity
                startActivity(intent);

                // Show a toast message
                Toast.makeText(MainActivity.this, "Button was clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
