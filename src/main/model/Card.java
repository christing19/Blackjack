package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

// Represents a card within the game of Blackjack
public class Card {

    public final ArrayList<String> suits =
            new ArrayList<>(Arrays.asList("Clubs", "Diamonds", "Hearts", "Spades"));
    private final int cardRank;
    private final int cardValue;
    private final String cardSuit;

    // EFFECTS: constructs a card with a random rank in range of [1,13], where
    //          1 is an Ace and 11, 12, and 13 are J, Q, K, respectively;
    //          constructor also sets card value based on rank, where
    //          Ace is worth 11 and J, Q, K are each worth 10;
    //          constructor also sets a random card suit in ArrayList<String> suits
    public Card() {
        Random randRank = new Random();
        this.cardRank = randRank.nextInt(13) + 1;

        if (this.cardRank >= 11) {
            this.cardValue = 10;
        } else if (this.cardRank == 1) {
            this.cardValue = 11;
        } else {
            this.cardValue = this.cardRank;
        }
        Random randSuit = new Random();
        int randIndex = randSuit.nextInt(suits.size());
        this.cardSuit = suits.get(randIndex);
    }

    // EFFECTS: returns face value of card in range of [1,13]
    public int getCardRank() {
        return cardRank;
    }

    // EFFECTS: returns in-game value of card in range of [1,10]
    public int getCardValue() {
        return cardValue;
    }

    // EFFECTS: returns suit of card; will be one of: clubs, diamonds, hearts, spades
    public String getCardSuit() {
        return cardSuit;
    }

    // EFFECTS: returns string of card rank and card suit
    public String getCardString() {

        String cardRankString;
        switch (cardRank) {
            case 1:
                cardRankString = "A";
                break;
            case 11:
                cardRankString = "J";
                break;
            case 12:
                cardRankString = "Q";
                break;
            case 13:
                cardRankString = "K";
                break;
            default:
                cardRankString = String.valueOf(cardRank);
        }
        return cardRankString + " of " + getCardSuit();
    }
}

