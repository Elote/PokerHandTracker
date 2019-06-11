package com.example.pokerhandtracker;

public class Player {
    String name;
    int chips;
    boolean isButton = false;
    boolean isAllIn = false;
    int amountThisStreet = 0;

    public Player(String name, int chips) {
        this.name = name;
        this.chips = chips;
    }
}
