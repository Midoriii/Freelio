package pv168.freelancer.ui.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.util.Map;

public class NavBarButton extends JButton implements MouseListener {

    public NavBarButton(String label, Icon icon){
        super(label);

        setBorderPainted(false);
        setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        setMargin(new Insets(0, 0, 0, 0));
        setContentAreaFilled(false);
        setFocusPainted(false);

        setMinimumSize(new Dimension(100, 32));
        setPreferredSize(new Dimension(150, 32));
        setMaximumSize(new Dimension(150, 32));

        setIconTextGap(10);

        setHorizontalAlignment(SwingConstants.LEFT);

        setForeground(Color.WHITE);
        setBackground(new Color(76, 175, 80));
        setIcon(icon);

        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //setForeground(Color.GRAY);
        Font font = getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL);
        setFont(font.deriveFont(attributes));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //setForeground(Color.WHITE);
        Font font = getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, null);
        setFont(font.deriveFont(attributes));
    }
}
