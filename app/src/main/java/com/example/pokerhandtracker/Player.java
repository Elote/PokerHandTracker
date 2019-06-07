package com.example.pokerhandtracker;

import java.util.ArrayList;
import java.util.List;

public class Player {
    String name;
    int chips;
    boolean isButton = false;
    int amountThisStreet = 0;
    boolean hasActed = false;

    List<Action> street1Actions = new ArrayList<>();
    List<Action> street2Actions = new ArrayList<>();;
    List<Action> street3Actions = new ArrayList<>();;
    List<Action> street4Actions = new ArrayList<>();;

    public Player(String name, int chips) {
        this.name = name;
        this.chips = chips;
    }
}
