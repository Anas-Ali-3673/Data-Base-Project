package org.example.pl;

import org.example.bll.PromotionManager;
import org.example.dto.PromotionDto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PromotionManagementFrame extends JFrame {
    private PromotionManager promotionManager;
    private DefaultTableModel tableModel;

    public PromotionManagementFrame() {
        promotionManager = new PromotionManager();
        setTitle("Promotion Management");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Table for displaying promotions
        String[] columnNames = {"Code", "Discount Percentage", "Active", "Product ID", "User ID"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable promotionTable = new JTable(tableModel);
        promotionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only one selection at a time
        promotionTable.setFont(new Font("Arial", Font.PLAIN, 14));
        promotionTable.setRowHeight(30);  // Set row height for better visibility
        JScrollPane scrollPane = new JScrollPane(promotionTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Load promotions
        loadPromotions();

        // Panel for adding new promotions with updated UI
        JPanel addPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        addPanel.setBackground(Color.WHITE);
        addPanel.setBorder(BorderFactory.createTitledBorder("Add New Promotion"));

        addPanel.add(new JLabel("Code:"));
        JTextField codeField = new JTextField();
        addPanel.add(codeField);

        addPanel.add(new JLabel("Discount Percentage:"));
        JTextField discountField = new JTextField();
        addPanel.add(discountField);

        addPanel.add(new JLabel("Active:"));
        JCheckBox activeCheckBox = new JCheckBox();
        addPanel.add(activeCheckBox);

        addPanel.add(new JLabel("Product ID (optional):"));
        JTextField productIdField = new JTextField();
        addPanel.add(productIdField);

        addPanel.add(new JLabel("User ID (optional):"));
        JTextField userIdField = new JTextField();
        addPanel.add(userIdField);

        JButton addButton = new JButton("Add Promotion");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> {
            String code = codeField.getText();
            double discount = Double.parseDouble(discountField.getText());
            boolean isActive = activeCheckBox.isSelected();
            Integer productId = productIdField.getText().isEmpty() ? null : Integer.parseInt(productIdField.getText());
            Integer userId = userIdField.getText().isEmpty() ? null : Integer.parseInt(userIdField.getText());
            PromotionDto promotion = new PromotionDto(code, discount, isActive, productId, userId);
            if (promotionManager.addPromotion(promotion)) {
                loadPromotions();
                JOptionPane.showMessageDialog(this, "Promotion added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add promotion.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        addPanel.add(addButton);

        // Panel for updating and deleting promotions with updated UI
        JPanel managePanel = new JPanel();
        managePanel.setBackground(Color.WHITE);
        managePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));

        JButton updateButton = new JButton("Update Promotion");
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateButton.setBackground(new Color(30, 144, 255));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);
        updateButton.addActionListener(e -> {
            int selectedRow = promotionTable.getSelectedRow();
            if (selectedRow >= 0) {
                String code = (String) tableModel.getValueAt(selectedRow, 0);
                double discount = Double.parseDouble(JOptionPane.showInputDialog("Enter new discount percentage:"));
                boolean isActive = JOptionPane.showConfirmDialog(this, "Is the promotion active?", "Update Promotion", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                Integer productId = tableModel.getValueAt(selectedRow, 3) == null ? null : Integer.parseInt(tableModel.getValueAt(selectedRow, 3).toString());
                Integer userId = tableModel.getValueAt(selectedRow, 4) == null ? null : Integer.parseInt(tableModel.getValueAt(selectedRow, 4).toString());
                PromotionDto promotion = new PromotionDto(code, discount, isActive, productId, userId);
                if (promotionManager.updatePromotion(promotion)) {
                    loadPromotions();
                    JOptionPane.showMessageDialog(this, "Promotion updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update promotion.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a promotion to update.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        managePanel.add(updateButton);

        JButton deleteButton = new JButton("Delete Promotion");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setBackground(new Color(255, 99, 71));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> {
            int selectedRow = promotionTable.getSelectedRow();
            if (selectedRow >= 0) {
                String code = (String) tableModel.getValueAt(selectedRow, 0);
                if (promotionManager.deletePromotion(code)) {
                    loadPromotions();
                    JOptionPane.showMessageDialog(this, "Promotion deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete promotion.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a promotion to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        managePanel.add(deleteButton);

        mainPanel.add(addPanel, BorderLayout.NORTH);
        mainPanel.add(managePanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private void loadPromotions() {
        tableModel.setRowCount(0);
        List<PromotionDto> promotions = promotionManager.getAllPromotions();
        for (PromotionDto promotion : promotions) {
            Object[] row = {promotion.getCode(), promotion.getDiscountPercentage(), promotion.isActive(), promotion.getProductId(), promotion.getUserId()};
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PromotionManagementFrame().setVisible(true));
    }
}
