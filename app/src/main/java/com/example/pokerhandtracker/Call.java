package com.example.pokerhandtracker;

public class Call implements Action {
    Player player;
    boolean allIn = false;

    public Call(Player player, boolean allIn) {
        this.player = player;
        this.allIn = allIn;
    }
}
