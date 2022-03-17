package ui;

import model.BlackjackGame;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

class BlackjackUI extends JFrame implements ActionListener {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String JSON_LOCATION = "./data/BlackjackGame.json";

    private BlackjackGame game;
    private JsonReader jsonReader;

    private JFrame mainFrame;
    private ImagePanel image;
    private JButton playBtn;
    private JButton loadBtn;
    private JButton quitBtn;

    // EFFECTS: sets up window in which Blackjack game will be played
    public BlackjackUI() {
        game = new BlackjackGame();
        jsonReader = new JsonReader(JSON_LOCATION);

        image = new ImagePanel(new ImageIcon("./images/menuBackground.jpg").getImage());

        mainFrame = new JFrame("Blackjack Game Simulator");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(image);
        image.setLayout(null);

        welcomeMessage();
        initializeButtonPanel();

        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        centreOnScreen();
    }

    public void welcomeMessage() {
        JLabel message = new JLabel("Welcome to Blackjack!", JLabel.LEFT);
        message.setFont(new Font("Avenir", Font.BOLD, 40));
        message.setForeground(Color.WHITE);
        message.setBounds(50, 50, WIDTH, 100);

        JLabel message2 = new JLabel("To begin, please select one of the following options:", JLabel.LEFT);
        message2.setFont(new Font("Avenir", Font.BOLD, 20));
        message2.setForeground(Color.WHITE);
        message2.setBounds(50, 70, 500, 250);

        image.add(message);
        image.add(message2);
    }

    public void initializeButtonPanel() {
        playBtn = new JButton("Play");
        loadBtn = new JButton("Load");
        quitBtn = new JButton("Quit");

        for (JButton button : new JButton[]{playBtn, loadBtn, quitBtn}) {
            button.setFont(new Font("Avenir", Font.BOLD, 20));
            button.setForeground(Color.WHITE);
            button.addActionListener(this);
        }

        playBtn.setBounds(50, 225, 100, 50);
        loadBtn.setBounds(50, 275, 100, 50);
        quitBtn.setBounds(50, 325, 100, 50);
        image.add(playBtn);
        image.add(loadBtn);
        image.add(quitBtn);
    }

    public void loadBlackjackGame() {
        try {
            game = jsonReader.read();
            mainFrame.dispose();
            new GameUI(game);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainFrame, "Unable to read file: " + JSON_LOCATION);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playBtn) {
            mainFrame.dispose();
            new GameUI(game);
        } else if (e.getSource() == loadBtn) {
            loadBlackjackGame();
        } else {
            JOptionPane.showMessageDialog(mainFrame, "See you next time!");
            System.exit(0);
        }
    }

    // Centres frame on desktop
    // MODIFIES: this
    // EFFECTS:  location of frame is set so frame is centred on desktop
    public void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    // starts the application
    public static void main(String[] args) {
        new BlackjackUI();
    }
}
