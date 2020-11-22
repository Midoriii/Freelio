package pv168.freelancer.ui.utils;

import pv168.freelancer.ui.MainWindow;
import pv168.freelancer.ui.buttons.NavBarButton;
import pv168.freelancer.ui.buttons.QuitPanelButton;
import pv168.freelancer.ui.buttons.RoundedButton;
import pv168.freelancer.ui.buttons.RoundedButtonSmall;

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

        // This is the main reason this class exists - custom HQ rendering of image
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2.setRenderingHint(
                RenderingHints.KEY_COLOR_RENDERING,
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);

        // This has to be done to ensure the drawing of the icon exactly where we want it,
        // since it is a Custom Icon class and cannot be positioned using typical methods.
        if(c instanceof NavBarButton){
            g2.drawImage(image, 0,4, width, height, null);
        }
        else if(c instanceof JButton){
            JButton cast = (JButton) c;
            if(cast.getUI() instanceof RoundedButtonSmall){
                g2.drawImage(image, 10,8, width, height, null);
            }
            else if(cast.getUI() instanceof RoundedButton){
                g2.drawImage(image, 25,18, width, height, null);
            }
            else{
                g2.drawImage(image, c.getWidth()/2 - width/2,c.getHeight()/2 - height/2,
                             width, height, null);
            }
        }
        else{
            g2.drawImage(image, 0,0, width, height, null);
        }


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
