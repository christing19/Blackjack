package ui;

import model.BlackjackGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuUI extends JComponent {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private BlackjackGame game;
    private static BufferedImage img;

    // Constructs a game panel
    // effects:  sets size and background colour of panel,
    //           updates this with the game to be displayed
    public MenuUI(BlackjackGame game) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.game = game;
    }

    @Override
    public void paintComponent(Graphics g) {
        try {
            img = ImageIO.read(new File("./images/menuBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g.drawImage(img, 0, 0, null);
        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.setColor(Color.WHITE);
        g.drawString("Welcome to Blackjack!", 20, 100);
    }
}
