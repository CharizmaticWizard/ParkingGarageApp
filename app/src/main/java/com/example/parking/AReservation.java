package com.example.parking;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;

public class AReservation extends AppCompatActivity {
    TextView text1;
    TextView text2;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminreservation);

        db = new DatabaseHelper(getApplicationContext());
        db.createDatabase();
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        String s=db.printRes();
        text2.setText(s);
    }}