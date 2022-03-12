package ui;

import model.BlackjackGame;

import javax.swing.*;
import java.awt.*;

class BlackjackUI extends JFrame {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private BlackjackGame game;
    private JFrame mainFrame;
    private ImagePanel image;
    private JLabel message;

    // EFFECTS: sets up window in which Blackjack game will be played
    public BlackjackUI() {
        this.game = new BlackjackGame();
        this.image = new ImagePanel(new ImageIcon("./images/menuBackground.jpg").getImage());

        mainFrame = new JFrame("Blackjack Game Simulator");
        mainFrame.add(image);
        welcomeMessage();
        addButtons();

        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        centreOnScreen();
    }

    public void welcomeMessage() {
        JLabel message = new JLabel("Welcome to Blackjack!");
        message.setBounds(80, 100, 500, 100);
        image.add(message);
    }

    public void addButtons() {
        JButton playBtn = new JButton("Play");
        JButton loadBtn = new JButton("Load");
        JButton quitBtn = new JButton("Quit");

        playBtn.setBounds(80,250,100,50);
        loadBtn.setBounds(80,325,100,50);
        quitBtn.setBounds(80,400,100,50);

        playBtn.setFont(new Font("Avenir", Font.BOLD, 25));
        loadBtn.setFont(new Font("Avenir", Font.BOLD, 25));
        quitBtn.setFont(new Font("Avenir", Font.BOLD, 25));

        image.add(playBtn);
        image.add(loadBtn);
        image.add(quitBtn);
    }

    // Centres frame on desktop
    // MODIFIES: this
    // EFFECTS:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    // starts the application
    public static void main(String[] args) {
        new BlackjackUI();
    }
}
