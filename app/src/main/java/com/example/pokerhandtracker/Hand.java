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
    List<Action> actions = new ArrayList<>();
    int street = 0;

    String flop = "";
    String turn = "";
    String river = "";

    boolean isOver = false;
    Player winner;

    public Hand(List<Player> playersInHand) {
        this.playersInHand = playersInHand;

        playersInHand.get(0).isButton = true;
        playersInHand.get(1).amountThisStreet = smallBlind;
        playersInHand.get(1).chips -= 5;
        playersInHand.get(2).amountThisStreet = bigBlind;
        playersInHand.get(2).chips -= bigBlind;
        pots.add(new Pot(playersInHand, smallBlind + bigBlind));
    }

    public void checkFold() {
        Player player = playersInHand.get(currentPlayer);

        cyclePlayer();

        if (!(player.amountThisStreet == bigBlind && player.actions[street].isEmpty())) {
            if (currentBet > 0) {
                player.actions[street].add(new Fold(player));
                playersInHand.remove(player);
                if (playersInHand.size() == 1) {
                    currentPlayer = 0;
                }
                if (currentPlayer != 0) {
                    currentPlayer--;
                }
            } else {
                player.actions[street].add(new Check(player));
            }
        }

        checkNoMoreAction();
        checkNoMorePlayers();
    }

    public boolean call() {
        if (currentBet == 0) {
            return false;
        }

        Player player = playersInHand.get(currentPlayer);

        player.actions[street].add(new Call(player));
        cyclePlayer();
        checkNoMoreAction();

        return true;
    }

//    public boolean call() {
//        if (currentBet == 0) {
//            return false;
//        }
//
//        Player player = playersInHand.get(currentPlayer);
//
//        if (player.chips - (currentBet - player.amountThisStreet) < 0) {
//            allIn(player);
//        } else {
//            player.chips -= (currentBet - player.amountThisStreet);
//            addToPot(currentBet - player.amountThisStreet);
//            player.amountThisStreet = currentBet;
//            cyclePlayer();
//            checkNoMoreAction();
//        }
//
//        return true;
//    }

    public boolean betRaise(int amount) {
        if (checkValidBet(amount)) {
            return false;
        }

        Player player = playersInHand.get(currentPlayer);

        player.actions[street].add(new Bet(amount, player));
        cyclePlayer();

        return true;
    }

//    public boolean betRaise(int amount) {
//        if (!checkValidBet(amount)) {
//            return false;
//        }
//
//        prevRaise = amount - currentBet;
//        Player player = playersInHand.get(currentPlayer);
//        currentBet = amount;
//        player.chips -= (amount - player.amountThisStreet);
//        pot += (amount - player.amountThisStreet);
//        player.amountThisStreet = currentBet;
//        cyclePlayer();
//
//        return true;
//    }

    private void addToPot(int amount) {
        pots.get(pots.size() - 1).potSize += amount;
    }

    private void allIn(Player player) {

    }

    private void computeStreet() {
        Pot mainPot = new Pot(smallBlind + bigBlind);
        mainPot.betAmount = 10;

        for (int i = 0; i < actions.size(); i++) {
            Action action = actions.get(i);
            if (action instanceof Bet) {
                //Player player = ((Bet) action).player;
                Pot lastPot = pots.get(pots.size() - 1);
                lastPot.potSize += ((Bet) action).amount;
                lastPot.betAmount = (((Bet) action).amount - lastPot.betAmount);
            } else if (action instanceof Call) {
                Player player = ((Call) action).player;
                Pot lastPot = pots.get(pots.size() - 1);
                Pot sidePot;

                if (((Call) action).player.chips > lastPot.betAmount) { //no sidepot needed
                    lastPot.potSize += lastPot.betAmount;
                    lastPot.players.add(player);
                } else { //sidepot needed
                    if (lastPot.players.contains(player)) { //if player is already in pot
                        lastPot.potSize -= ((lastPot.betAmount - player.chips) * lastPot.players.size()) - 1;
                        sidePot = new Pot((lastPot.betAmount - player.chips) * lastPot.players.size() - 1);
                    } else { //if player is entering pot
                        lastPot.potSize -= ((lastPot.betAmount - player.chips) * lastPot.players.size());
                        lastPot.players.add(player);
                        sidePot = new Pot((lastPot.betAmount - player.chips) * lastPot.players.size());
                    }
                }
            }
        }
    }

    private int numActions() {
        int actionCount = 0;

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
            if (player.actions[street].isEmpty()) {
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

        if (playersInHand.get(0).isButton) {
            currentPlayer = 1;
        } else {
            currentPlayer = 0;
        }
        currentBet = 0;
    }
}