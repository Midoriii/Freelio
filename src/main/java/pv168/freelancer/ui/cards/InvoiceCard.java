package pv168.freelancer.ui.cards;


import pv168.freelancer.ui.buttons.RoundedButton;
import pv168.freelancer.ui.utils.I18N;
import pv168.freelancer.ui.utils.Icons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * A card for cardLayout in MainWindow class, containing a basic overview of Invoices.
 *
 * @author xbenes2
 */
public class InvoiceCard extends JPanel {

    private Frame owner;

    private JPanel contentPanel;
    private JPanel btnPanel;

    private JLabel annualIncome;

    private JTable invoiceTable;
    private Action editAction;
    private Action deleteAction;

    private JButton btnCreate;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnDetail;

    private static final I18N I18N = new I18N(InvoiceCard.class);

    public final String name;

    public InvoiceCard(String name, Frame owner){
        this.name = name;
        this.owner = owner;
        setPreferredSize(new Dimension(890, 635));

        createContentPanel();

        createButtonPanel();

        setUpGroupLayout();
    }

    private void createContentPanel() {
        JScrollPane tablePane = new JScrollPane(invoiceTable);
        tablePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tablePane.setBackground(new Color(76, 175, 80));
        tablePane.setBorder(new MatteBorder(0,0,1,0, Color.BLACK));
        tablePane.getViewport().setBackground(Color.WHITE);

        contentPanel = new JPanel();
        contentPanel.setMinimumSize(new Dimension(470, 450));
        contentPanel.setPreferredSize(new Dimension(690, 600));
        contentPanel.setMaximumSize(new Dimension(920, 820));

        JPanel incomeLabelPanel = createIncomePanel();

        GroupLayout groupLayout = new GroupLayout(contentPanel);
        contentPanel.setLayout(groupLayout);

        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup()
                        .addComponent(incomeLabelPanel)
                        .addComponent(tablePane)
        );
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent(incomeLabelPanel)
                        .addComponent(tablePane)
        );

        contentPanel.setBorder(new EmptyBorder(50,40,50,0));
    }

    private JPanel createIncomePanel() {
        JPanel incomeLabelPanel = new JPanel();
        incomeLabelPanel.setMinimumSize(new Dimension(470, 50));
        incomeLabelPanel.setPreferredSize(new Dimension(690, 50));
        incomeLabelPanel.setMaximumSize(new Dimension(920, 50));
        incomeLabelPanel.setLayout(new BoxLayout(incomeLabelPanel, BoxLayout.X_AXIS));

        annualIncome = new JLabel(I18N.getString("income"));
        incomeLabelPanel.add(Box.createHorizontalStrut(10));
        incomeLabelPanel.add(annualIncome);
        incomeLabelPanel.add(Box.createHorizontalGlue());
        return incomeLabelPanel;
    }

    private void createButtonPanel() {
        btnPanel = new JPanel();
        btnPanel.setMinimumSize(new Dimension(180, 450));
        btnPanel.setPreferredSize(new Dimension(200, 635));
        btnPanel.setMaximumSize(new Dimension(220, 820));
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.PAGE_AXIS));

        createButtons();

        btnPanel.add(new Box.Filler(new Dimension(180, 100), new Dimension(220, 180),
                new Dimension(220, 250)));
        btnPanel.add(btnCreate);
        btnPanel.add(new Box.Filler(new Dimension(0, 8), new Dimension(0, 20),
                new Dimension(0, 35)));
        btnPanel.add(btnEdit);
        btnPanel.add(new Box.Filler(new Dimension(0, 8), new Dimension(0, 20),
                new Dimension(0, 35)));
        btnPanel.add(btnDelete);
        btnPanel.add(new Box.Filler(new Dimension(0, 30), new Dimension(0, 95),
                new Dimension(0, 185)));
        btnPanel.add(btnDetail);
        btnPanel.add(new Box.Filler(new Dimension(180, 40), new Dimension(220, 80),
                new Dimension(220, 105)));

        add(btnPanel);
    }

    private void createButtons() {
        btnCreate = new JButton(I18N.getButtonString("create"));
        btnCreate.setUI(new RoundedButton(new Color(76, 175, 80), Icons.ADD_ICON));
        //btnCreate.addActionListener(this::addWorkDone);

        btnEdit = new JButton(I18N.getButtonString("edit"));
        btnEdit.setUI(new RoundedButton(new Color(76, 175, 80), Icons.EDIT_ICON));
        //btnEdit.addActionListener(this::editWorkDone);

        btnDelete = new JButton(I18N.getButtonString("delete"));
        btnDelete.setUI(new RoundedButton(new Color(246, 105, 94), Icons.DELETE_ICON));
        //btnDelete.addActionListener(this::deleteWorkDone);

        btnDetail = new JButton(I18N.getButtonString("details"));
        btnDetail.setUI(new RoundedButton(new Color(76, 175, 80), Icons.DETAIL_ICON));
        btnDetail.addActionListener(e -> setAnnualIncome(24));
    }

    private void setUpGroupLayout() {
        GroupLayout groupLayout = new GroupLayout(this);
        setLayout(groupLayout);

        groupLayout.setHorizontalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent(contentPanel)
                        .addComponent(btnPanel)
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup()
                        .addComponent(contentPanel)
                        .addComponent(btnPanel)
        );
    }

    public void setAnnualIncome(double income){
        annualIncome.setText(String.format("<html>%s <font color=#4CAF50>%s $</font></html>",
                I18N.getString("income"), Math.round(income)));
    }
}
