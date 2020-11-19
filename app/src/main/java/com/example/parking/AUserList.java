package com.example.parking;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AUserList extends AppCompatActivity {
    TextView text1;
    TextView text2;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminuserlist);

        db = new DatabaseHelper(getApplicationContext());
        db.createDatabase();
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        String s=db.printUL();
        text2.setText(s);
    }
}
