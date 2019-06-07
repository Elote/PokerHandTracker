package com.example.pokerhandtracker;

import java.util.List;

public class Hand {
    List<Player> playersInHand;
    int currentPlayer = 3;
    int pot = 15;
    int currentBet = 10;
    int street = 0;

    String flop = "";
    String turn = "";
    String river = "";

    boolean isOver = false;
    Player winner;

    public Hand(List<Player> playersInHand) {
        this.playersInHand = playersInHand;
        playersInHand.get(1).amountThisStreet = 5;
        playersInHand.get(1).chips -= 5;
        playersInHand.get(2).amountThisStreet = 10;
        playersInHand.get(2).chips -= 10;
        for (Player player : this.playersInHand) {
            player.hasActed = false;
        }
    }

    public void checkFold() {
        Player player = playersInHand.get(currentPlayer);
        cyclePlayer();
        //Big blind preflop check
        if (!(player.amountThisStreet == currentBet && !player.hasActed)) {
            if (currentBet > 0) {
                playersInHand.remove(player);
                if (playersInHand.size() == 1) {
                    currentPlayer = 0;
                }
                if (currentPlayer != 0) { //currentPlayer already cycled
                    currentPlayer--;
                }
            }
        }
        player.hasActed = true;
        checkNoMoreAction();
        checkNoMorePlayers();
    }

    public boolean call() {
        if (currentBet == 0) {
            return false;
        } else {
            Player player = playersInHand.get(currentPlayer);
            player.chips -= (currentBet - player.amountThisStreet);
            pot += (currentBet - player.amountThisStreet);
            player.amountThisStreet = currentBet;
            player.hasActed = true;
            cyclePlayer();
            checkNoMoreAction();
            return true;
        }
    }

    public void betRaise(int amount) {
        Player player = playersInHand.get(currentPlayer);
        currentBet = amount;
        player.chips -= (amount - player.amountThisStreet);
        pot += (amount - player.amountThisStreet);
        player.amountThisStreet = currentBet;
        player.hasActed = true;
        cyclePlayer();
    }

    private void cyclePlayer() {
        if (currentPlayer == playersInHand.size() - 1) {
            currentPlayer = 0;
        } else {
            currentPlayer++;
        }
    }

    private void checkNoMoreAction() {
        for (Player player : playersInHand) {
            if (!player.hasActed) {
                return;
            } else {
                if (player.amountThisStreet != currentBet) {
                    return;
                }
            }
        }
        nextStreet();
    }

    private void checkNoMorePlayers() {
        if (playersInHand.size() == 1) {
            MainActivity.game.endHand();
            System.out.println("made");
        }
    }

    private void nextStreet() {
        street++;
        if (street == 4) {
            MainActivity.game.endHand();
            return;
        }

        for (Player player : playersInHand) {
            player.amountThisStreet = 0;
            player.hasActed = false;
        }

        for (Player player : playersInHand) {
            //button still in hand
            if (player.isButton) {
                currentPlayer = 1;
                break;
            } else {
                currentPlayer = 0;
            }
        }
        currentBet = 0;
    }
}