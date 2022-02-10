package model;

// Represents a player in a game of Blackjack
public class Player {

    private int balance;
    private int bet;

    // EFFECTS: constructs a player with starting account balance = $1,000 and current bet = $0
    public Player() {
        this.balance = 1000;
        this.bet = 0;
    }

    // REQUIRES: 0 < amount <= balance
    // MODIFIES: this
    // EFFECTS: applies given amount as current bet for current round of play
    public void makeBet(int amount) {
        bet = amount;
        balance -= bet;
    }

    // REQUIRES: bet <= balance
    // MODIFIES: this
    // EFFECTS: doubles player's current bet for the round by applying amount equivalent to player's original bet
    public boolean doubleDown() {
        if (balance >= bet) {
            balance -= bet;
            bet += bet;
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

    // EFFECTS: returns player's bet for the current round
    public int getBet() {
        return bet;
    }
}
