package ui;

import model.BlackjackGame;

import java.util.Scanner;

// Represents a Blackjack game application
public class BlackjackApp {

    private static final String PLAY_CMD = "play";
    private static final String QUIT_CMD = "quit";

    private BlackjackGame game;

    private boolean newGame;
    private Scanner input;
    private String command;

    // EFFECTS: runs the Blackjack application
    public BlackjackApp() {
        runBlackjack();
    }

    // Sections of this method are adapted from:
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp
    // MODIFIES: this
    // EFFECTS: processes user input to play a game or quit the application
    private void runBlackjack() {
        System.out.println("Hello and welcome to the Blackjack table!");
        System.out.println("My name is Steve Wynn and I will be your dealer today.");

        newGame = true;
        initialize();
        homeScreen();

        while (newGame) {
            command = input.next();
            command = command.toLowerCase();

            if (command.equals(QUIT_CMD)) {
                newGame = false;
            } else if (command.equals(PLAY_CMD)) {
                playGame();
            } else {
                System.out.println("\nPlease enter a valid command.");
            }
        }
        System.out.println("\nCome again soon!");
    }

    // MODIFIES: this
    // EFFECTS: initializes a new game
    private void initialize() {
        game = new BlackjackGame();
        input = new Scanner(System.in);
    }

    // EFFECTS: loads instructions and available selections for launching application
    private void homeScreen() {
        System.out.println("\nPlease select one of the following options:");
        System.out.println("\t- Enter '" + PLAY_CMD + "' to start a new game.");
        System.out.println("\t- Enter '" + QUIT_CMD + "' to quit the application.");
    }

    // MODIFIES: this
    // EFFECTS: begins a round of Blackjack by asking for player bets and handing out two cards each
    private void playGame() {
        System.out.println("\nYou have a current balance of $" + game.getPlayer().getBalance()
                + ". Please place a bet.");
        input = new Scanner(System.in);

        while (!input.hasNextInt()) {
            System.out.println("\nThat is an invalid bet. Please try again.");
            input.nextLine();
        }

        int bet = input.nextInt();
        while (bet <= 0 || bet > game.getPlayer().getBalance()) {
            System.out.println("\nThat is an invalid bet. Please try again.");
            bet = input.nextInt();
        }

        game.getPlayer().makeBet(bet);
        System.out.println("\nYou have made a bet of $" + game.getPlayer().getBet() + ". Good luck.");

        game.startGame();
        System.out.println("\nYou have " + game.getHandInString(game.getPlayerHand()) + ", for a total count of "
                + game.getHandInValue(game.getPlayerHand()) + ".");
        checkBlackjack();
    }

    // MODIFIES: this
    // EFFECTS: checks if player or dealer has a Blackjack; otherwise game continues
    private void checkBlackjack() {
        if (game.checkBlackjack(game.getDealerHand()) || game.checkBlackjack(game.getPlayerHand())) {
            System.out.println("\nDealer shows " + game.getHandInString(game.getDealerHand()) + ".");
            endGame();
        } else {
            System.out.println("Dealer shows " + game.getDealerFirstCardString() + ".");
            System.out.println("\nWould you like to hit, stand, or double?");
            playerAction();
        }
    }

    // MODIFIES: this
    // EFFECTS: asks for player input on whether they would like to hit, stand, or double
    private void playerAction() {
        input = new Scanner(System.in);
        command = input.next();
        command = command.toLowerCase();

        switch (command) {
            case "hit":
                playerHit();
                break;
            case "stand":
                dealerTurn();
                break;
            case "double":
                playerDouble();
                break;
            default:
                System.out.println("\nPlease enter a valid command.");
                playerAction();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds card to player's list of cards
    private void playerHit() {
        game.hit(game.getPlayerHand());

        if (game.checkBust(game.getPlayerHand())) {
            System.out.println("\nYou have " + game.getHandInString(game.getPlayerHand()) + " for a total count of "
                    + game.getHandInValue(game.getPlayerHand()) + " and have bust.");
            endGame();
        } else if (game.getHandInValue(game.getPlayerHand()) == 21) {
            System.out.println("\nYou have " + game.getHandInString(game.getPlayerHand()) + " for a total count of "
                    + game.getHandInValue(game.getPlayerHand()) + ". Good hit.");
            dealerTurn();
        } else {
            System.out.println("\nYou have " + game.getHandInString(game.getPlayerHand()) + " for a total count of "
                    + game.getHandInValue(game.getPlayerHand()) + ". Would you like to hit or stand?");
            playerNextAction();
        }
    }

    // MODIFIES: this
    // EFFECTS: hits dealer's hand until count is either within range of 17 - 21 or busts
    private void dealerTurn() {
        System.out.println("\nDealer has " + game.getHandInString(game.getDealerHand()) + ", for a total count of "
                + game.getHandInValue(game.getDealerHand()) + ".");

        while (game.getHandInValue(game.getDealerHand()) < 17) {
            game.hit(game.getDealerHand());
            System.out.println("Dealer hits and gets " + game.getHandInString(game.getDealerHand())
                    + ", for a total count of " + game.getHandInValue(game.getDealerHand()) + ".");
        }

        if (game.checkBust(game.getDealerHand())) {
            System.out.println("\nDealer busts.");
        }
        endGame();
    }

    // MODIFIES: this
    // EFFECTS: doubles player's original wager and hits hand once, and determines if hand is valid for dealer's turn
    private void playerDouble() {
        if (game.getPlayer().doubleDown()) {
            System.out.println("\nYour bet is now $" + game.getPlayer().getBet() + ".");
        } else {
            System.out.println("\nYou have an insufficient balance. Please try again.");
            playerNextAction();
        }

        game.hit(game.getPlayerHand());

        if (game.checkBust(game.getPlayerHand())) {
            System.out.println("You have " + game.getHandInString(game.getPlayerHand()) + " for a total count of "
                    + game.getHandInValue(game.getPlayerHand()) + " and have bust.");
            endGame();
        } else {
            System.out.println("You have " + game.getHandInString(game.getPlayerHand()) + " for a total count of "
                    + game.getHandInValue(game.getPlayerHand()) + ".");
            dealerTurn();
        }
    }

    // MODIFIES: this
    // EFFECTS: asks for player input whether they would like to hit another time or stand
    private void playerNextAction() {
        input = new Scanner(System.in);
        command = input.next();
        command = command.toLowerCase();

        switch (command) {
            case "hit":
                playerHit();
                break;
            case "stand":
                dealerTurn();
                break;
            default:
                System.out.println("\nPlease enter a valid command.");
                playerNextAction();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: determines winner of the round and updates player's balance accordingly; prompts user to start a new
    //          game or to quit the application
    private void endGame() {
        System.out.println(game.determineWinner());
        game.updatePlayerBalance();
        game.clearHand();

        if (game.getPlayer().getBalance() == 0) {
            System.out.println("\nYour new balance is now $0. Please reload the game to replenish your balance.");
            newGame = false;
        } else {
            System.out.println("\nYour new balance is $" + game.getPlayer().getBalance()
                    + ". Would you like to play again?");
            homeScreen();
        }
    }
}

