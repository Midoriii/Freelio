package pv168.freelancer.ui;

import pv168.freelancer.data.WorkDoneDao;
import pv168.freelancer.data.WorkTypeDao;
import pv168.freelancer.ui.buttons.MinimizeButton;
import pv168.freelancer.ui.buttons.QuitButton;
import pv168.freelancer.ui.cards.InvoiceCard;
import pv168.freelancer.ui.cards.ProfitCard;
import pv168.freelancer.ui.cards.WorkDoneCard;
import pv168.freelancer.ui.navbar.NavBar;
import pv168.freelancer.ui.utils.ComponentMover;
import pv168.freelancer.ui.utils.ComponentResizer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

/**
 * The main window of the application.
 *
 * @author xbenes2
 */
public class MainWindow {
    private JFrame frame;
    private JPanel navBar;
    private JPanel quitPanel;
    private JPanel contentPanel;

    // These are here because undecorated Frame cannot be moved or resized on its own
    private final ComponentMover cm = new ComponentMover();
    private final ComponentResizer cr = new ComponentResizer();

    private final static String WORK_DONE = "Work Done";
    private final static String INVOICES = "Invoices";
    private final static String PROFIT_CALC = "Profit Calculator";

    private final WorkDoneDao workDoneDao;
    private final WorkTypeDao workTypeDao;

    public MainWindow(WorkDoneDao workDoneDao, WorkTypeDao workTypeDao) {
        this.workDoneDao = workDoneDao;
        this.workTypeDao = workTypeDao;

        setUpUIManager();

        frame = createFrame();

        createContentPanel();
        createNavbar();
        createQuitPanel();

        setUpGroupLayout();

        setUpMover();
        setUpResizer();
    }

    private JFrame createFrame() {
        frame = new JFrame("Work evidence");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1100, 675));
        frame.setSize(new Dimension(1100, 675));

        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return frame;
    }

    private void createContentPanel() {
        CardLayout cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.add(new WorkDoneCard(WORK_DONE, frame, workTypeDao, workDoneDao), WORK_DONE);
        contentPanel.add(new InvoiceCard(INVOICES, frame), INVOICES);
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

        quitPanel.setLayout(new BoxLayout(quitPanel, BoxLayout.LINE_AXIS));
        // The Glue and Rigid Areas are a way of composing the components where one wants them
        quitPanel.add(Box.createHorizontalGlue());
        quitPanel.add(new MinimizeButton(frame));
        quitPanel.add(Box.createRigidArea(new Dimension(5,0)));
        quitPanel.add(new QuitButton(e -> System.exit(0)));
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

    private void setUpUIManager() {
        UIManager.put("Panel.background", new ColorUIResource(Color.WHITE));
        UIManager.put("ComboBox.background", new ColorUIResource(Color.WHITE));
        UIManager.put("ComboBox.foreground", new ColorUIResource(new Color(51, 51, 51)));
        UIManager.put("ToolBar.background", new ColorUIResource(Color.WHITE));
        UIManager.put("Label.foreground", new ColorUIResource(new Color(51, 51, 51)));
        UIManager.put("TableHeader.font", new FontUIResource("Sans-Serif",Font.BOLD,12));
        UIManager.put("TableHeader.cellBorder", new EmptyBorder(0,0,0,0));
        UIManager.put("TableHeader.foreground", new ColorUIResource(Color.WHITE));
        UIManager.put("TableHeader.background", new ColorUIResource(new Color(76, 175, 80)));
        // This is actually hover color, might come handy still
        //UIManager.put("ComboBox.selectionBackground", new ColorUIResource(Color.WHITE));
    }
}
