package pv168.freelancer.ui.details;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import pv168.freelancer.ui.buttons.MinimizeButton;
import pv168.freelancer.ui.buttons.QuitButton;
import pv168.freelancer.ui.buttons.RoundedButton;
import pv168.freelancer.ui.utils.ComponentMover;
import pv168.freelancer.ui.utils.DateLabelFormatter;
import pv168.freelancer.ui.utils.I18N;
import pv168.freelancer.ui.utils.Icons;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Date;
import java.util.Properties;

/**
 *
 *
 * @author xbenes2
 */
public class InvoiceDetail extends JDialog {

    private JPanel quitPanel;
    private JPanel contentPanel;

    JTextField client;

    private JLabel income;

    private JDatePickerImpl issuedDate;
    private JDatePickerImpl dueDate;

    private final ComponentMover cm = new ComponentMover();

    private static final I18N I18N = new I18N(InvoiceDetail.class);

    public InvoiceDetail(JFrame owner, boolean modality){
        super(owner, modality);
        setUpDialog();

        setUpQuitPanel(owner);

        setUpContentPanel();

        add(Box.createVerticalStrut(8));
        add(quitPanel);
        add(Box.createVerticalStrut(15));
        add(contentPanel);

        setUpMover();

        setVisible(true);
    }

    private void setUpQuitPanel(JFrame owner) {
        quitPanel = new JPanel();
        quitPanel.setPreferredSize(new Dimension(840, 40));

        quitPanel.setLayout(new BoxLayout(quitPanel, BoxLayout.LINE_AXIS));
        // The Glue and Rigid Areas are a way of composing the components where one wants them
        quitPanel.add(Box.createHorizontalGlue());
        quitPanel.add(new MinimizeButton(owner));
        quitPanel.add(Box.createRigidArea(new Dimension(5,0)));
        quitPanel.add(new QuitButton(e -> dispose()));
        quitPanel.add(Box.createRigidArea(new Dimension(5,0)));
    }

    private void setUpContentPanel(){
        contentPanel = new JPanel();
        contentPanel.setMinimumSize(new Dimension(800, 630));
        contentPanel.setPreferredSize(new Dimension(800, 630));
        contentPanel.setMaximumSize(new Dimension(800, 630));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        createInfoPanels();

        createCentralPanel();

        createBottomPanel();
    }

    private void createInfoPanels(){
        JPanel firstLine = createRowPanel();
        JLabel idTextLabel = new JLabel(I18N.getString("id"));
        // Insert actual values HERE
        JLabel idLabel = new JLabel("01157");
        firstLine.add(idTextLabel);
        firstLine.add(Box.createHorizontalStrut(5));
        firstLine.add(idLabel);
        contentPanel.add(firstLine);
        contentPanel.add(Box.createVerticalStrut(10));

        JPanel secondLine = createRowPanel();
        JLabel issuerTextLabel = new JLabel(I18N.getString("issuer"));
        JLabel issuerLabel = new JLabel("It's a Me");
        JLabel issuedDateTextLabel = new JLabel(I18N.getString("issuedDate"));
        issuedDate = createDatePicker();
        secondLine.add(issuerTextLabel);
        secondLine.add(Box.createHorizontalStrut(5));
        secondLine.add(issuerLabel);
        secondLine.add(Box.createHorizontalGlue());
        secondLine.add(issuedDateTextLabel);
        secondLine.add(Box.createHorizontalStrut(5));
        secondLine.add(issuedDate);
        contentPanel.add(secondLine);

        JPanel thirdLine = createRowPanel();
        JLabel clientTextLabel = new JLabel(I18N.getString("client"));
        // Possibly a comboBox but that would require to model Clients ..
        client = new JTextField("Vin Diesel");
        client.setMinimumSize(new Dimension(190, 30));
        client.setPreferredSize(new Dimension(190, 30));
        client.setMaximumSize(new Dimension(190, 30));
        JLabel dueDateTextLabel = new JLabel(I18N.getString("dueDate"));
        dueDate = createDatePicker();
        thirdLine.add(clientTextLabel);
        thirdLine.add(Box.createHorizontalStrut(5));
        thirdLine.add(client);
        thirdLine.add(Box.createHorizontalGlue());
        thirdLine.add(dueDateTextLabel);
        thirdLine.add(Box.createHorizontalStrut(5));
        thirdLine.add(dueDate);
        contentPanel.add(thirdLine);
    }

    private JPanel createRowPanel(){
        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(780, 30));
        panel.setPreferredSize(new Dimension(780, 30));
        panel.setMaximumSize(new Dimension(780, 30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        return panel;
    }

    private JDatePickerImpl createDatePicker() {
        UtilDateModel dateModel = new UtilDateModel();
        dateModel.setValue(new Date());

        Properties p = new Properties();
        p.put("text.today", I18N.getString("today"));

        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, p);
        JDatePickerImpl dateImpl = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        // What a fancy way to style the picker ...
        ((JFormattedTextField)dateImpl.getComponents()[0]).setBorder(new LineBorder(Color.BLACK));
        ((JButton)dateImpl.getComponents()[1]).setIcon(Icons.CALENDAR_ICON);
        ((JButton)dateImpl.getComponents()[1]).setText("");
        ((JButton)dateImpl.getComponents()[1]).setMargin(new Insets(0, 0, 0, 0));
        ((JButton)dateImpl.getComponents()[1]).setContentAreaFilled(false);
        ((JButton)dateImpl.getComponents()[1]).setFocusPainted(false);
        ((JButton)dateImpl.getComponents()[1]).setBorder(new LineBorder(Color.BLACK));

        JFormattedTextField textField = dateImpl.getJFormattedTextField();
        textField.setBackground(Color.WHITE);

        dateImpl.setMinimumSize(new Dimension(120,26));
        dateImpl.setPreferredSize(new Dimension(120,26));
        dateImpl.setMaximumSize(new Dimension(120,26));

        return dateImpl;
    }

    private void createCentralPanel(){
        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(800, 400));
        panel.setPreferredSize(new Dimension(800, 400));
        panel.setMaximumSize(new Dimension(800, 400));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(createTablePanel());
        panel.add(createButtonPanel());

        contentPanel.add(Box.createVerticalStrut(25));
        contentPanel.add(panel);
    }

    private JPanel createTablePanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        //Insert table HERE
        JScrollPane tablePane = new JScrollPane();
        tablePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tablePane.setBackground(new Color(76, 175, 80));
        tablePane.setBorder(new MatteBorder(0,0,1,0, Color.BLACK));
        tablePane.getViewport().setBackground(Color.WHITE);

        panel.add(tablePane);
        return panel;
    }

    private JPanel createButtonPanel(){
        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(160, 400));
        panel.setPreferredSize(new Dimension(160, 400));
        panel.setMaximumSize(new Dimension(160, 400));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton btnAdd = new JButton(I18N.getButtonString("add"));
        btnAdd.setUI(new RoundedButton(new Color(76, 175, 80), Icons.ADD_ICON));

        JButton btnRemove = new JButton(I18N.getButtonString("remove"));
        btnRemove.setUI(new RoundedButton(new Color(246, 105, 94), Icons.DELETE_ICON));

        panel.add(Box.createVerticalStrut(110));
        panel.add(btnAdd);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnRemove);
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private void createBottomPanel(){
        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(780, 60));
        panel.setPreferredSize(new Dimension(780, 60));
        panel.setMaximumSize(new Dimension(780, 60));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        income = new JLabel(I18N.getString("total") + " 285 $");

        JButton btnConfirm = new JButton(I18N.getButtonString("confirm"));
        btnConfirm.setUI(new RoundedButton(new Color(76, 175, 80), Icons.CONFIRM_ICON));
        btnConfirm.addActionListener(e -> dispose());

        panel.add(income);
        panel.add(Box.createHorizontalGlue());
        panel.add(btnConfirm);
        panel.add(Box.createHorizontalStrut(5));

        contentPanel.add(Box.createVerticalStrut(14));
        contentPanel.add(panel);
    }

    private void setUpDialog() {
        setUndecorated(true);
        setSize(new Dimension(840,670));
        setLocationRelativeTo(null);
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    }

    private void setUpMover() {
        cm.setDragInsets(new Insets(5, 5, 5, 5));
        cm.registerComponent(this);
    }
}
