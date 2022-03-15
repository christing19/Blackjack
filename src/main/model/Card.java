package model;

// Represents a card in the game of Blackjack
public class Card {

    private final int cardRank;
    private final int cardValue;
    private final String cardSuit;

    // REQUIRES: card rank is in range of [1,13]; card suit is one of: "Clubs", "Diamonds", "Hearts", "Spades"
    // EFFECTS: constructs a card with a rank (i.e. face value) in range of [1,13], where
    //          1 is an Ace, 11, 12, and 13 are J, Q, K, respectively;
    //          constructor also sets card value based on rank, where
    //          Ace is worth 11 and J, Q, K are each worth 10;
    //          finally, constructor also sets card suit based on suit
    public Card(int rank, String suit) {
        // card rank component
        this.cardRank = rank;

        // card value component
        if (this.cardRank >= 11) {
            this.cardValue = 10;
        } else if (this.cardRank == 1) {
            this.cardValue = 11;
        } else {
            this.cardValue = this.cardRank;
        }

        // card suit component
        this.cardSuit = suit;
    }

    // EFFECTS: returns card rank and card suit in String type
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

    // EFFECTS: returns face value of card in range of [1,13]
    public int getCardRank() {
        return cardRank;
    }

    // EFFECTS: returns in-game value of card in range of [1,10]
    public int getCardValue() {
        return cardValue;
    }

    // EFFECTS: returns suit of card; will be one of: clubs, diamonds, hearts, spades from "suits"
    public String getCardSuit() {
        return cardSuit;
    }

    public int getCardSuitNum() {
        switch (cardSuit) {
            case "Clubs":
                return 1;
            case "Diamonds":
                return 2;
            case "Hearts":
                return 3;
            case "Spades":
                return 4;
        }
        return 0;
    }
}

