package com.example.pokerhandtracker;

import java.util.ArrayList;
import java.util.List;

public class Pot {
    List<Player> players = new ArrayList<>();
    int potSize = 0;
    int betAmount = 0;

    public Pot(int potSize) {
        this.potSize = potSize;
        this.betAmount = potSize;
    }
}
