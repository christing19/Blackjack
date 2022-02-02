package model;

import java.util.ArrayList;

// Represents a game of Blackjack
public class BlackjackGame {

    private Player player;
    private ArrayList<Card> dealerHand;
    private ArrayList<Card> playerHand;

    // EFFECTS: constructs a new round of Blackjack with empty hands (arrays) for dealer and player
    public BlackjackGame() {
        this.player = new Player();
        this.playerHand = new ArrayList<>();
        this.dealerHand = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: starts the game by generating two random cards for player and dealer and adding the cards
    //          to their respective hands
    public void startGame() {
        Card playerCard1 = new Card();
        Card playerCard2 = new Card();
        playerHand.add(playerCard1);
        playerHand.add(playerCard2);

        Card dealerCard1 = new Card();
        Card dealerCard2 = new Card();
        dealerHand.add(dealerCard1);
        dealerHand.add(dealerCard2);
    }

    // EFFECTS: returns the player's list of cards (value and suits)
    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    // EFFECTS: returns the dealer's list of cards (value and suits)
    public ArrayList<Card> getDealerHand() {
        return dealerHand;
    }

    // EFFECTS: returns the dealer's first card (value and suit)
    public ArrayList<String> getDealerFirstCard() {
        ArrayList<String> displayHand = new ArrayList<>();
        displayHand.add(dealerHand.get(0).getCardString());
        return displayHand;
    }

    // EFFECTS: returns the list of cards in the hand (value and suits in a string)
    public ArrayList<String> getHandString(ArrayList<Card> hand) {
        ArrayList<String> displayHand = new ArrayList<>();
        for (Card card : hand) {
            displayHand.add(card.getCardString());
        }
        return displayHand;
    }

    // EFFECTS: adds the values of all the current cards in the player's hand
    public int getHandValue(ArrayList<Card> hand) {
        int sum = 0;
        for (Card card : hand) {
            sum += card.getCardValue();
        }
        return sum;
    }

    public boolean hasAce(ArrayList<Card> hand) {
        for (Card card : hand) {
            if (card.getCardValue() == 11) {
                return true;
            }
        }
        return false;
    }

//    public int aceSumSwitch(ArrayList<Card> hand) {
//        if (hasAce(hand)) {
//            if (getHandValue(hand) > 21) {
//
//            }
//        }
//    }

    // EFFECTS: checks if dealer or player has a Blackjack after first cards are handed out
    public boolean checkBlackjack(ArrayList<Card> hand) {
        return getHandValue(hand) == 21 && getHandString(hand).size() == 2;
    }

    // EFFECTS: checks if dealer or player has bust
    public boolean checkBust(ArrayList<Card> hand) {
        return getHandValue(hand) > 21;
    }

    // MODIFIES: this
    // EFFECTS: adds a new card to the current list of cards
    public void hit(ArrayList<Card> hand) {
        Card hitCard = new Card();
        hand.add(hitCard);
    }

    // EFFECTS: compares total value in player's hand vs. dealer's hand,
    //          returns person with the highest total value without going over 21
    public String determineWinner() {
        if (checkBlackjack(dealerHand)) {
            return "Dealer has a Blackjack. Better luck next game.";
        } else if (checkBlackjack(dealerHand) && checkBlackjack(playerHand)) {
            return "Push on this round.";
        } else if (checkBlackjack(playerHand)) {
            return "You have a Blackjack!";
        } else if (checkBust(playerHand)) {
            return "Dealer wins.";
        } else if (checkBust(dealerHand)) {
            return "You win!";
        } else if (getHandValue(dealerHand) > getHandValue(playerHand)) {
            return "Dealer wins.";
        } else if (getHandValue(dealerHand) == getHandValue(playerHand)) {
            return "Push on this round.";
        } else if (getHandValue(dealerHand) < getHandValue(playerHand)) {
            return "You win!";
        }
        return "";
    }

    // MODIFIES: player
    // EFFECTS: updates player balance by amount equivalent to bet if player has won a round
    public void updatePlayerBalance() {
        if (determineWinner().equals("You have a Blackjack!")) {
            player.setBalance(player.getBet() * 2.5);
        } else if (determineWinner().equals("You win!")) {
            player.setBalance(player.getBet() * 2);
        } else if (determineWinner().equals("Push on this round.")) {
            player.setBalance(player.getBet());
        }
    }

    // EFFECTS: clears hand of existing cards
    public void clearHand(ArrayList<Card> hand) {
        hand.clear();
    }

    // EFFECTS: returns player
    public Player getPlayer() {
        return player;
    }

}
