package model;

import exceptions.IllegalBetException;

// Represents a player in a game of Blackjack
public class Player {

    private int balance;
    private int bet;

    // EFFECTS: constructs a player with starting account balance = $1,000 and current bet = $0
    public Player() {
        this.balance = 1000;
        this.bet = 0;
    }

    // MODIFIES: this
    // EFFECTS: applies given amount as current bet for current round of play and adds bet to event log
    //          throws IllegalBetException if given amount is < 0 or > current balance
    public void makeBet(int amount) throws IllegalBetException {
        if (amount < 0 || amount > balance) {
            throw new IllegalBetException();
        }

        bet = amount;
        balance -= bet;

//        EventLog.getInstance().logEvent(new Event("Player makes a bet of $" + getBet()
//                + " to start the round"));
    }

    // REQUIRES: bet <= balance
    // MODIFIES: this
    // EFFECTS: doubles player's current bet for the round by applying amount equivalent to player's original bet
    //          and adds double down to event log
    public boolean doubleDown() {
        if (balance >= bet) {
            balance -= bet;
            bet += bet;

//            EventLog.getInstance().logEvent(new Event("Player doubles down for a total bet of $" + getBet()
//                    + " for this round"));
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: adds amount to player's current balance
    public void addBalance(int amount) {
        balance += amount;
    }

    // EFFECTS: returns player's current balance
    public int getBalance() {
        return balance;
    }

    // EFFECTS: sets player's current balance to given amount
    public void setBalance(int amount) {
        balance = amount;
    }

    // EFFECTS: returns player's bet for the current round
    public int getBet() {
        return bet;
    }

    // EFFECTS: sets player's current bet to given amount
    public void setBet(int amount) {
        bet = amount;
    }
}
