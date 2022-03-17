package ui;

import exceptions.IllegalBetException;
import model.BlackjackGame;
import model.Card;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;

// Represents the main window in which the Blacjack game is played
public class GameUI extends JFrame implements ActionListener {

    private BlackjackGame game;
    private JFrame gameFrame;
    private ImagePanel image;
    private JLabel faceDownCard;
    private GridBagConstraints gc;

    private JButton hitBtn;
    private JButton standBtn;
    private JButton doubleBtn;
    private JButton saveBtn;

    private JPanel dealerPanel;
    private JPanel playerPanel;

    private JLabel dealerText;
    private JLabel playerText;
    private JLabel balanceMsg;
    private JLabel betMsg;

    private JPanel actionPanel;
    private JPanel infoPanel;
    private JsonWriter jsonWriter;

    // Sections of this method are adapted from:
    // https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    // gameBackground image taken from: https://stock.adobe.com/sk/search/images?k=blackjack+table&asset_id=93364974
    // EFFECTS: sets up window in which the Blackjack game will be played
    public GameUI(BlackjackGame game) {
        this.game = game;
        jsonWriter = new JsonWriter(BlackjackUI.JSON_LOCATION);

        gameFrame = new JFrame("Blackjack Game Simulator");
        image = new ImagePanel(new ImageIcon("./images/gameBackground.jpg").getImage());
        image.setLayout(new GridBagLayout());
        gc = new GridBagConstraints();

        gameFrame.add(image);
        gameFrame.setVisible(true);
        gameFrame.setSize(BlackjackUI.WIDTH, BlackjackUI.HEIGHT);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setResizable(false);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeGameUI();
    }

    // MODIFIES: this
    // EFFECTS: initializes various game panels, including dealer / player hands and action / info panels
    public void initializeGameUI() {
        if (game.getPlayerHand().isEmpty()) {
            game.startGame();
            initializeBetPopOut();
        } else {
            JOptionPane.showMessageDialog(gameFrame, "Successfully loaded previously saved game. \n"
                    + "Your balance is $" + NumberFormat.getIntegerInstance().format(game.getPlayer().getBalance())
                    + " and your current bet for this round is $"
                    + NumberFormat.getIntegerInstance().format(game.getPlayer().getBet()) + ".");
        }

        initializeDealerPanel();
        initializePlayerPanel();
        initializeActionPanel();

        if (game.getPlayerHand().size() > 2) {
            doubleBtn.setVisible(false);
        }

        initializeInfoPanel();
        image.revalidate();
        image.repaint();
    }

    // MODIFIES: Player
    // EFFECTS: takes user input for bet and sets player bet to given input;
    //          prompts user to re-enter bet input if NumberFormatException or IllegalBetException is caught
    public void initializeBetPopOut() {
        int bet;

        do {
            try {
                bet = Integer.parseInt(JOptionPane.showInputDialog(gameFrame,
                        "Please make a bet.\nYour balance is: $"
                                + NumberFormat.getIntegerInstance().format(game.getPlayer().getBalance()),
                        "Time to make a bet!", JOptionPane.QUESTION_MESSAGE));
                game.getPlayer().makeBet(bet);
            } catch (NumberFormatException | IllegalBetException e) {
                JOptionPane.showMessageDialog(gameFrame, "Please make a valid bet.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                bet = 0;
            }
        } while (bet == 0);
    }

    // backOfCard image taken from: https://www.nicepng.com/maxp/u2w7e6w7a9q8t4q8/
    // MODIFIES: this
    // EFFECTS: displays dealer's current count as well as a visual of dealer's hand with one card face-down
    public void initializeDealerPanel() {
        dealerText = new JLabel("Dealer shows a count of "
                + game.getDealerHand().get(0).getCardValue() + ".",SwingConstants.CENTER);
        dealerText.setLayout(new BorderLayout());
        dealerText.setFont(new Font("Avenir", Font.BOLD, 20));
        dealerText.setForeground(Color.WHITE);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weighty = 0.5;
        gc.gridx = 0;
        gc.gridy = 0;
        image.add(dealerText, gc);

        Image dealerFaceDownImage = new ImageIcon("./images/backOfCard.jpg").getImage();
        dealerPanel = new JPanel();
        dealerPanel.setLayout(new FlowLayout());
        dealerPanel.setOpaque(false);
        dealerPanel.add(new JLabel(new ImageIcon(printCard(game.getDealerHand().get(0)))));

        faceDownCard = new JLabel(new ImageIcon(dealerFaceDownImage));
        dealerPanel.add(faceDownCard);
        gc.gridx = 0;
        gc.gridy = 1;
        image.add(dealerPanel, gc);
    }

    // MODIFIES: this
    // EFFECTS: displays player's current count as well as a visual of player's hand
    public void initializePlayerPanel() {
        playerPanel = new JPanel();
        playerPanel.setLayout(new FlowLayout());
        playerPanel.setOpaque(false);

        for (Card card : game.getPlayerHand()) {
            playerPanel.add(new JLabel(new ImageIcon(printCard(card))));
        }
        gc.gridx = 0;
        gc.gridy = 2;
        image.add(playerPanel, gc);

        playerText = new JLabel("", SwingConstants.CENTER);
        if (game.checkBlackjack(game.getPlayerHand())) {
            playerText.setText(game.determineWinner());
            endGame();
        } else {
            playerText.setText("You have a current count of " + game.getHandInValue(game.getPlayerHand())
                    + ". What would you like to do?");
        }
        playerText.setFont(new Font("Avenir", Font.BOLD, 20));
        playerText.setForeground(Color.WHITE);
        gc.weightx = 0.5;
        gc.gridx = 0;
        gc.gridy = 3;
        image.add(playerText, gc);
    }

    // MODIFIES: this
    // EFFECTS: displays action panel with 4 buttons available for user to decide on next move
    public void initializeActionPanel() {
        actionPanel = new JPanel();
        hitBtn = new JButton("Hit");
        standBtn = new JButton("Stand");
        doubleBtn = new JButton("Double");
        saveBtn = new JButton("Save & Quit");
        actionPanel.setOpaque(false);
        actionPanel.setLayout(new FlowLayout());
        for (JButton button : new JButton[]{hitBtn, standBtn, doubleBtn, saveBtn}) {
            button.setOpaque(false);
            button.setFont(new Font("Avenir", Font.BOLD, 20));
            button.setForeground(Color.WHITE);
            button.addActionListener(this);
            actionPanel.add(button);
        }
        gc.gridx = 0;
        gc.gridy = 4;
        image.add(actionPanel, gc);
    }

    // MODIFIES: this
    // EFFECTS: displays current balance and bet information for this round
    public void initializeInfoPanel() {
        infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        balanceMsg = new JLabel("Current balance: $"
                + NumberFormat.getIntegerInstance().format(game.getPlayer().getBalance()), SwingConstants.LEFT);
        betMsg = new JLabel("    Current bet: $"
                + NumberFormat.getIntegerInstance().format(game.getPlayer().getBet()), SwingConstants.LEFT);
        for (JLabel label : new JLabel[]{balanceMsg, betMsg}) {
            label.setFont(new Font("Avenir", Font.BOLD, 15));
            label.setForeground(Color.WHITE);
            infoPanel.add(label);
        }
        gc.gridx = 0;
        gc.gridy = 5;
        image.add(infoPanel, gc);
    }

    // listOfCards image taken from: https://www.nicepng.com/maxp/u2w7e6w7a9q8t4q8/
    // EFFECTS: reads listOfCards.jpg and returns a sub-image based on a card input using the card's suit and rank
    public BufferedImage printCard(Card c) {
        BufferedImage allCards = null;
        try {
            allCards = ImageIO.read(new File("./images/listOfCards.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert allCards != null;
        int cardWidth = allCards.getWidth() / 13;
        int cardHeight = allCards.getHeight() / 4;

        return allCards.getSubimage((c.getCardRank() - 1) * cardWidth,
                (c.getCardSuitNum() - 1) * cardHeight, cardWidth, cardHeight);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: completes actions based on button pressed on action panel
    //          "Hit" adds card to player's hand
    //          "Stand" ends player's turn and moves to dealer's turn
    //          "Double" doubles the player's current bet and takes a single card
    //          "Save & Quit" saves the current game, closes the game menu and exits the program
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == hitBtn) {
            playerHit();
        } else if (e.getSource() == standBtn) {
            dealerTurn();
        } else if (e.getSource() == doubleBtn) {
            playerDouble();
        } else if (e.getSource() == saveBtn) {
            saveGame();
            System.exit(0);
        }
    }

    // MODIFIES: this, BlackjackGame
    // EFFECTS: adds a card to player's hand and displays it onto the game window
    public void playerHit() {
        game.hit(game.getPlayerHand());

        int last = game.getPlayerHand().size() - 1;
        playerPanel.add(new JLabel(new ImageIcon(printCard(game.getPlayerHand().get(last)))));
        gc.gridx = 0;
        gc.gridy = 2;
        image.add(playerPanel, gc);
        playerText.setText("You have a current count of " + game.getHandInValue(game.getPlayerHand())
                + ". What would you like to do?");

        image.revalidate();
        image.repaint();

        playerDialog();
    }

    // EFFECTS: displays a pop-out dialog that summarizes the result of the player's hit
    public void playerDialog() {
        int last = game.getPlayerHand().size() - 1;

        if (game.getHandInValue(game.getPlayerHand()) == 21) {
            JOptionPane.showMessageDialog(gameFrame, "You have a total count of 21! \n"
                    + game.determineWinner());
            endGame();
        } else if (game.getHandInValue(game.getPlayerHand()) > 21) {
            JOptionPane.showMessageDialog(gameFrame, "You hit a "
                    + game.getPlayerHand().get(last).getCardString() + " for a total count of "
                    + game.getHandInValue(game.getPlayerHand()) + " and have bust.\n" + game.determineWinner());
            endGame();
        } else {
            JOptionPane.showMessageDialog(gameFrame, "You hit a "
                    + game.getPlayerHand().get(last).getCardString() + " for a total count of "
                    + game.getHandInValue(game.getPlayerHand()) + ".");
        }
    }

    // MODIFIES: this
    // EFFECTS: reveals dealer's face-down card onto the game window
    public void dealerTurn() {
        faceDownCard.setVisible(false);
        dealerPanel.add(new JLabel(new ImageIcon(printCard(game.getDealerHand().get(1)))));
        gc.gridx = 0;
        gc.gridy = 1;
        image.add(dealerPanel, gc);

        image.revalidate();
        image.repaint();

        dealerHit();
    }

    // MODIFIES: this, BlackjackGame
    // EFFECTS: adds cards to dealer's hand until dealer reaches at least 17 and displays them onto the game window
    public void dealerHit() {
        int counter = 0;
        while (game.getHandInValue(game.getDealerHand()) < 17) {
            game.hit(game.getDealerHand());

            int last = game.getDealerHand().size() - 1;
            dealerPanel.add(new JLabel(new ImageIcon(printCard(game.getDealerHand().get(last)))));
            gc.gridx = 0;
            gc.gridy = 1;
            image.add(dealerPanel, gc);
            dealerText.setText("Dealer shows a count of " + game.getHandInValue(game.getDealerHand()) + ".");

            image.revalidate();
            image.repaint();

            counter++;
        }
        dealerDialog(counter);
    }

    // EFFECTS: displays a pop-out dialog that summarizes the result of the dealer's hit(s) as well as the result
    //          of the round
    public void dealerDialog(int timesHit) {
        if (timesHit == 0) {
            JOptionPane.showMessageDialog(gameFrame, "Dealer stands with a total count of "
                    + game.getHandInValue(game.getDealerHand()) + ". \n" + game.determineWinner());
        } else if (timesHit > 0 && game.checkBust(game.getDealerHand())) {
            JOptionPane.showMessageDialog(gameFrame, "Dealer hits " + timesHit
                    + " time(s) and gets a total count of " + game.getHandInValue(game.getDealerHand())
                    + ". \nDealer busts, you win!");
        } else {
            JOptionPane.showMessageDialog(gameFrame, "Dealer hits " + timesHit
                    + " time(s) and gets a total count of " + game.getHandInValue(game.getDealerHand()) + ". \n"
                    + game.determineWinner());
        }

        endGame();
    }

    // MODIFIES: this, Player, BlackjackGame
    // EFFECTS: double's the player's current bet, adds one card to player's hand and displays it onto the game window
    public void playerDouble() {
        if (game.getPlayer().doubleDown()) {
            JOptionPane.showMessageDialog(gameFrame, "Your bet is now $" + game.getPlayer().getBet()
                    + ". Good luck!");
            game.hit(game.getPlayerHand());
            playerPanel.add(new JLabel(new ImageIcon(printCard(game.getPlayerHand().get(2)))));
            gc.gridx = 0;
            gc.gridy = 2;
            image.add(playerPanel, gc);
            balanceMsg.setText("Current balance: $"
                    + NumberFormat.getIntegerInstance().format(game.getPlayer().getBalance()));
            betMsg.setText("    Current bet: $"
                    + NumberFormat.getIntegerInstance().format(game.getPlayer().getBet()));

            image.revalidate();
            image.repaint();

            playerDialog();
            dealerTurn();

        } else {
            JOptionPane.showMessageDialog(gameFrame, "You have an insufficient balance. Please try again.");
            doubleBtn.setVisible(false);
            image.revalidate();
            image.repaint();
        }
    }

    // Sections of this method are adapted from:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: saves the current round of Blackjack to file
    public void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            JOptionPane.showMessageDialog(gameFrame, "Successfully saved game. Your balance is $"
                    + NumberFormat.getIntegerInstance().format(game.getPlayer().getBalance()) + ".\n"
                    + "Thanks for playing!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(gameFrame, "Unable to write to file: " + BlackjackUI.JSON_LOCATION);
        }
    }

    // MODIFIES: BlackjackGame, Player
    // EFFECTS: updates player's balance accordingly, clears both player and dealer's hands
    public void endGame() {
        game.updatePlayerBalance();
        game.clearHand();
        game.getPlayer().setBet(0);

        if (game.getPlayer().getBalance() <= 0) {
            JOptionPane.showMessageDialog(gameFrame, "Your balance is now $0. \n"
                    + "Please reload the game to replenish your balance. \nBetter luck next time!",
                    "No More Money", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        } else {
            playAgain();
        }
    }

    // bettingChips image taken from: https://shutr.bz/3qdojxs
    // MODIFIES: this
    // EFFECTS: prompts user to select from 3 options:
    //          "Yes" starts a new round of Blackjack;
    //          "No" closes the application;
    //          "Save & Quit" saves the current balance to file and then closes the application
    public void playAgain() {
        String [] options = {"Save & Quit", "No", "Yes"};
        int selected;

        ImageIcon chips = new ImageIcon("./images/bettingChips.png");

        do {
            selected = JOptionPane.showOptionDialog(gameFrame,
                    "Your new balance is $"
                            + NumberFormat.getIntegerInstance().format(game.getPlayer().getBalance())
                            + ".\nWould you like to play again? \nPlease select one of the following:",
                    "Play Again?", JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE, chips,
                    options, options[2]);
        } while (selected == JOptionPane.CLOSED_OPTION);

        switch (selected) {
            case 2:
                gameFrame.dispose();
                new GameUI(game);
                break;
            case 1:
                JOptionPane.showMessageDialog(gameFrame, "Thanks for playing, see you next time!");
                System.exit(0);
            case 0:
                saveGame();
                System.exit(0);
        }
    }
}
