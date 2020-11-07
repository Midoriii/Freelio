package pv168.freelancer.ui;

import pv168.freelancer.ui.buttons.MinimizeButton;
import pv168.freelancer.ui.buttons.QuitButton;
import pv168.freelancer.ui.cards.InvoiceCard;
import pv168.freelancer.ui.cards.ProfitCard;
import pv168.freelancer.ui.cards.WorkDoneCard;
import pv168.freelancer.ui.navbar.NavBar;
import pv168.freelancer.ui.utils.ComponentMover;
import pv168.freelancer.ui.utils.ComponentResizer;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    private final JFrame frame;
    private JPanel navBar;
    private JPanel quitPanel;
    private JPanel contentPanel;

    // These are here because undecorated Frame cannot be moved or resized on its own
    private final ComponentMover cm = new ComponentMover();
    private final ComponentResizer cr = new ComponentResizer();

    private final String WORK_DONE = "Work Done";
    private final String INVOICES = "Invoices";
    private final String PROFIT_CALC = "Profit Calculator";

    public MainWindow() {
        frame = createFrame();

        createContentPanel();
        createNavbar();
        createQuitPanel();

        setUpGroupLayout();

        setUpMover();
        setUpResizer();
    }

    private JFrame createFrame() {
        var frame = new JFrame("Work evidence");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1100, 675));
        frame.setSize(new Dimension(1100, 675));

        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        return frame;
    }

    private void createContentPanel() {
        CardLayout cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.add(new WorkDoneCard(WORK_DONE), WORK_DONE);
        contentPanel.add(new InvoiceCard(INVOICES), INVOICES);
        contentPanel.add(new ProfitCard(PROFIT_CALC), PROFIT_CALC);
    }

    private void createNavbar() {
        navBar = new NavBar(INVOICES, WORK_DONE, PROFIT_CALC, contentPanel);
    }

    private void createQuitPanel() {
        quitPanel = new JPanel();
        quitPanel.setMinimumSize(new Dimension(550, 40));
        quitPanel.setPreferredSize(new Dimension(890, 40));
        quitPanel.setMaximumSize(new Dimension(1400, 40));
        // The Blue color is for debugging purposes only
        // quitPanel.setBackground(Color.BLUE);

        quitPanel.setLayout(new BoxLayout(quitPanel, BoxLayout.LINE_AXIS));
        // The Glue and Rigid Areas are a way of composing the components where on wants them
        quitPanel.add(Box.createHorizontalGlue());
        quitPanel.add(new MinimizeButton(frame));
        quitPanel.add(Box.createRigidArea(new Dimension(5,0)));
        quitPanel.add(new QuitButton());
        quitPanel.add(Box.createRigidArea(new Dimension(5,0)));
    }

    public void show() {
        frame.pack();
        frame.setVisible(true);
    }

    private void setUpGroupLayout() {
        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        frame.setLayout(groupLayout);

        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent(navBar)
                        .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(quitPanel)
                                .addComponent(contentPanel))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(navBar)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(quitPanel)
                                .addComponent(contentPanel))
        );
    }

    private void setUpMover() {
        // This is here so that the MouseDrag event of resize / move doesn't clash on the edges
        cm.setDragInsets(new Insets(5, 5, 5, 5));
        cm.registerComponent(frame);
    }

    private void setUpResizer() {
        cr.setMinimumSize(new Dimension(800, 490));
        cr.setMaximumSize(new Dimension(1400, 860));
        cr.registerComponent(frame);
    }
}
