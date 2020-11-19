package com.example.parking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Reservation extends AppCompatActivity {
    TextView text1;
    TextView text2;
    Button b1;
    Button b2;
    Button b3;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        db = new DatabaseHelper(getApplicationContext());
        db.openDatabase();
        b1 = findViewById(R.id.checkin);
        b2 = findViewById(R.id.checkout);
        b3 = findViewById(R.id.cancel);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        String s= db.dispRes();
        text2.setText(s);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.genInv();
                Toast.makeText(Reservation.this,"Check In Time Noted.",Toast.LENGTH_SHORT).show();
                b1.setEnabled(false);
                b2.setEnabled(true);
                b3.setEnabled(false);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.updInv();
                Toast.makeText(Reservation.this,"Check Out Time Noted. Invoice Generated.",Toast.LENGTH_LONG).show();
                text2.setText("");
                b1.setEnabled(false);
                b2.setEnabled(false);
                b3.setEnabled(false);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.cancelRes();
                Toast.makeText(Reservation.this,"Reservation Cancelled.",Toast.LENGTH_SHORT).show();
                String s= db.dispRes();
                text2.setText(s);
                b1.setEnabled(false);
                b2.setEnabled(false);
                b3.setEnabled(false);
            }
        });
    }}