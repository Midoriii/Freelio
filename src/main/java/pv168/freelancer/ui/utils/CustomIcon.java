package pv168.freelancer.ui.utils;

import pv168.freelancer.ui.MainWindow;

import javax.swing.*;
import java.awt.*;

public class CustomIcon implements Icon {

    private final Image image;

    private final int width;
    private final int height;


    public CustomIcon(String url, int width, int height){
        this.width = width;
        this.height = height;

        ImageIcon icon = new ImageIcon(MainWindow.class.getResource(url));
        image = icon.getImage().getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH);
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

        g2.drawImage(image, 0, 0, width, height, null);

        c.repaint();
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }
}
