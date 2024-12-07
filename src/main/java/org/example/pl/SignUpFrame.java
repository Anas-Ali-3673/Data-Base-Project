package org.example.pl;

import org.example.bll.SignUpUser;
import org.example.dto.SignUpDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class SignUpFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JComboBox<String> roleComboBox;
    private final SignUpUser signUp; // Final reference for SignUp

    public SignUpFrame(Map<String, User> users, JLabel messageLabel) {
        setTitle("Sign Up");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        signUp = new SignUpUser(); // Correctly initialize the BLL SignUp instance

        JPanel signUpPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        signUpPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        signUpPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        signUpPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        signUpPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        signUpPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        emailField = new JTextField(15);
        signUpPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        signUpPanel.add(new JLabel("Role:"), gbc);

        gbc.gridx = 1;
        roleComboBox = new JComboBox<>(new String[]{"customer", "admin", "cashier", "delivery", "manager"});
        signUpPanel.add(roleComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterAction(users, messageLabel));
        signUpPanel.add(registerButton, gbc);

        add(signUpPanel);
        setVisible(true);
    }

    private class RegisterAction implements ActionListener {
        private final Map<String, User> users;
        private final JLabel messageLabel;

        public RegisterAction(Map<String, User> users, JLabel messageLabel) {
            this.users = users;
            this.messageLabel = messageLabel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String email = emailField.getText();
            String role = (String) roleComboBox.getSelectedItem();

            if (users.containsKey(username)) {
                messageLabel.setText("Username already exists. Please try again.");
            } else {
                SignUpDto signUpDto = new SignUpDto(username, password, email, role);
                boolean success = signUp.registerUser(signUpDto);
                if (success) {
                    messageLabel.setText("Registration successful!");
//                  new Dashboard(role);
                  // dispose();
                } else {
                    messageLabel.setText("Registration failed. Please try again.");
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Map<String, User> users = new HashMap<>();
            JLabel messageLabel = new JLabel();
            new SignUpFrame(users, messageLabel);
        });
    }
}
