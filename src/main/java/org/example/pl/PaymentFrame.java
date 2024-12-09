package org.example.pl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.example.bll.PaymentManager;
import org.example.dal.ShoppingCart;
import org.example.helper.UserSession;
import org.example.dto.PaymentDto;

public class PaymentFrame extends JFrame {
    private final ShoppingCart shoppingCart;
    private final JFrame parentFrame;
    private final int userId;
    private final PaymentManager paymentManager;

    public PaymentFrame(ShoppingCart shoppingCart, JFrame parentFrame, UserSession userSession) {
        this.shoppingCart = shoppingCart;
        this.parentFrame = parentFrame;
        this.userId = userSession.getUserId(); // Get the user ID from the UserSession
        this.paymentManager = new PaymentManager();

        setTitle("Payment Details");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(mainPanel);

        // Payment Form Panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Enter Payment Details"));

        formPanel.add(createStyledLabel("Payment Method:"));
        String[] paymentMethods = {"Credit Card", "Easypaisa", "JazzCash"};
        JComboBox<String> paymentMethodComboBox = new JComboBox<>(paymentMethods);
        styleComboBox(paymentMethodComboBox);
        formPanel.add(paymentMethodComboBox);

        formPanel.add(createStyledLabel("Account Number:"));
        JTextField accountNumberField = new JTextField(20);
        styleTextField(accountNumberField);
        formPanel.add(accountNumberField);

        formPanel.add(createStyledLabel("Total Amount:"));
        JLabel totalAmountLabel = new JLabel("$" + shoppingCart.getTotalCost());
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalAmountLabel.setForeground(new Color(44, 62, 80));
        formPanel.add(totalAmountLabel);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton payButton = new JButton("Pay Now");
        styleButton(payButton, new Color(46, 204, 113), Color.GREEN);
        JButton cancelButton = new JButton("Cancel");
        styleButton(cancelButton, new Color(231, 76, 60), Color.RED);

        payButton.addActionListener(e -> processPayment(paymentMethodComboBox.getSelectedItem().toString(), accountNumberField.getText()));
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(payButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(44, 62, 80));
        return label;
    }

    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setForeground(new Color(44, 62, 80));
        textField.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setForeground(new Color(44, 62, 80));
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
    }

    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(bgColor.darker(), 1));
    }

    private void processPayment(String paymentMethod, String accountNumber) {
        if (!paymentManager.isUserExists(userId)) {
            JOptionPane.showMessageDialog(this, "User does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        double totalAmount = shoppingCart.getTotalCost();
        PaymentDto paymentDto = new PaymentDto(userId, paymentMethod, accountNumber, totalAmount);
        paymentManager.savePaymentToDatabase(paymentDto);
    
        // Simulate successful payment
        JOptionPane.showMessageDialog(this, "Payment successful using " + paymentMethod + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
    
        // Place order
        if (paymentManager.placeOrder(userId, shoppingCart)) {
            JOptionPane.showMessageDialog(this, "Order placed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to place order.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
        shoppingCart.clearCart();
        parentFrame.dispose(); // Close the cart panel
        dispose(); // Close the payment frame
    }
}