package com.example.pokerhandtracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Hand {
    List<Player> playersInHand;
    int allInPlayers = 0;
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

        //initialise pot
        Pot mainPot = new Pot();
        for (Player p : playersInHand) {
            mainPot.playerContribution.put(p, 0);
        }
        mainPot.playerContribution.put(playersInHand.get(1), smallBlind);
        mainPot.playerContribution.put(playersInHand.get(2), bigBlind);
        pots.add(mainPot);
    }

    public void checkFold() {
        Player player = playersInHand.get(currentPlayer);

        cyclePlayer();

        if (actions.size() == playersInHand.size() - 1 && currentBet == bigBlind) {
            actions.add(new Check(player));
        } else if (currentBet > 0) {
            actions.add(new Fold(player));
            playersInHand.remove(player);

            if (playersInHand.size() == 1) {
                currentPlayer = 0;
            }

            if (currentPlayer != 0) {
                currentPlayer--;
            }
        } else {
            actions.add(new Check(player));
        }

        checkNoMoreAction();
        checkNoMorePlayers();
    }

    public boolean call() {
        if (currentBet == 0) {
            return false;
        }

        Player player = playersInHand.get(currentPlayer);

        if (currentBet - player.amountThisStreet > player.chips) {
            player.amountThisStreet += player.chips;
            actions.add(new Call(player, true));
        } else {
            player.amountThisStreet = currentBet;
            actions.add(new Call(player, false));
        }

        cyclePlayer();
        checkNoMoreAction();

        return true;
    }

    public boolean betRaise(int amount) {
        if (!checkValidBet(amount)) {
            return false;
        }

        Player player = playersInHand.get(currentPlayer);

        currentBet = amount;
        player.amountThisStreet = amount;
        if (player.amountThisStreet - player.chips == 0) {
            actions.add(new Bet(amount, player, true));
        } else {
            actions.add(new Bet(amount, player, false));
        }

        cyclePlayer();

        return true;
    }

    private void computeStreet() {
        for (int i = 0; i < actions.size(); i++) {
            Action action = actions.get(i);
            Pot lastPot = pots.get(pots.size() - 1);
            Map<Player, Integer> betMap = lastPot.playerContribution;

            if (action instanceof Bet) {
                Player player = ((Bet) action).player;

                betMap.put(player, ((Bet) action).amount);
            } else if (action instanceof Call) {
                Player player = ((Call) action).player;

                int highestContribution = 0;
                for (Player p : lastPot.playerContribution.keySet()) {
                    if (betMap.get(p) > highestContribution) {
                        highestContribution = betMap.get(p);
                    }
                }

                if (player.chips > highestContribution) {
                    betMap.put(player, highestContribution);
                } else {
                    betMap.put(player, player.chips);
                }
            } else if (action instanceof Check || action instanceof Fold) {
                return;
            }
        }

        //create sidepots
        for (int i = pots.size() - 1; i < pots.size(); i++) {
            Pot mainPot = pots.get(i);
            if (!checkPotIsEqual(mainPot)) {
                Pot sidePot = new Pot();
                Player lowestContributor = getLowestContribution(mainPot);

                for (Player player : mainPot.playerContribution.keySet()) {
                    if (player != lowestContributor) {
                        sidePot.playerContribution.put(player, mainPot.playerContribution.get(player) - mainPot.playerContribution.get(lowestContributor));
                    }
                }

                for (Player player : mainPot.playerContribution.keySet()) {
                    mainPot.playerContribution.put(player, mainPot.playerContribution.get(lowestContributor));
                }

                pots.add(sidePot);
            }
        }

        actions.clear();
    }

    private boolean checkPotIsEqual(Pot pot) {
        int amount = pot.playerContribution.get(0);
        for (Player p : pot.playerContribution.keySet()) {
            if (pot.playerContribution.get(p) != amount) {
                return false;
            }
        }
        return true;
    }

    private Player getLowestContribution(Pot pot) {
        Player lowestContributor = null;
        int lowestContribution = Integer.MAX_VALUE;
        for (Player p : pot.playerContribution.keySet()) {
            if (lowestContribution > pot.playerContribution.get(p)) {
                lowestContribution = pot.playerContribution.get(p);
                lowestContributor = p;
            }
        }
        return lowestContributor;
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
        int checkCount = 0;
        int callCount = 0;
        int callsRequired = playersInHand.size() - 1;
        int allInPlayers = this.allInPlayers;
        boolean aggressorAllIn = false;
        boolean cannotLimp = true;

        if (street == 0) {
            cannotLimp = false;
        }

        for (Action action : actions) {
            if (action instanceof Bet) {
                cannotLimp = true;
                if (((Bet) action).allIn) {
                    if (aggressorAllIn) {
                        allInPlayers++;
                    } else {
                        aggressorAllIn = true;
                    }
                } else if (aggressorAllIn) {
                    aggressorAllIn = false;
                    allInPlayers++;
                }
                callCount = 0;
            } else if (action instanceof Call) {
                if (((Call) action).allIn) {
                    allInPlayers++;
                } else {
                    callCount++;
                }
            } else if (action instanceof Check) {
                checkCount++;
            } else if (action instanceof Fold) {
                callsRequired--;
                if (action != actions.get(actions.size() - 1)) {
                    callsRequired++;
                    if (playersInHand.get(playersInHand.size() - 1) != ((Fold) action).player) {
                        return;
                    }
                }
            }

            if (!cannotLimp) {
                if (callCount == callsRequired && checkCount == 1) {
                    nextStreet();
                }
            } else if (callCount == callsRequired - allInPlayers || checkCount == callsRequired + 1 - allInPlayers) {
                nextStreet();
                this.allInPlayers = allInPlayers;
            }
        }
    }

    private void checkNoMorePlayers() {
        if (playersInHand.size() == 1) {
            MainActivity.game.endHand();
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