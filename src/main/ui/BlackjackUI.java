package ui;

import model.BlackjackGame;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// Represents the main menu of options for when the program is initially ran
public class BlackjackUI extends JFrame implements ActionListener {

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

    // Sections of this method are adapted from:
    // https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    // menuBackground image taken from: https://bit.ly/3MRa8rD
    // EFFECTS: sets up window in which the background image and menu options will be displayed
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

    // MODIFIES: this
    // EFFECTS: adds a welcome message to the main menu, and provides instructions on how to proceed
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


    // MODIFIES: this
    // EFFECTS: adds the "Play", "Load", and "Quit" buttons onto the main menu
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

    // Sections of this method are adapted from:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads the saved round of Blackjack from file into a game window and disposes of the main menu
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
    // MODIFIES: this
    // EFFECTS: completes actions based on button pressed on main menu:
    //          "Play" loads a new game into a GameUI and disposes of the main menu
    //          "Load" calls the loadBlackjackGame() method
    //          "Quit" closes the main menu and exits the program
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

    // Sections of this method are adapted from:
    // https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase
    // MODIFIES: this
    // EFFECTS:  location of frame is set so frame is centred on desktop
    public void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    // EFFECTS: starts a new main menu and runs the application
    public static void main(String[] args) {
        new BlackjackUI();
    }
}
