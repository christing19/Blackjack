package ui;

import model.BlackjackGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BlackjackUI extends JFrame implements ActionListener {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private BlackjackGame game;
    private JFrame mainFrame;
    private ImagePanel image;
    private JButton playBtn;
    private JButton loadBtn;
    private JButton quitBtn;

    // EFFECTS: sets up window in which Blackjack game will be played
    public BlackjackUI() {
        game = new BlackjackGame();
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
        message2.setBounds(50, 100, 500, 250);

        image.add(message);
        image.add(message2);
    }

    public void initializeButtonPanel() {
        playBtn = new JButton("Play");
        loadBtn = new JButton("Load");
        quitBtn = new JButton("Quit");

        for (JButton button : new JButton[]{playBtn, loadBtn, quitBtn}) {
            button.setFont(new Font("Avenir", Font.BOLD, 20));
            button.addActionListener(this);
        }

        playBtn.setBounds(50, 250, 100, 50);
        loadBtn.setBounds(50, 300, 100, 50);
        quitBtn.setBounds(50, 350, 100, 50);
        image.add(playBtn);
        image.add(loadBtn);
        image.add(quitBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playBtn) {
            new GameUI(game);
            mainFrame.dispose();
        } else if (e.getSource() == quitBtn) {
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
