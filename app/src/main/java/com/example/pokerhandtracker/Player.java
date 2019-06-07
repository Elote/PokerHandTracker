package com.example.pokerhandtracker;

public class Player {
    String name;
    int chips;
    boolean isButton = false;
    int amountThisStreet = 0;
    boolean hasActed = false;

    public Player(String name, int chips) {
        this.name = name;
        this.chips = chips;
    }
}
