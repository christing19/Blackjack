package ui;

import model.BlackjackGame;

import javax.swing.*;
import java.awt.*;

class BlackjackUI extends JFrame {

    private BlackjackGame game;
    private MenuUI menu;

    // EFFECTS: sets up window in which Blackjack game will be played
    public BlackjackUI() {
        super("Blackjack Game Simulator");
        game = new BlackjackGame();
        menu = new MenuUI(game);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        add(menu);
        pack();
        centreOnScreen();
        setVisible(true);
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
