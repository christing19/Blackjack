package model;

// Represents a player in a game of Blackjack
public class Player {

    private int balance;
    private int bet;

    // REQUIRES: player's name to be a non-empty string
    // EFFECTS: constructs a player with the given name and starting account balance = $1000 and current bet = $0
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
    public boolean  doubleDown() {
        if (balance >= bet) {
            balance -= bet;
            bet += bet;
            return true;
        } else {
            return false;
        }
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(double amount) {
        balance += amount;
    }

    public int getBet() {
        return bet;
    }
}
