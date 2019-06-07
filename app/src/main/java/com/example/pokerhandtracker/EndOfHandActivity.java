package com.example.pokerhandtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EndOfHandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_hand);

        //Hand Just Played
        Hand hand = MainActivity.game.prevHands.get(MainActivity.game.prevHands.size() - 1);

        //Actionbar Title
        getSupportActionBar().setTitle("End of Hand");

        //Set Hand Winner (if applicable)
        final EditText handWinner = findViewById(R.id.handWinner);
        if (hand.playersInHand.size() == 1) {
            for (int i = 0; i < MainActivity.game.players.size(); i++) {
                if (MainActivity.game.players.get(i) == hand.playersInHand.get(0)) {
                    handWinner.setText(String.valueOf(i + 1));
                }
            }
        }

        //Next Hand Button
        Button nextHand = findViewById(R.id.newHand);
        nextHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hand hand = MainActivity.game.prevHands.get(MainActivity.game.prevHands.size() - 1);

                if (handWinner.getText().toString() != "") {
                    for (Player player : hand.playersInHand) {
                        if (player == MainActivity.game.players.get(
                                Integer.parseInt(handWinner.getText().toString()) - 1)) {
                            MainActivity.game.pushPot(player);
                        }
                    }

                    Intent newHandActivity = new Intent(getApplicationContext(), HandActivity.class);
                    startActivity(newHandActivity);
                }
            }
        });
    }
}
