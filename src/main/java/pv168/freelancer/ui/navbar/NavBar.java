package pv168.freelancer.ui.navbar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Label.CENTER;

public class NavBar extends JPanel implements ActionListener {

    private Label brand;
    private JButton btnInvoices;
    private JButton btnWorkDone;
    private JButton btnProfitCalc;
    private JPanel contentPanel;

    public NavBar(String invoiceName, String workDoneName, String profitCalcName, JPanel panel){
        super();
        setUp();

        this.contentPanel = panel;

        createBrandLogo();
        createButtons(invoiceName, workDoneName, profitCalcName);
        add(Box.createVerticalGlue());
    }

    private void setUp() {
        setPreferredSize(new Dimension(250, 740));
        setBackground(new Color(76, 175, 80));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    private void createBrandLogo() {
        brand = new Label("Freelio");
        brand.setPreferredSize(new Dimension(250, 120));
        brand.setAlignment(CENTER);
        brand.setForeground(Color.WHITE);

        add(brand);
    }

    private void createButtons(String invoiceName, String workDoneName, String profitCalcName) {
        btnInvoices = new JButton(invoiceName);
        btnInvoices.addActionListener(this);
        btnInvoices.setAlignmentX(CENTER_ALIGNMENT);

        btnWorkDone = new JButton(workDoneName);
        btnWorkDone.addActionListener(this);
        btnWorkDone.setAlignmentX(CENTER_ALIGNMENT);

        btnProfitCalc = new JButton(profitCalcName);
        btnProfitCalc.addActionListener(this);
        btnProfitCalc.setAlignmentX(CENTER_ALIGNMENT);

        add(btnInvoices);
        add(btnWorkDone);
        add(btnProfitCalc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout layout = (CardLayout) contentPanel.getLayout();
        layout.show(contentPanel, e.getActionCommand());
    }
}
