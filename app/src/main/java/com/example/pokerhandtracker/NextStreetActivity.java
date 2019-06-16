package com.example.pokerhandtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NextStreetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_street);

        //Actionbar Title
        getSupportActionBar().setTitle("Community Cards");

        //Next Button
        Button next = findViewById(R.id.nextSteet);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText cards = findViewById(R.id.communityCards);
                if (!cards.getText().toString().equals("")) {
                    switch (MainActivity.game.currentHand.street) {
                        case 1:
                            MainActivity.game.currentHand.flop = cards.getText().toString();
                            System.out.println("Flop = " + MainActivity.game.currentHand.flop);
                            break;
                        case 2:
                            MainActivity.game.currentHand.turn = cards.getText().toString();
                            break;
                        case 3:
                            MainActivity.game.currentHand.river = cards.getText().toString();
                            break;
                    }
                }

                Intent nextPlayerHand = new Intent(getApplicationContext(), HandActivity.class);
                startActivity(nextPlayerHand);
            }
        });
    }
}
