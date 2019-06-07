package com.example.pokerhandtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class NewGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        //Actionbar Title
        getSupportActionBar().setTitle("New Game");

        //Game Creation on Next Button
        Button createGame = findViewById(R.id.nextNewGame);
        createGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.game = new Game();
                addPlayers();
                assignButton();
                MainActivity.game.newHand();

                Intent newHand = new Intent(getApplicationContext(), HandActivity.class);
                startActivity(newHand);
            }
        });
    }

    private void addPlayers() {
        //Name of Seat 1
        EditText seat1Name = findViewById(R.id.seat1Name);
        String seat1NameString = seat1Name.getText().toString();

        //Checks if Seat 1 is in use
        if (!seat1NameString.isEmpty()) {
            //Starting stack of Seat 1
            EditText seat1Stack = findViewById(R.id.seat1Stack);
            int seat1StackInt = Integer.parseInt(seat1Stack.getText().toString());

            MainActivity.game.addPlayer(seat1NameString, seat1StackInt);
        }

        //Name of Seat 2
        EditText seat2Name = findViewById(R.id.seat2Name);
        String seat2NameString = seat2Name.getText().toString();

        //Checks if Seat 2 is in use
        if (!seat2NameString.isEmpty()) {
            //Starting stack of Seat 2
            EditText seat2Stack = findViewById(R.id.seat2Stack);
            int seat2StackInt = Integer.parseInt(seat2Stack.getText().toString());

            MainActivity.game.addPlayer(seat2NameString, seat2StackInt);
        }

        //Name of Seat 3
        EditText seat3Name = findViewById(R.id.seat3Name);
        String seat3NameString = seat3Name.getText().toString();

        //Checks if Seat 3 is in use
        if (!seat3NameString.isEmpty()) {
            //Starting stack of Seat 3
            EditText seat3Stack = findViewById(R.id.seat3Stack);
            int seat3StackInt = Integer.parseInt(seat3Stack.getText().toString());

            MainActivity.game.addPlayer(seat3NameString, seat3StackInt);
        }

        //Name of Seat 4
        EditText seat4Name = findViewById(R.id.seat4Name);
        String seat4NameString = seat4Name.getText().toString();

        //Checks if Seat 4 is in use
        if (!seat4NameString.isEmpty()) {
            //Starting stack of Seat 4
            EditText seat4Stack = findViewById(R.id.seat4Stack);
            int seat4StackInt = Integer.parseInt(seat4Stack.getText().toString());

            MainActivity.game.addPlayer(seat4NameString, seat4StackInt);
        }

        //Name of Seat 5
        EditText seat5Name = findViewById(R.id.seat5Name);
        String seat5NameString = seat5Name.getText().toString();

        //Checks if Seat 5 is in use
        if (!seat5NameString.isEmpty()) {
            //Starting stack of Seat 5
            EditText seat5Stack = findViewById(R.id.seat5Stack);
            int seat5StackInt = Integer.parseInt(seat5Stack.getText().toString());

            MainActivity.game.addPlayer(seat5NameString, seat5StackInt);
        }

        //Name of Seat 6
        EditText seat6Name = findViewById(R.id.seat6Name);
        String seat6NameString = seat6Name.getText().toString();

        //Checks if Seat 6 is in use
        if (!seat6NameString.isEmpty()) {
            //Starting stack of Seat 6
            EditText seat6Stack = findViewById(R.id.seat6Stack);
            int seat6StackInt = Integer.parseInt(seat6Stack.getText().toString());

            MainActivity.game.addPlayer(seat6NameString, seat6StackInt);
        }

        //Name of Seat 7
        EditText seat7Name = findViewById(R.id.seat7Name);
        String seat7NameString = seat7Name.getText().toString();

        //Checks if Seat 7 is in use
        if (!seat7NameString.isEmpty()) {
            //Starting stack of Seat 7
            EditText seat7Stack = findViewById(R.id.seat7Stack);
            int seat7StackInt = Integer.parseInt(seat7Stack.getText().toString());

            MainActivity.game.addPlayer(seat7NameString, seat7StackInt);
        }

        //Name of Seat 8
        EditText seat8Name = findViewById(R.id.seat8Name);
        String seat8NameString = seat8Name.getText().toString();

        //Checks if Seat 8 is in use
        if (!seat8NameString.isEmpty()) {
            //Starting stack of Seat 8
            EditText seat8Stack = findViewById(R.id.seat8Stack);
            int seat8StackInt = Integer.parseInt(seat8Stack.getText().toString());

            MainActivity.game.addPlayer(seat8NameString, seat8StackInt);
        }

        //Name of Seat 9
        EditText seat9Name = findViewById(R.id.seat9Name);
        String seat9NameString = seat9Name.getText().toString();

        //Checks if Seat 9 is in use
        if (!seat9NameString.isEmpty()) {
            //Starting stack of Seat 9
            EditText seat9Stack = findViewById(R.id.seat9Stack);
            int seat9StackInt = Integer.parseInt(seat9Stack.getText().toString());;

            MainActivity.game.addPlayer(seat9NameString, seat9StackInt);
        }
    }

    private void assignButton() {
        RadioGroup radioGroup = findViewById(R.id.newGameButtonGroup);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedButton = findViewById(selectedId);
        MainActivity.game.button = Integer.parseInt(selectedButton.getTag().toString()) - 1;
        MainActivity.game.players.get(MainActivity.game.button).isButton = true;
    }
}
