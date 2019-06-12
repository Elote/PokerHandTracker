package com.example.pokerhandtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PreHandOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_hand_options);

        //Title
        getSupportActionBar().setTitle("Hand Intermission");

        Button buyin = findViewById(R.id.postBuyin);
        buyin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        Button cashoutReload = findViewById(R.id.postCashReload);
        cashoutReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText seatNum = findViewById(R.id.postSeatNum);
                MainActivity.seatNum = seatNum.getText().toString();

                if (seatNum.getText().toString() != "") {
                    Intent newCashoutReload = new Intent(getApplicationContext(), PlayerCashoutReloadActivity.class);
                    startActivity(newCashoutReload);
                }
            }
        });
    }
}
