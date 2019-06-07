package com.example.pokerhandtracker;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    List<Player> playersInHand;
    int currentPlayer = 3;

    int smallBlind = 5; //hardcoded
    int bigBlind = 10;

    List<Pot> pots = new ArrayList<>();
    int prevRaise = 0;
    int currentBet = bigBlind;
    int street = 0;

    String flop = "";
    String turn = "";
    String river = "";

    boolean isOver = false;
    Player winner;

    public Hand(List<Player> playersInHand) {
        this.playersInHand = playersInHand;

        playersInHand.get(1).amountThisStreet = smallBlind;
        playersInHand.get(1).chips -= 5;
        playersInHand.get(2).amountThisStreet = bigBlind;
        playersInHand.get(2).chips -= bigBlind;
        pots.add(new Pot(playersInHand, smallBlind + bigBlind));

        for (Player player : this.playersInHand) {
            player.hasActed = false;
        }
    }

    public void checkFold() {
        Player player = playersInHand.get(currentPlayer);

        
    }

//    public void checkFold() {
//        Player player = playersInHand.get(currentPlayer);
//        cyclePlayer();
//        //Big blind preflop check
//        if (!(player.amountThisStreet == bigBlind && !player.hasActed)) {
//            if (currentBet > 0) {
//                playersInHand.remove(player);
//                if (playersInHand.size() == 1) {
//                    currentPlayer = 0;
//                }
//                if (currentPlayer != 0) { //currentPlayer already cycled
//                    currentPlayer--;
//                }
//            }
//        }
//        player.hasActed = true;
//        checkNoMoreAction();
//        checkNoMorePlayers();
//    }

    public boolean call() {
        if (currentBet == 0) {
            return false;
        }

        Player player = playersInHand.get(currentPlayer);

        if (player.chips - (currentBet - player.amountThisStreet) < 0) {
            allIn(player);
        } else {
            player.chips -= (currentBet - player.amountThisStreet);
            addToPot(currentBet - player.amountThisStreet);
            player.amountThisStreet = currentBet;
            player.hasActed = true;
            cyclePlayer();
            checkNoMoreAction();
        }

        return true;
    }

    public boolean betRaise(int amount) {
        if (!checkValidBet(amount)) {
            return false;
        }

        prevRaise = amount - currentBet;
        Player player = playersInHand.get(currentPlayer);
        currentBet = amount;
        player.chips -= (amount - player.amountThisStreet);
        pot += (amount - player.amountThisStreet);
        player.amountThisStreet = currentBet;
        player.hasActed = true;
        cyclePlayer();

        return true;
    }

    private void addToPot(int amount) {
        pots.get(pots.size() - 1).potSize += amount;
    }

    private void allIn(Player player) {

    }

    private boolean checkValidBet(int amount) {
        Player player = playersInHand.get(currentPlayer);
        //min raise
        if (amount - currentBet < prevRaise) {
            return false;
        }

        //not enough chips
        if (amount - player.amountThisStreet > player.chips) {
            return false;
        }

        return true;
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