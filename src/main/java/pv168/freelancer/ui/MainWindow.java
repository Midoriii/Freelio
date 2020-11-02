package pv168.freelancer.ui;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    private JFrame frame;
//    private JPanel panel;
    private CardLayout cardLayout;
    private JTabbedPane tabbedPane;

    public MainWindow() {
        frame = createFrame();
        tabbedPane = createTabbedPane();
    }

    public void show() {
        frame.setVisible(true);
    }

    private JFrame createFrame() {
        var frame = new JFrame("Invoice table");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return frame;
    }

    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel invoices = new JPanel();
        JLabel label1 = new JLabel("invoices");
        invoices.add(label1);

        JPanel workDone = new JPanel();

        JPanel profitCalc = new JPanel();

        tabbedPane.addTab("Invoices", invoices);
        tabbedPane.addTab("Work done", workDone);
        tabbedPane.addTab("Profit calculator", profitCalc);

        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        return tabbedPane;
    }

}
