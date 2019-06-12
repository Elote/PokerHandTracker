package com.example.pokerhandtracker;

import java.util.ArrayList;
import java.util.List;

public class Game {

    List<Player> players = new ArrayList<>();
    int button;

    Hand currentHand;
    List<Hand> prevHands = new ArrayList<>();

    public void addPlayer(String name, int chips) {
        players.add(new Player(name, chips));
    }

    public void newHand() {
        currentHand = new Hand(generatePlayerOrder());
    }

    private List<Player> generatePlayerOrder() {
        List<Player> orderedPlayers = new ArrayList<>(players);
        for (int i = 0; i < button; i++) {
            orderedPlayers.add(orderedPlayers.get(0));
            orderedPlayers.remove(0);
        }

        return orderedPlayers;
    }

    public void pushPot(Player player, int potNum) {
        player.chips += prevHands.get(prevHands.size() - 1).pots.get(potNum).getTotal();
    }

    public void endHand() {
        currentHand.isOver = true;
        if (button == players.size() - 1) {
            button = 0;
        } else {
            button++;
        }
        prevHands.add(currentHand);
        newHand();
    }
}
