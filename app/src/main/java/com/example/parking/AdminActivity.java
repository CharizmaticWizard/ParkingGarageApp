package com.example.parking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Button B1, B2, B3, B4, B5;
        B1 = findViewById(R.id.button_P1);
        B2 = findViewById(R.id.button_P2);
        B3 = findViewById(R.id.button_Res);
        B4 = findViewById(R.id.button_Rec);
        B5 = findViewById(R.id.button_UL);

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomePage = new Intent(AdminActivity.this, AParkingLot1.class);
                startActivity(HomePage);
            }
        });
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomePage = new Intent(AdminActivity.this, AParkingLot2.class);
                startActivity(HomePage);
            }
        });
        B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomePage = new Intent(AdminActivity.this, AReservation.class);
                startActivity(HomePage);
            }
        });
        B4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomePage = new Intent(AdminActivity.this, AReceipt.class);
                startActivity(HomePage);
            }
        });
        B5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomePage = new Intent(AdminActivity.this, AUserList.class);
                startActivity(HomePage);
            }
        });
    }
}