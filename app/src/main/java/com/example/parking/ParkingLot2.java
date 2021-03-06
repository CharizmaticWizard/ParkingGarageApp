package com.example.parking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ParkingLot2 extends AppCompatActivity {
    DatabaseHelper db;
    Button B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, Submit;
    final int[] slot = {0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parkinglot2);

        db = new DatabaseHelper(this);
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
        Submit = findViewById(R.id.button_sub);

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
                slot[0] = 9;
            }
        });
        B10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot[0] = 10;
            }
        });
        B11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot[0] = 11;
            }
        });
        B12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slot[0] = 12;
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b = db.checkSlot2(slot[0]);
                if (b == true) {
                    Toast.makeText(ParkingLot2.this,"Slot Available",Toast.LENGTH_SHORT).show();
                    long val = db.makeRes2(slot[0]);
                    if (val > 0) {
                        Toast.makeText(ParkingLot2.this, "Slot Reserved", Toast.LENGTH_SHORT).show();
                        Submit.setEnabled(false);
                    } else {
                        Toast.makeText(ParkingLot2.this, "Reservation Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ParkingLot2.this, "Slot Unavailable", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
