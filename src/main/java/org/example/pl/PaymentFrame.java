package org.example.pl;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import org.example.bll.PaymentManager;
import org.example.dal.ShoppingCart;
import org.example.dto.PaymentDto;
import org.example.helper.UserSession;
import org.example.ui.UiUtils;

public class PaymentFrame extends JFrame {
    private final ShoppingCart shoppingCart;
    private final JFrame parentFrame;
    private final int userId;
    private final PaymentManager paymentManager;

    private JTextField accountNumberField;
    private JComboBox<String> paymentMethodComboBox;
    private JLabel accountNumberErrorLabel;

    public PaymentFrame(ShoppingCart shoppingCart, JFrame parentFrame, UserSession userSession) {
        this.shoppingCart = shoppingCart;
        this.parentFrame = parentFrame;
        this.userId = userSession.getUserId(); // Ensure getUserId() returns int
        this.paymentManager = new PaymentManager();

        setTitle("Payment Details");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainPanel);

        // Payment Form
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        UiUtils.setButtonCursor(this);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBackground(new Color(255, 255, 255));
        formPanel.setBorder(new CompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                "Enter Payment Details",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(52, 73, 94)
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Payment Method
        formPanel.add(createStyledLabel("Payment Method:"));
        paymentMethodComboBox = createPaymentMethodComboBox();
        formPanel.add(paymentMethodComboBox);

        // Account Number
        formPanel.add(createStyledLabel("Account Number:"));
        accountNumberField = createStyledTextField();
        formPanel.add(accountNumberField);

        // Account Number Error
        formPanel.add(new JLabel()); // Empty cell
        accountNumberErrorLabel = createErrorLabel();
        formPanel.add(accountNumberErrorLabel);

        // Total Amount
        formPanel.add(createStyledLabel("Total Amount:"));
        JLabel totalAmountLabel = createTotalAmountLabel();
        formPanel.add(totalAmountLabel);

        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

        // Pay Now Button
        JButton payButton = createStyledButton("Pay Now", new Color(46, 204, 113));
        payButton.addActionListener(e -> processPayment());

        // Cancel Button
        JButton cancelButton = createStyledButton("Cancel", new Color(231, 76, 60));
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(payButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(52, 73, 94));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setForeground(new Color(52, 73, 94));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }

    private JLabel createErrorLabel() {
        JLabel errorLabel = new JLabel();
        errorLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        errorLabel.setForeground(Color.RED);
        return errorLabel;
    }

    private JComboBox<String> createPaymentMethodComboBox() {
        String[] paymentMethods = {"Credit Card", "Easypaisa", "JazzCash"};
        JComboBox<String> comboBox = new JComboBox<>(paymentMethods);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        comboBox.setForeground(new Color(52, 73, 94));
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        return comboBox;
    }

    private JLabel createTotalAmountLabel() {
        JLabel label = new JLabel("$" + String.format("%.2f", shoppingCart.getTotalCost()));
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(new Color(52, 73, 94));
        return label;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 73, 94)),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        return button;
    }

    private void processPayment() {
        String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
        String accountNumber = accountNumberField.getText().trim();

        accountNumberErrorLabel.setText(""); // Clear error label

        if (!paymentManager.isUserExists(userId)) {
            JOptionPane.showMessageDialog(this, "User does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String validationError = validateAccountNumber(accountNumber, paymentMethod);
        if (validationError != null) {
            accountNumberErrorLabel.setText(validationError);
            return;
        }

        double totalAmount = shoppingCart.getTotalCost();
        PaymentDto paymentDto = new PaymentDto(userId, paymentMethod, accountNumber, totalAmount);

        boolean paymentSaved = paymentManager.savePaymentToDatabase(paymentDto);
        if (!paymentSaved) {
            JOptionPane.showMessageDialog(this, "Failed to save payment details.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean orderPlaced = paymentManager.placeOrder(userId, shoppingCart);
        if (orderPlaced) {
            JOptionPane.showMessageDialog(this, "Payment and order placed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            shoppingCart.clearCart();
            parentFrame.dispose();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to place order.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String validateAccountNumber(String accountNumber, String paymentMethod) {
        if (accountNumber.isEmpty()) {
            return "Account number cannot be empty.";
        }
        if (!accountNumber.matches("\\d+")) {
            return "Account number must be numeric.";
        }
        int length = accountNumber.length();
        if ("Easypaisa".equals(paymentMethod) || "JazzCash".equals(paymentMethod)) {
            if (length != 11) {
                return "Account number must be 11 digits for Easypaisa/JazzCash.";
            }
        } else if ("Credit Card".equals(paymentMethod)) {
            if (length != 14 && length != 16) {
                return "Credit card number must be 14 or 16 digits.";
            }
        }
        return null; 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserSession userSession = UserSession.getInstance();
            String role = userSession.getRole();
            int userId = userSession.getUserId(); // Ensure getUserId() returns int
            new PaymentFrame(new ShoppingCart(userId), new JFrame(), userSession).setVisible(true);
        });
    }
}
