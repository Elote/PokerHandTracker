package com.example.pokerhandtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PlayerCashoutReloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_cashout_reload);

        //Title
        getSupportActionBar().setTitle("Cashout/Reload");

        final Player cashoutPlayer = MainActivity.game.players.get(Integer.parseInt(MainActivity.seatNum));

        final TextView player = findViewById(R.id.cashoutDisplayName);
        player.setText(cashoutPlayer.name);

        TextView playerStack = findViewById(R.id.cashoutDisplayStack);
        playerStack.setText(cashoutPlayer.chips);

        final Button cashout = findViewById(R.id.playerCashout);
        cashout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.game.players.remove(cashoutPlayer);

                Intent preHandOptions = new Intent(getApplicationContext(), PreHandOptionsActivity.class);
                startActivity(preHandOptions);
            }
        });

        Button reload = findViewById(R.id.playerReload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText amount = findViewById(R.id.cashoutAmount);

                if (amount.getText().toString() != "") {
                    cashoutPlayer.chips += Integer.parseInt(amount.getText().toString());
                    cashoutPlayer.amountAtHandStart = cashoutPlayer.chips;

                    Intent preHandOptions = new Intent(getApplicationContext(), PreHandOptionsActivity.class);
                    startActivity(preHandOptions);
                }
            }
        });

        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent preHandOptions = new Intent(getApplicationContext(), PreHandOptionsActivity.class);
                startActivity(preHandOptions);
            }
        });
    }
}
