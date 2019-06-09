package com.example.pokerhandtracker;

public class Bet implements Action {
    int amount;
    Player player;
    boolean allIn = false;

    public Bet(int amount, Player player, boolean allIn) {
        this.amount = amount;
        this.player = player;
        this.allIn = allIn;
    }
}
