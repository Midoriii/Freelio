package pv168.freelancer.ui;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    private JFrame frame;
    private JPanel panel;
    private CardLayout cardLayout;

    public MainWindow() {
        frame = createFrame();
        panel = new JPanel();
    }

    public void show() {
        frame.setVisible(true);
    }

    private JFrame createFrame() {
        var frame = new JFrame("Work evidence");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return frame;
    }

}
