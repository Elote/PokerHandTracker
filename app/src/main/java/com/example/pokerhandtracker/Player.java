package com.example.pokerhandtracker;

import java.util.ArrayList;
import java.util.List;

public class Player {
    String name;
    int chips;
    boolean isButton = false;
    int amountThisStreet = 0;
    boolean hasActed = false;

    List<Action>[] actions = (ArrayList<Action>[]) new ArrayList[4];

    public Player(String name, int chips) {
        this.name = name;
        this.chips = chips;
    }
}
