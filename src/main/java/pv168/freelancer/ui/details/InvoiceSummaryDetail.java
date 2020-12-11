package pv168.freelancer.ui.details;

import pv168.freelancer.ui.buttons.MinimizeButton;
import pv168.freelancer.ui.buttons.QuitButton;
import pv168.freelancer.ui.buttons.RoundedButton;
import pv168.freelancer.ui.utils.ComponentFactory;
import pv168.freelancer.ui.utils.ComponentMover;
import pv168.freelancer.ui.utils.I18N;
import pv168.freelancer.ui.utils.Icons;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * An overview of single Invoice, containing comprehensible table of work done and the total sum
 *
 * @author xbenes2
 */
public class InvoiceSummaryDetail extends JDialog {

    private JPanel quitPanel;
    private JPanel contentPanel;

    private JLabel total;

    private final ComponentMover cm = new ComponentMover();

    private static final I18N I18N = new I18N(InvoiceSummaryDetail.class);

    public InvoiceSummaryDetail(JFrame owner, boolean modality){
        super(owner, modality);
        setUpDialog();

        quitPanel = ComponentFactory.createQuitPanel(owner, this, 800, 40);

        setUpContentPanel();

        add(Box.createVerticalStrut(8));
        add(quitPanel);
        add(Box.createVerticalStrut(10));
        add(contentPanel);

        setUpMover();

        setVisible(true);
    }

    private void setUpContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setMinimumSize(new Dimension(740, 610));
        contentPanel.setPreferredSize(new Dimension(740, 610));
        contentPanel.setMaximumSize(new Dimension(740, 610));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        createInfoPanels();

        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(createTablePanel());
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createSendPanel());
    }

    private void createInfoPanels(){
        JPanel firstLine = createRowPanel();
        JLabel idTextLabel = new JLabel(I18N.getString("id"));
        // Insert actual values HERE
        JLabel idLabel = new JLabel("00087");
        firstLine.add(idTextLabel);
        firstLine.add(Box.createHorizontalStrut(5));
        firstLine.add(idLabel);
        contentPanel.add(firstLine);
        contentPanel.add(Box.createVerticalStrut(10));

        JPanel secondLine = createRowPanel();
        JLabel issuerTextLabel = new JLabel(I18N.getString("issuer"));
        JLabel issuerLabel = new JLabel("It's a Me");
        JLabel issuedDateTextLabel = new JLabel(I18N.getString("issuedDate"));
        // Insert actual values HERE
        JLabel issuedDateLabel = new JLabel("30.2.2012");
        secondLine.add(issuerTextLabel);
        secondLine.add(Box.createHorizontalStrut(5));
        secondLine.add(issuerLabel);
        secondLine.add(Box.createHorizontalGlue());
        secondLine.add(issuedDateTextLabel);
        secondLine.add(Box.createHorizontalStrut(5));
        secondLine.add(issuedDateLabel);
        contentPanel.add(secondLine);

        JPanel thirdLine = createRowPanel();
        JLabel clientTextLabel = new JLabel(I18N.getString("client"));
        // Insert actual values HERE
        JLabel clientLabel = new JLabel("Vin Diesel");
        JLabel dueDateTextLabel = new JLabel(I18N.getString("dueDate"));
        // Insert actual values HERE
        JLabel dueDateLabel = new JLabel("32.5.2012");
        thirdLine.add(clientTextLabel);
        thirdLine.add(Box.createHorizontalStrut(5));
        thirdLine.add(clientLabel);
        thirdLine.add(Box.createHorizontalGlue());
        thirdLine.add(dueDateTextLabel);
        thirdLine.add(Box.createHorizontalStrut(5));
        thirdLine.add(dueDateLabel);
        contentPanel.add(thirdLine);
    }

    private JPanel createRowPanel(){
        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(740, 20));
        panel.setPreferredSize(new Dimension(740, 20));
        panel.setMaximumSize(new Dimension(740, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        return panel;
    }

    private JPanel createTablePanel(){
        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(600, 400));
        panel.setPreferredSize(new Dimension(600, 400));
        panel.setMaximumSize(new Dimension(600, 400));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //Insert table HERE
        JScrollPane tablePane = new JScrollPane();
        tablePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tablePane.setBackground(new Color(76, 175, 80));
        tablePane.setBorder(new MatteBorder(0,0,1,0, Color.BLACK));
        tablePane.getViewport().setBackground(Color.WHITE);

        panel.add(tablePane);
        panel.add(Box.createVerticalStrut(15));
        panel.add(createTotalPanel());

        return(panel);
    }

    private JPanel createTotalPanel(){
        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(600, 30));
        panel.setPreferredSize(new Dimension(600, 30));
        panel.setMaximumSize(new Dimension(600, 30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        // Insert actual values HERE
        total = new JLabel(I18N.getString("total") + " 15 000 $");
        panel.add(Box.createHorizontalGlue());
        panel.add(total);
        panel.add(Box.createHorizontalStrut(20));

        return panel;
    }

    private JPanel createSendPanel(){
        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(740, 100));
        panel.setPreferredSize(new Dimension(740, 100));
        panel.setMaximumSize(new Dimension(740, 100));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JButton btnConfirm = new JButton(I18N.getButtonString("confirm"));
        btnConfirm.setUI(new RoundedButton(new Color(76, 175, 80), Icons.CONFIRM_ICON));
        btnConfirm.addActionListener(e -> dispose());

        JButton btnSend = new JButton(I18N.getButtonString("send"));
        btnSend.setUI(new RoundedButton(new Color(76, 175, 80), Icons.SEND_ICON));

        panel.add(btnSend);
        panel.add(Box.createHorizontalGlue());
        panel.add(btnConfirm);

        return(panel);
    }

    private void setUpDialog() {
        setUndecorated(true);
        setSize(new Dimension(800,650));
        setLocationRelativeTo(null);
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    }

    private void setUpMover() {
        cm.setDragInsets(new Insets(5, 5, 5, 5));
        cm.registerComponent(this);
    }
}
