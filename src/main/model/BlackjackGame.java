package model;

import java.util.ArrayList;
import java.util.Random;

// Represents a game of Blackjack
public class BlackjackGame {

    private final Player player;
    private final ArrayList<Card> dealerHand;
    private final ArrayList<Card> playerHand;
    private int handValue;

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
        Random randRank = new Random();

        Card playerCard1 = new Card(randRank.nextInt(13) + 1);
        Card playerCard2 = new Card(randRank.nextInt(13) + 1);
        playerHand.add(playerCard1);
        playerHand.add(playerCard2);

        Card dealerCard1 = new Card(randRank.nextInt(13) + 1);
        Card dealerCard2 = new Card(randRank.nextInt(13) + 1);
        dealerHand.add(dealerCard1);
        dealerHand.add(dealerCard2);
    }

    // EFFECTS: adds specific card to player's hand (value and suits)
    public void setPlayerCard(Card c) {
        playerHand.add(c);
    }

    // EFFECTS: adds specific card to player's hand (value and suits)
    public void setDealerCard(Card c) {
        dealerHand.add(c);
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
        ArrayList<String> handString = new ArrayList<>();
        handString.add(dealerHand.get(0).getCardString());
        return handString;
    }

    // EFFECTS: returns the list of cards in the hand (value and suits in string type)
    public ArrayList<String> getHandInString(ArrayList<Card> hand) {
        ArrayList<String> displayHand = new ArrayList<>();
        for (Card card : hand) {
            displayHand.add(card.getCardString());
        }
        return displayHand;
    }

    // EFFECTS: adds the values of all the current cards in the player's hand
    public int getHandInValue(ArrayList<Card> hand) {
        handValue = 0;
        for (Card card : hand) {
            handValue += card.getCardValue();
        }
        return handValue;
    }

//    // EFFECTS: checks if current list of cards contains an Ace
//    public boolean hasAce(ArrayList<Card> hand) {
//        for (Card card : hand) {
//            if (card.getCardRank() == 1) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    // REQUIRES: list of card includes an Ace
//    // EFFECTS: searches through current list of cards and returns the card object with rank = 1 (or Ace)
//    public Card findAce(ArrayList<Card> hand) {
//        for (Card card : hand) {
//            if (card.getCardRank() == 1) {
//                return card;
//            }
//        }
//        return null;
//    }
//
    // EFFECTS: sets value of Ace to 1 if current sum of values in hand with Ace as 11 is >21
    public void aceValueSwitch(ArrayList<Card> hand) {
        int numAces = 0;
        for (Card card : hand) {
            if (card.getCardRank() == 1) {
                numAces++;
            }

            while (numAces > 1 && getHandInValue(hand) > 21) {
                handValue -= 10;
                numAces--;
            }
        }
    }

    // EFFECTS: checks if dealer or player has a Blackjack after first cards are handed out
    public boolean checkBlackjack(ArrayList<Card> hand) {
        return getHandInValue(hand) == 21 && getHandInString(hand).size() == 2;
    }

    // EFFECTS: checks if dealer or player has bust
    public boolean checkBust(ArrayList<Card> hand) {
        return getHandInValue(hand) > 21;
    }

    // MODIFIES: this
    // EFFECTS: adds a new card to the current list of cards
    public void hit(ArrayList<Card> hand) {
        Random randRank = new Random();
        Card hitCard = new Card(randRank.nextInt(13) + 1);
        hand.add(hitCard);
    }

    // EFFECTS: compares total value in player's hand vs. dealer's hand,
    //          returns person with the highest total value without going over 21
    public String determineWinner() {
        if (checkBlackjack(dealerHand) && checkBlackjack(playerHand)) {
            return "Push on this round.";
        } else if (checkBlackjack(dealerHand)) {
            return "Dealer has a Blackjack. Better luck next game.";
        } else if (checkBlackjack(playerHand)) {
            return "You have a Blackjack!";
        } else if (checkBust(playerHand)) {
            return "Dealer wins.";
        } else if (checkBust(dealerHand)) {
            return "You win!";
        } else if (getHandInValue(dealerHand) > getHandInValue(playerHand)) {
            return "Dealer wins.";
        } else if (getHandInValue(dealerHand) == getHandInValue(playerHand)) {
            return "Push on this round.";
        } else {
            return "You win!";
        }
    }

    // MODIFIES: player
    // EFFECTS: updates player balance by amount equivalent to bet if player has won a round
    public void updatePlayerBalance() {
        if (determineWinner().equals("You have a Blackjack!")) {
            player.addBalance(player.getBet() * 5 / 2);
        } else if (determineWinner().equals("You win!")) {
            player.addBalance(player.getBet() * 2);
        } else if (determineWinner().equals("Push on this round.")) {
            player.addBalance(player.getBet());
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
