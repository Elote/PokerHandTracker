package com.example.pokerhandtracker;

import java.util.ArrayList;
import java.util.List;

public class Pot {
    List<Player> players;
    int potSize;

    public Pot(List<Player> players, int potSize) {
        this.players = new ArrayList<>(players);
        this.potSize = potSize;
    }
}
