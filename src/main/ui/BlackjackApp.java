package ui;

import exceptions.IllegalBetException;
import model.BlackjackGame;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Represents a Blackjack game application
public class BlackjackApp {

    private static final String JSON_LOCATION = "./data/BlackjackGame.json";
    private static final String PLAY_CMD = "play";
    private static final String QUIT_CMD = "quit";
    private static final String LOAD_CMD = "load";
    private static final String SAVE_CMD = "save";
    private static final String HIT_CMD = "hit";
    private static final String STAND_CMD = "stand";
    private static final String DOUBLE_CMD = "double";


    private BlackjackGame game;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private boolean newGame;
    private Scanner input;
    private String command;

    // EFFECTS: runs the Blackjack application
    public BlackjackApp() {
        jsonWriter = new JsonWriter(JSON_LOCATION);
        jsonReader = new JsonReader(JSON_LOCATION);
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
                printCards();
            } else if (command.equals(LOAD_CMD)) {
                loadBlackjackGame();
            } else if (command.equals(SAVE_CMD)) {
                saveBlackjackGame();
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
        System.out.println("\t- Enter '" + LOAD_CMD + "' to load a previously saved game.");
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

        try {
            game.getPlayer().makeBet(bet);
        } catch (IllegalBetException e) {
            //
        }
        System.out.println("\nYou have made a bet of $" + game.getPlayer().getBet() + ". Good luck.");
        game.startGame();
    }

    // EFFECTS: prints out cards in player's hand
    private void printCards() {
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
            System.out.println("\nWould you like to:");
            System.out.println("\t- '" + HIT_CMD + "' to take another card.");
            System.out.println("\t- '" + STAND_CMD + "' to complete your turn.");
            System.out.println("\t- '" + DOUBLE_CMD + "' to double your wager and take another card.");
            System.out.println("\t- '" + SAVE_CMD + "' to save your progress in the round and quit the application.");
            playerAction();
        }
    }

    // MODIFIES: this
    // EFFECTS: asks for player input on whether they would like to hit, stand, double, or save their progress
    private void playerAction() {
        input = new Scanner(System.in);
        command = input.next();
        command = command.toLowerCase();

        switch (command) {
            case HIT_CMD:
                playerHit();
                break;
            case STAND_CMD:
                dealerTurn();
                break;
            case DOUBLE_CMD:
                playerDouble();
                break;
            case SAVE_CMD:
                saveBlackjackGame();
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
                    + game.getHandInValue(game.getPlayerHand()) + ".");
            System.out.println("\nWould you like to:");
            System.out.println("\t- '" + HIT_CMD + "' to take another card.");
            System.out.println("\t- '" + STAND_CMD + "' to complete your turn.");
            System.out.println("\t- '" + SAVE_CMD + "' to save your progress in the round and quit the application.");
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
    // EFFECTS: asks for player input whether they would like to hit another time, stand, or save their progress
    private void playerNextAction() {
        input = new Scanner(System.in);
        command = input.next();
        command = command.toLowerCase();

        switch (command) {
            case HIT_CMD:
                playerHit();
                break;
            case STAND_CMD:
                dealerTurn();
                break;
            case SAVE_CMD:
                saveBlackjackGame();
                break;
            default:
                System.out.println("\nPlease enter a valid command.");
                playerNextAction();
                break;
        }
    }

    // Sections of this method are adapted from:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: saves the current round of Blackjack to file and quits the application
    private void saveBlackjackGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved game to " + JSON_LOCATION);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_LOCATION);
        }
        newGame = false;
    }

    // Sections of this method are adapted from:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads the saved round of Blackjack from file and prints out cards in player's hand
    private void loadBlackjackGame() {
        try {
            game = jsonReader.read();
            System.out.println("Loaded game from " + JSON_LOCATION);
        } catch (IOException e) {
            System.out.println("Unable to read to file: " + JSON_LOCATION);
        }

        if (game.getPlayerHand().isEmpty()) {
            playGame();
        } else {
            System.out.println("\nYour current balance is $" + game.getPlayer().getBalance() + " and your current bet"
                    + " for this round is $" + game.getPlayer().getBet() + ".");
        }
        printCards();
    }

    // MODIFIES: this
    // EFFECTS: determines winner of the round and updates player's balance accordingly; prompts user to start a new
    //          game, save their progress, or quit the application
    private void endGame() {
        System.out.println(game.determineWinner());
        game.updatePlayerBalance();
        game.clearHand();
        game.getPlayer().setBet(0);

        if (game.getPlayer().getBalance() == 0) {
            System.out.println("\nYour new balance is now $0. Please reload the game to replenish your balance.");
            newGame = false;
        } else {
            System.out.println("\nYour new balance is $" + game.getPlayer().getBalance()
                    + ". Would you like to play again?");
            homeScreen();
            System.out.println("\t- Enter '" + SAVE_CMD + "' to save your progress and quit the application.");
        }
    }
}

