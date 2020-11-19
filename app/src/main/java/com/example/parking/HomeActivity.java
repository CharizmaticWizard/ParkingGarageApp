package com.example.parking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button B1, B2, B3, B4;
        B1 = findViewById(R.id.button_P1);
        B2 = findViewById(R.id.button_P2);
        B3 = findViewById(R.id.button_Res);
        B4 = findViewById(R.id.button_Rec);

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent HomePage = new Intent(HomeActivity.this, ParkingLot1.class);
                    startActivity(HomePage);
            }
        });
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomePage = new Intent(HomeActivity.this, ParkingLot2.class);
                startActivity(HomePage);
            }
        });
        B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomePage = new Intent(HomeActivity.this, Reservation.class);
                startActivity(HomePage);
            }
        });
        B4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomePage = new Intent(HomeActivity.this, Receipt.class);
                startActivity(HomePage);
            }
        });
    }
}