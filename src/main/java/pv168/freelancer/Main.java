package pv168.freelancer;

import pv168.freelancer.ui.MainWindow;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MainWindow().show());
    }
}
