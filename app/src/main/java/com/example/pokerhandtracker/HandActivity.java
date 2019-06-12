package com.example.pokerhandtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand);

        //Actionbar Title
        getSupportActionBar().setTitle("Hand");

        //Player Name
        TextView playerName = findViewById(R.id.playerDisplayName);
        playerName.setText(MainActivity.game.currentHand.playersInHand
                .get(MainActivity.game.currentHand.currentPlayer).name);

        //Player Stack
        TextView playerStack = findViewById(R.id.playerDisplayStack);
        playerStack.setText(Integer.toString(MainActivity.game.currentHand.playersInHand
                .get(MainActivity.game.currentHand.currentPlayer).chips));

        //Pot Total
        String potString = "";
        for (int i = 0; i < MainActivity.game.currentHand.pots.size(); i++) {
            potString += MainActivity.game.currentHand.pots.get(i).getTotal();
            if (i != MainActivity.game.currentHand.pots.size() - 1) {
                potString += " + ";
            }
        }
        TextView potDisplay = findViewById(R.id.potTotal);
        potDisplay.setText(potString);

        //Check/Fold button
        Button checkFoldButton = findViewById(R.id.checkButton);
        checkFoldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hand hand = MainActivity.game.currentHand;
                int street = MainActivity.game.currentHand.street;

                hand.checkFold();

                if (hand.isOver) {
                    Intent endOfHand = new Intent(getApplicationContext(), EndOfHandActivity.class);
                    startActivity(endOfHand);
                } else if (street == MainActivity.game.currentHand.street) {
                    Intent nextPlayerHand = new Intent(getApplicationContext(), HandActivity.class);
                    startActivity(nextPlayerHand);
                } else {
                    Intent nextStreet = new Intent(getApplicationContext(), NextStreetActivity.class);
                    startActivity(nextStreet);
                }
            }
        });

        //Call button
        Button callButton = findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hand hand = MainActivity.game.currentHand;
                int street = MainActivity.game.currentHand.street;

                MainActivity.game.currentHand.call();

                if (hand.isOver) {
                    Intent endOfHand = new Intent(getApplicationContext(), EndOfHandActivity.class);
                    startActivity(endOfHand);
                } else if (street == MainActivity.game.currentHand.street) {
                    Intent nextPlayerHand = new Intent(getApplicationContext(), HandActivity.class);
                    startActivity(nextPlayerHand);
                } else {
                    Intent nextStreet = new Intent(getApplicationContext(), NextStreetActivity.class);
                    startActivity(nextStreet);
                }
            }
        });

        //Bet/Raise button
        Button betRaiseButton = findViewById(R.id.betButton);
        betRaiseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int street = MainActivity.game.currentHand.street;

                if (street == MainActivity.game.currentHand.street) {
                    EditText amount = findViewById(R.id.betAmount);
                    if (amount.getText().toString() != "") {
                        MainActivity.game.currentHand.betRaise(Integer.parseInt(amount.getText().toString()));

                        Intent nextPlayerHand = new Intent(getApplicationContext(), HandActivity.class);
                        startActivity(nextPlayerHand);
                    }
                } else {
                    Intent nextStreet = new Intent(getApplicationContext(), NextStreetActivity.class);
                    startActivity(nextStreet);
                }
            }
        });
    }
}
