package com.example.pokerhandtracker;

import java.util.HashMap;
import java.util.Map;

public class Pot {
    Map<Player, Integer> playerContribution = new HashMap<>();
    Player winner;

    public Pot() {

    }

    public Pot(Pot otherPot) {
        this.playerContribution = new HashMap<>(otherPot.playerContribution);
    }

    public int getTotal() {
        int total = 0;
        for (Player player : playerContribution.keySet()) {
            total += playerContribution.get(player);
        }
        return total;
    }
}
