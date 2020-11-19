package com.example.parking;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Receipt extends AppCompatActivity {
    TextView text1;
    TextView text2;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        db = new DatabaseHelper(this);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        String s=db.calcInv();
        text2.setText(s);
    }
}
