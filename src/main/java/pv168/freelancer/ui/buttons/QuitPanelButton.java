package pv168.freelancer.ui.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuitPanelButton extends JButton implements MouseListener, MouseMotionListener {

    public QuitPanelButton(){
        super();

        setBorderPainted(false);
        setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        setMargin(new Insets(0, 0, 0, 0));
        setMinimumSize(new Dimension(24, 24));
        setPreferredSize(new Dimension(24, 24));
        setMaximumSize(new Dimension(24, 24));
        setContentAreaFilled(false);
        setFocusPainted(false);

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    private boolean isValidClickPosition(Point p) {
        double x = p.x;
        double y = p.y;

        // Calculate centre of the ellipse.
        double s1 = getLocationOnScreen().x + getSize().width / 2.0;
        double s2 = getLocationOnScreen().y + getSize().height / 2.0;

        // Calculate semi-major and semi-minor axis
        double a = getSize().width / 2.0;
        double b = getSize().height / 2.0;

        // Check if the given point is within the specified ellipse:
        return ((((x - s1)*(x - s1)) / (a*a)) + (((y - s2)*(y - s2)) / (b*b))) <= 1.0;
    }

    @Override
    public void addActionListener(ActionListener l) {
        super.addActionListener(e -> {
            if (isValidClickPosition(MouseInfo.getPointerInfo().getLocation())) {
                l.actionPerformed(e);
            }
        });
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
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // So far only found solution to set rollover icon properly
        setRolloverEnabled(isValidClickPosition(e.getLocationOnScreen()));
        repaint();
    }
}
