package com.example.pokerhandtracker;

import java.util.HashMap;
import java.util.Map;

public class Pot {
    Map<Player, Integer> playerContribution = new HashMap<>();

    public int getTotal() {
        return playerContribution.get(0) * playerContribution.size();
    }
}
