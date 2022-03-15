package ui;

import model.BlackjackGame;
import model.Card;
import model.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameUI extends JFrame implements ActionListener {

    private BlackjackGame game;
    private ImagePanel image;
    private JFrame gameFrame;
    private JButton hitBtn;
    private JButton standBtn;
    private JButton doubleBtn;
    private JButton saveBtn;

    public GameUI(BlackjackGame game) {
        this.game = game;
        game.startGame();

        gameFrame = new JFrame("Blackjack Game Simulator");
        image = new ImagePanel(new ImageIcon("./images/gameBackground.jpg").getImage());

        image.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel dealerText = new JLabel("Dealer shows a count of "
                + game.getDealerHand().get(0).getCardValue() + ".",SwingConstants.CENTER);
        dealerText.setForeground(Color.WHITE);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
        c.weightx = 0.5;
        c.gridwidth = 0;
        c.gridx = 0;
        c.gridy = 0;
        image.add(dealerText, c);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setOpaque(false);
        for (Card card : game.getPlayerHand()) {
            panel.add(new JLabel(new ImageIcon(printCard(card))));
        }

        c.gridx = 0;
        c.gridy = 2;
        image.add(panel, c);

        JLabel playerText = new JLabel("You have a current count of " + game.getHandInValue(game.getPlayerHand())
                + ". What would you like to do?", SwingConstants.CENTER);
        playerText.setForeground(Color.WHITE);
        c.ipady = 20;
        c.weightx = 0.5;
        c.gridwidth = 0;
        c.gridx = 0;
        c.gridy = 3;
        image.add(playerText, c);

        gameFrame.add(image);
        gameFrame.setSize(BlackjackUI.WIDTH, BlackjackUI.HEIGHT);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setResizable(false);

//        initializeActionPanel();
//        initializeBetPopOut();
//        initializePlayerCardPanel();
//        initializeDealerCardPanel();
//        initializeSidePanel();

        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initializeBetPopOut() {
        int bet = 0;
        int initialType = JOptionPane.QUESTION_MESSAGE;

        do {
            try {
                bet = Integer.parseInt(JOptionPane.showInputDialog(gameFrame,
                        "Please make a bet.\nYour balance is: $" + game.getPlayer().getBalance(),
                        "Time to make a bet!", initialType));
            } catch (NumberFormatException e) {
                initialType = JOptionPane.ERROR_MESSAGE;
            }
        } while (bet == 0);

        game.getPlayer().makeBet(bet);
    }

    public void initializeActionPanel() {
        JPanel panel = new JPanel();
        hitBtn = new JButton("Hit");
        standBtn = new JButton("Stand");
        doubleBtn = new JButton("Double");
        saveBtn = new JButton("Save & Quit");
        panel.setOpaque(false);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setPreferredSize(new Dimension(110, 100));

        JLabel message = new JLabel("You have a current count of " + game.getHandInValue(game.getPlayerHand())
                + ". What would you like to do?", JLabel.CENTER);
        message.setForeground(Color.WHITE);
        panel.add(message);

        for (JButton button : new JButton[]{hitBtn, standBtn, doubleBtn, saveBtn}) {
            button.setOpaque(true);
            button.setFont(new Font("Avenir", Font.BOLD, 20));
            button.setBackground(new Color(13,159,93));
            button.setForeground(Color.WHITE);
            button.addActionListener(this);
            panel.add(button);
        }

        image.add(panel, "South");
    }

    public void initializeDealerCardPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(BlackjackUI.WIDTH, 225));

        JLabel message = new JLabel("Dealer has: ", JLabel.CENTER);
        message.setFont(new Font("Avenir", Font.BOLD, 20));
        message.setForeground(Color.WHITE);
        panel.add(message);

        panel.add(new JLabel(new ImageIcon(printCard(game.getDealerHand().get(0)))));
        image.add(panel, "North");
    }

    public void initializePlayerCardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setOpaque(false);

        for (Card c : game.getPlayerHand()) {
            panel.add(new JLabel(new ImageIcon(printCard(c))));
        }

        image.add(panel, "Center");
    }

    public void initializeSidePanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);

        JLabel balanceMsg = new JLabel("Current balance: " + game.getPlayer().getBalance(), JLabel.LEFT);
        JLabel betMsg = new JLabel("Current bet: " + game.getPlayer().getBet(), JLabel.LEFT);

        balanceMsg.setFont(new Font("Avenir", Font.BOLD, 15));
        betMsg.setFont(new Font("Avenir", Font.BOLD, 15));

        balanceMsg.setForeground(Color.WHITE);
        betMsg.setForeground(Color.WHITE);

        panel.add(balanceMsg);
        panel.add(betMsg);

        image.add(panel, "West");
    }

    public BufferedImage printCard(Card c) {
        BufferedImage allCards = null;
        try {
            allCards = ImageIO.read(new File("./images/listOfCards.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int cardWidth = allCards.getWidth() / 13;
        int cardHeight = allCards.getHeight() / 4;

        BufferedImage cardImage = allCards.getSubimage((c.getCardRank() - 1) * cardWidth,
                (c.getCardSuitNum() - 1) * cardHeight, cardWidth, cardHeight);

        return cardImage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveBtn) {
            JOptionPane.showMessageDialog(gameFrame, "Your saved balance is $"
                    + game.getPlayer().getBalance());
            System.exit(0);
        }
    }
}
