package pv168.freelancer.ui.navbar;

import pv168.freelancer.ui.buttons.NavBarButton;
import pv168.freelancer.ui.utils.I18N;
import pv168.freelancer.ui.utils.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A navigation bar for switching cards in the cardLayout of MainWindow class.
 *
 * @author xbenes2
 */
public class NavBar extends JPanel implements ActionListener {

    private Label brand;
    private JPanel brandPanel;
    private JButton btnWorkDone;
    private JButton btnProfitCalc;
    private JPanel btnPanel;
    private JPanel contentPanel;

    private static final I18N I18N = new I18N(NavBar.class);

    public NavBar(String workDoneName, String profitCalcName, JPanel panel){
        super();
        setUp();

        this.contentPanel = panel;

        createBrandLogo();
        add(new Box.Filler(new Dimension(0, 40), new Dimension(0, 40),
                new Dimension(0, 150)));
        createButtons(workDoneName, profitCalcName);
        add(Box.createVerticalGlue());
    }

    private void setUp() {
        setMinimumSize(new Dimension(150, 490));
        setPreferredSize(new Dimension(200, 675));
        setMaximumSize(new Dimension(240, 860));
        setBackground(new Color(76, 175, 80));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(LEFT_ALIGNMENT);
    }

    private void createBrandLogo() {
        brand = new Label(I18N.getString("brand"));
        brand.setForeground(Color.WHITE);

        brandPanel = new JPanel();

        brandPanel.setMinimumSize(new Dimension(250, 80));
        brandPanel.setPreferredSize(new Dimension(250, 80));
        brandPanel.setMaximumSize(new Dimension(250, 80));
        brandPanel.setBackground(new Color(76, 175, 80));
        brandPanel.setLayout(new BoxLayout(brandPanel, BoxLayout.X_AXIS));

        brandPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        brandPanel.add(brand);
        brandPanel.add(Box.createHorizontalGlue());

        add(brandPanel);
    }

    private void createButtons(String workDoneName, String profitCalcName) {
        btnPanel = new JPanel();

        btnPanel.setMinimumSize(new Dimension(100, 210));
        btnPanel.setPreferredSize(new Dimension(150, 395));
        btnPanel.setMaximumSize(new Dimension(150, 580));
        btnPanel.setBackground(new Color(76, 175, 80));
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));

        JPanel gluePanel = new JPanel();
        gluePanel.setAlignmentY(TOP_ALIGNMENT);
        gluePanel.setBackground(new Color(76, 175, 80));
        gluePanel.setLayout(new BoxLayout(gluePanel, BoxLayout.Y_AXIS));

        btnWorkDone = new NavBarButton(workDoneName, Icons.WORK_ICON);
        btnWorkDone.addActionListener(this);

        btnProfitCalc = new NavBarButton(profitCalcName, Icons.PROFIT_ICON);
        btnProfitCalc.addActionListener(this);

        // Fillers between buttons
        gluePanel.add(btnWorkDone);
        gluePanel.add(new Box.Filler(new Dimension(0, 5), new Dimension(0, 8),
                new Dimension(0, 8)));
        gluePanel.add(btnProfitCalc);

        // Kind of a fake filler to move the buttons a bit to the middle
        btnPanel.add(new Box.Filler(new Dimension(10, 0), new Dimension(25, 0),
                     new Dimension(35, 0)));
        btnPanel.add(gluePanel);

        add(btnPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout layout = (CardLayout) contentPanel.getLayout();
        layout.show(contentPanel, e.getActionCommand());
    }
}
