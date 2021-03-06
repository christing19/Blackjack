package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

// Represents a game of Blackjack
public class BlackjackGame implements Writable {

    public final ArrayList<String> suits =
            new ArrayList<>(Arrays.asList("Clubs", "Diamonds", "Hearts", "Spades"));
    private final Player player;
    private final ArrayList<Card> dealerHand;
    private final ArrayList<Card> playerHand;

    // EFFECTS: constructs a new round of Blackjack with a player and initializes empty hands for player and dealer
    public BlackjackGame() {
        this.player = new Player();
        this.playerHand = new ArrayList<>();
        this.dealerHand = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: starts the game by generating two random cards for player and dealer and adding the cards
    //          to their respective hands; adds starting hands to event log
    public void startGame() {
        Random randRank = new Random();
        Random randSuit = new Random();

        Card playerCard1 = new Card(randRank.nextInt(13) + 1, suits.get(randSuit.nextInt(suits.size())));
        Card playerCard2 = new Card(randRank.nextInt(13) + 1, suits.get(randSuit.nextInt(suits.size())));

        playerHand.add(playerCard1);
        playerHand.add(playerCard2);
        EventLog.getInstance().logEvent(new Event("Player starts with a " + playerHand.get(0).getCardString()
                + " and a " + playerHand.get(1).getCardString() + " for a count of " + getHandInValue(playerHand)));

        Card dealerCard1 = new Card(randRank.nextInt(13) + 1, suits.get(randSuit.nextInt(suits.size())));
        Card dealerCard2 = new Card(randRank.nextInt(13) + 1, suits.get(randSuit.nextInt(suits.size())));

        dealerHand.add(dealerCard1);
        dealerHand.add(dealerCard2);
        EventLog.getInstance().logEvent(new Event("Dealer starts with a " + dealerHand.get(0).getCardString()
                + " and a " + dealerHand.get(1).getCardString() + " for a count of " + getHandInValue(dealerHand)));
    }

    // REQUIRES: hand is a non-empty list
    // EFFECTS: returns the list of cards in the hand with rank and suit in String type
    public ArrayList<String> getHandInString(ArrayList<Card> hand) {
        ArrayList<String> handInString = new ArrayList<>();
        for (Card card : hand) {
            handInString.add(card.getCardString());
        }
        return handInString;
    }

    // REQUIRES: hand is a non-empty list
    // EFFECTS: adds the values of all the current cards in the hand; accounts for aces and will
    //          deduct 10 from sum (i.e. treating ace as 1) if ace as 11 causes player to go over 21
    public int getHandInValue(ArrayList<Card> hand) {
        int handValue = 0;
        int numAces = 0;
        for (Card card : hand) {
            if (card.getCardRank() == 1) {
                numAces++;
            }
            handValue += card.getCardValue();
        }

        while (numAces > 0 && handValue > 21) {
            handValue -= 10;
            numAces--;
        }

        return handValue;
    }

    // EFFECTS: returns dealer's first card
    public ArrayList<String> getDealerFirstCardString() {
        ArrayList<String> dealerFirstCard = new ArrayList<>();
        dealerFirstCard.add(dealerHand.get(0).getCardString());
        return dealerFirstCard;
    }

    // REQUIRES: hand is a non-empty list
    // EFFECTS: checks if hand has a Blackjack after initial cards are handed out
    public boolean checkBlackjack(ArrayList<Card> hand) {
        return getHandInValue(hand) == 21 && getHandInString(hand).size() == 2;
    }

    // REQUIRES: hand is a non-empty list
    // EFFECTS: checks if hand has bust (value > 21)
    public boolean checkBust(ArrayList<Card> hand) {
        return getHandInValue(hand) > 21;
    }

    // REQUIRES: hand is a non-empty list
    // MODIFIES: this
    // EFFECTS: adds a new card to the current hand, adds hit to event log
    public void hit(ArrayList<Card> hand) {
        Random randRank = new Random();
        Random randSuit = new Random();
        int randIndex = randSuit.nextInt(suits.size());

        Card hitCard = new Card(randRank.nextInt(13) + 1, suits.get(randIndex));
        hand.add(hitCard);

        String person;
        if (hand == dealerHand) {
            person = "Dealer";
            if (dealerHand.size() <= 3) {
                EventLog.getInstance().logEvent(new Event("Player stands on a count of "
                        + getHandInValue(playerHand)));
            }
        } else {
            person = "Player";
        }
        EventLog.getInstance().logEvent(new Event(person + " hits a " + hitCard.getCardString()
                + " for a count of " + getHandInValue(hand)));
    }

    // EFFECTS: compares total value in player's hand vs. dealer's hand;
    //          returns result of the round from the player's perspective
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

    // MODIFIES: this
    // EFFECTS: adds dealer's result to event log and updates player balance based on result of the round;
    //          if player has won, rewards player by amount equivalent to bet;
    //          also rewards player with 3:2 of bet if player wins with a Blackjack
    public void updatePlayerBalance() {
        String result;
        if (checkBust(dealerHand)) {
            result = "busts";
        } else {
            result = "stands";
        }
        EventLog.getInstance().logEvent(new Event("Dealer " + result + " on a count of "
                + getHandInValue(dealerHand)));

        if (determineWinner().equals("You have a Blackjack!")) {
            player.addBalance(player.getBet() * 5 / 2);
        } else if (determineWinner().equals("You win!")) {
            player.addBalance(player.getBet() * 2);
        } else if (determineWinner().equals("Push on this round.")) {
            player.addBalance(player.getBet());
        }
    }

    // MODIFIES: this
    // EFFECTS: clears player's and dealer's hand of existing cards to prepare for new round, adds action to event log
    public void clearHand() {
        playerHand.clear();
        dealerHand.clear();
        EventLog.getInstance().logEvent(new Event("Both hands cleared to begin a new round"));
    }

    // MODIFIES: this
    // EFFECTS: adds specific card to player's hand
    public void addCard(ArrayList<Card> hand, Card c) {
        hand.add(c);
    }

    // EFFECTS: returns player's hand
    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    // EFFECTS: returns dealer's hand
    public ArrayList<Card> getDealerHand() {
        return dealerHand;
    }

    // EFFECTS: returns player; allows access to player fields
    public Player getPlayer() {
        return player;
    }

    // Sections of this method are adapted from:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: adds player's balance, bet, hand and dealer's hand into JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Player Bet", player.getBet());
        json.put("Player Balance", player.getBalance());
        json.put("Player Hand", playerHand);
        json.put("Dealer Hand", dealerHand);
        return json;
    }
}
