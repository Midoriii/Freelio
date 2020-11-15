package pv168.freelancer.ui.utils;

import pv168.freelancer.ui.MainWindow;

import javax.swing.*;
import java.awt.*;

public class CustomIcon implements Icon {

    private final Image image;

    private final int WIDTH = 24;
    private final int HEIGHT = 24;


    public CustomIcon(String url){
        ImageIcon icon = new ImageIcon(MainWindow.class.getResource(url));
        image = icon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);

        c.repaint();
    }

    @Override
    public int getIconWidth() {
        return WIDTH;
    }

    @Override
    public int getIconHeight() {
        return HEIGHT;
    }
}
