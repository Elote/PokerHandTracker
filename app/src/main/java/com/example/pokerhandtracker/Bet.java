package com.example.pokerhandtracker;

public class Bet implements Action {
    int amount;
    Player player;

    public Bet(int amount, Player player) {
        this.amount = amount;
        this.player = player;
    }
}
