package ui;

import exceptions.IllegalBetException;
import model.BlackjackGame;
import model.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

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
    private JDialog againPrompt;

    public GameUI(BlackjackGame game) {
        this.game = game;
        game.startGame();
        gameFrame = new JFrame("Blackjack Game Simulator");
        image = new ImagePanel(new ImageIcon("./images/gameBackground.jpg").getImage());

        image.setLayout(new GridBagLayout());
        gc = new GridBagConstraints();

        initializeBetPopOut();

        gameFrame.add(image);
        gameFrame.setVisible(true);
        initializeDealerPanel();
        initializePlayerPanel();
        initializeActionPanel();
        initializeInfoPanel();

        gameFrame.setSize(BlackjackUI.WIDTH, BlackjackUI.HEIGHT);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setResizable(false);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initializeBetPopOut() {
        int bet;

        do {
            try {
                bet = Integer.parseInt(JOptionPane.showInputDialog(gameFrame,
                        "Please make a bet.\nYour balance is: $" + game.getPlayer().getBalance(),
                        "Time to make a bet!", JOptionPane.QUESTION_MESSAGE));
                game.getPlayer().makeBet(bet);
            } catch (NumberFormatException | IllegalBetException e) {
                JOptionPane.showMessageDialog(gameFrame, "Please make a valid bet.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                bet = 0;
            }
        } while (bet == 0);
    }

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
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == hitBtn) {
            playerHit();
        } else if (e.getSource() == standBtn) {
            dealerTurn();
        } else if (e.getSource() == doubleBtn) {
            playerDouble();
        } else if (e.getSource() == saveBtn) {
            JOptionPane.showMessageDialog(gameFrame, "Your saved balance is $"
                    + NumberFormat.getIntegerInstance().format(game.getPlayer().getBalance()));
            System.exit(0);
        }
    }

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

    public void playerDialog() {
        int last = game.getPlayerHand().size() - 1;

        if (game.getHandInValue(game.getPlayerHand()) == 21) {
            JOptionPane.showMessageDialog(gameFrame, "You have a total count of 21! \n"
                    + game.determineWinner());
            endGame();
        } else if (game.getHandInValue(game.getPlayerHand()) > 21) {
            JOptionPane.showMessageDialog(gameFrame, "You have a total count of "
                    + game.getHandInValue(game.getPlayerHand()) + " and have bust.\n" + game.determineWinner());
            endGame();
        } else {
            JOptionPane.showMessageDialog(gameFrame, "You hit a "
                    + game.getPlayerHand().get(last).getCardValue() + " for a total count of "
                    + game.getHandInValue(game.getPlayerHand()) + ".");
        }
    }

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

    public void playerDouble() {
        if (game.getPlayer().doubleDown()) {
            JOptionPane.showMessageDialog(gameFrame, "Your bet is now $" + game.getPlayer().getBet() + ".");
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

    public void endGame() {
        game.updatePlayerBalance();
        game.clearHand();
        game.getPlayer().setBet(0);

        dealerPanel.setVisible(false);
        playerPanel.setVisible(false);
        dealerText.setVisible(false);
        playerText.setVisible(false);
        actionPanel.setVisible(false);
        infoPanel.setVisible(false);

        if (game.getPlayer().getBalance() <= 0) {
            JOptionPane.showMessageDialog(gameFrame, "Your balance is now $0. \n"
                    + "Please reload the game to replenish your balance. \nBetter luck next time!",
                    "No More Money", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        } else {
            playAgain();
        }
    }

    public void playAgain() {
        String [] options = {"Save & Quit", "No", "Yes"};
        int selected;

        do {
            selected = JOptionPane.showOptionDialog(gameFrame,
                    "Would you like to play again? Please select one of the following:", "Play Again?",
                    JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
        } while (selected == JOptionPane.CLOSED_OPTION);

        switch (selected) {
            case 2:
                new GameUI(game);
                gameFrame.dispose();
                break;
            case 1:
                JOptionPane.showMessageDialog(gameFrame, "Thanks for playing, see you next time!");
                System.exit(0);
            case 0:
                System.exit(0);
        }
    }
}
