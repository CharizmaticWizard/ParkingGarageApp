package com.example.parking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AParkingLot1 extends AppCompatActivity {
    DatabaseHelper db;
    Button B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, Lock, Unlock;
    final int[] slot = {0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminparking);

        db = new DatabaseHelper(getApplicationContext());
        db.createDatabase();
        B1 = findViewById(R.id.button_1);
        B2 = findViewById(R.id.button_2);
        B3 = findViewById(R.id.button_3);
        B4 = findViewById(R.id.button_4);
        B5 = findViewById(R.id.button_5);
        B6 = findViewById(R.id.button_6);
        B7 = findViewById(R.id.button_7);
        B8 = findViewById(R.id.button_8);
        B9 = findViewById(R.id.button_9);
        B10 = findViewById(R.id.button_10);
        B11 = findViewById(R.id.button_11);
        B12 = findViewById(R.id.button_12);
        B13 = findViewById(R.id.button_13);
        B14 = findViewById(R.id.button_14);
        B15 = findViewById(R.id.button_15);
        Lock = findViewById(R.id.button_l);
        Unlock = findViewById(R.id.button_ul);

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot[0] = 1;
            }
        });
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot[0] = 2;
            }
        });
        B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot[0] = 3;
            }
        });
        B4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot[0] = 4;
            }
        });
        B5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot[0] = 5;
            }
        });
        B6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot[0] = 6;
            }
        });
        B7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot[0] = 7;
            }
        });
        B8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot[0] = 8;
            }
        });
        B9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot[0]= 9;
            }
        });
        B10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot[0]= 10;
            }
        });
        B11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot[0]= 11;
            }
        });
        B12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot[0]= 12;
            }
        });
        B13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot[0]= 13;
            }
        });
        B14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot[0]= 14;
            }
        });
        B15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot[0]= 15;
            }
        });
        Lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.lock(slot[0]);
                Toast.makeText(AParkingLot1.this, "Slot Restricted", Toast.LENGTH_LONG).show();
            }
        });
        Unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.unlock(slot[0]);
                Toast.makeText(AParkingLot1.this, "Slot Released", Toast.LENGTH_LONG).show();
            }
        });
    }
}
