package org.example.pl;

import org.example.dal.ShoppingCart;
import org.example.dto.ProductDto;
import org.example.helper.UserSession;
import org.example.ui.UiUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ShoppingCartPanel extends JPanel {
    private final ShoppingCart shoppingCart;
    private final DefaultListModel<String> cartListModel;
    private final JLabel totalCostLabel;
    private final JFrame parentFrame;
    private final int userId;

    public ShoppingCartPanel(ShoppingCart shoppingCart, JFrame parentFrame, int userId) {
        this.shoppingCart = shoppingCart;
        this.parentFrame = parentFrame;
        this.userId = userId;
        this.cartListModel = new DefaultListModel<>();
        this.totalCostLabel = new JLabel("Total Cost: $0.00");

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title Label
        JLabel titleLabel = new JLabel("Shopping Cart");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(44, 62, 80));
        add(titleLabel, BorderLayout.NORTH);

        // Cart Items Panel
        JPanel cartPanel = new JPanel(new BorderLayout(10, 10));
        cartPanel.setBorder(BorderFactory.createTitledBorder("Items in Cart"));

        JList<String> cartList = new JList<>(cartListModel);
        cartList.setFont(new Font("Arial", Font.PLAIN, 14));
        cartList.setBackground(new Color(236, 240, 241));
        cartList.setSelectionBackground(new Color(52, 152, 219));
        cartList.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(cartList);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        cartPanel.add(scrollPane, BorderLayout.CENTER);

        // Total Cost Panel
        JPanel totalCostPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalCostLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalCostLabel.setForeground(new Color(39, 174, 96));
        totalCostPanel.add(totalCostLabel);
        cartPanel.add(totalCostPanel, BorderLayout.SOUTH);
        add(cartPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JButton checkoutButton = new JButton("Checkout");
        JButton deleteButton = new JButton("Delete Item");
        JButton updateQuantityButton = new JButton("Update Quantity");

        styleButton(checkoutButton, new Color(46, 204, 113), Color.BLUE);
        styleButton(deleteButton, new Color(231, 76, 60), Color.RED);
        styleButton(updateQuantityButton, new Color(52, 152, 219), Color.BLACK);

        buttonPanel.add(checkoutButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateQuantityButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        checkoutButton.addActionListener(e -> openPaymentFrame());
        deleteButton.addActionListener(e -> deleteSelectedItem(cartList));
        updateQuantityButton.addActionListener(e -> updateSelectedItemQuantity(cartList));

        // Initialize cart display
        updateCart();
        UiUtils.setButtonCursor(this);
    }

    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(bgColor.darker(), 1));
    }

    private void updateCart() {
        cartListModel.clear();
        for (Map.Entry<ProductDto, Integer> entry : shoppingCart.getCartItems().entrySet()) {
            cartListModel.addElement(entry.getKey().getName() + " x " + entry.getValue());
        }
        totalCostLabel.setText("Total Cost: $" + shoppingCart.getTotalCost());
    }

    private void openPaymentFrame() {
        UserSession userSession = UserSession.getInstance();
        JFrame paymentFrame = new PaymentFrame(shoppingCart, parentFrame, userSession);
        paymentFrame.setVisible(true);
        System.out.println("Opening Payment Frame... User ID: " + userSession.getUserId() + ", Username: " + userSession.getUsername());
    }
    private void deleteSelectedItem(JList<String> cartList) {
        int selectedIndex = cartList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedItem = cartListModel.getElementAt(selectedIndex);
            String productName = selectedItem.split(" x ")[0];
            shoppingCart.removeProductByName(productName);
            updateCart();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateSelectedItemQuantity(JList<String> cartList) {
        int selectedIndex = cartList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedItem = cartListModel.getElementAt(selectedIndex);
            String productName = selectedItem.split(" x ")[0];
            String quantityStr = JOptionPane.showInputDialog(this, "Enter new quantity for " + productName + ":");
            if (quantityStr != null && !quantityStr.isEmpty()) {
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    shoppingCart.updateProductQuantity(productName, quantity);
                    updateCart();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to update.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}