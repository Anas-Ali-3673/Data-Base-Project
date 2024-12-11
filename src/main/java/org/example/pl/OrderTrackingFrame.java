package org.example.pl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.example.bll.OrderManager;
import org.example.bll.SignInUser;
import org.example.dto.Order;
import org.example.ui.UiUtils;

public class OrderTrackingFrame extends JFrame {
    private final OrderManager orderManager;
    private final int userId;

    public OrderTrackingFrame(OrderManager orderManager, int userId) {
        this.orderManager = orderManager;
        this.userId = userId;
        setTitle("Order Tracking");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        UiUtils.setButtonCursor(this);
    }

    private void initComponents() {
        // Title Panel
        JLabel titleLabel = new JLabel("Order Tracking");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Orders Table
        String[] columnNames = {"Order ID", "Order Date", "Total Amount", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable orderTable = new JTable(tableModel);
        orderTable.setFont(new Font("Arial", Font.PLAIN, 14));
        orderTable.setRowHeight(25);
        orderTable.setSelectionBackground(new Color(52, 152, 219));
        orderTable.setSelectionForeground(Color.WHITE);
        orderTable.setGridColor(new Color(189, 195, 199));
        orderTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        orderTable.getTableHeader().setBackground(new Color(44, 62, 80));
        orderTable.getTableHeader().setForeground(Color.WHITE);

        // Fetch and populate orders
        List<Order> orders = orderManager.getOrdersByUserId(userId);
        for (Order order : orders) {
            Object[] row = {order.getOrderId(), order.getOrderDate(), order.getTotalAmount(), order.getStatus()};
            tableModel.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton confirmDeliveryButton = new JButton("Confirm Delivery");
        styleButton(confirmDeliveryButton, new Color(46, 204, 113), Color.WHITE);

        confirmDeliveryButton.addActionListener(e -> confirmDelivery(orderTable));
        buttonPanel.add(confirmDeliveryButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(bgColor.darker(), 1));
    }

    private void confirmDelivery(JTable orderTable) {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            int orderId = (int) orderTable.getValueAt(selectedRow, 0);
            new DeliveryConfirmationFrame(orderManager, orderId).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order to confirm delivery.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Simulate a signed-in user
            SignInUser signInUser = new SignInUser(new org.example.dal.SignIn());
            String username = JOptionPane.showInputDialog(null, "Enter your username:", "Username", JOptionPane.QUESTION_MESSAGE);
            if (username != null && !username.trim().isEmpty()) {
                int userId = signInUser.getUserId(username);
                if (userId != -1) {
                    OrderManager orderManager = new OrderManager();
                    new OrderTrackingFrame(orderManager, userId).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Username is required.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}