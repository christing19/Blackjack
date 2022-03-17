package ui;

import javax.swing.*;
import java.awt.*;

// Represents a class that extends JPanel to allow usage of image backgrounds for the user interface
public class ImagePanel extends JPanel {

    private Image image;

    // EFFECTS: constructs an ImagePanel given an image as input
    public ImagePanel(Image img) {
        this.image = img;
    }


    @Override
    // MODIFIES: this
    // EFFECTS: draws image onto panel
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}
