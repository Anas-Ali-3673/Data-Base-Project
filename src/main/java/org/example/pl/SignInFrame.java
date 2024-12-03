package org.example.pl;

import org.example.bll.SignInUser;
import org.example.dto.SignInDto;
import org.example.dto.SignUpDto;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

public class SignInFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private SignInUser signInUser; // Reference to the BLL SignInUser class

    public SignInFrame(Map<String, SignUpDto> users, JLabel messageLabel) {
        setTitle("Sign In");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        signInUser = new SignInUser(new org.example.dal.SignIn()); // Initialize the SignInUser instance

        JPanel signInPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        signInPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        signInPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        signInPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        signInPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginAction(users, messageLabel));
        signInPanel.add(loginButton, gbc);

        add(signInPanel);
        setVisible(true);
    }

    private class LoginAction implements ActionListener {
        private Map<String, SignUpDto> users;
        private JLabel messageLabel;

        public LoginAction(Map<String, SignUpDto> users, JLabel messageLabel) {
            this.users = users;
            this.messageLabel = messageLabel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            SignInDto signInDto = new SignInDto(username, password);
            if (signInUser.loginUser(signInDto)) {
                messageLabel.setText("Login successful");
                messageLabel.setForeground(new java.awt.Color(0, 153, 0));
                JOptionPane.showMessageDialog(SignInFrame.this, "Login successful! Welcome, " + username, "Login Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
                messageLabel.setText("Login failed");
                messageLabel.setForeground(new java.awt.Color(255, 0, 0));
                JOptionPane.showMessageDialog(SignInFrame.this, "Invalid username or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Map<String, SignUpDto> users = new HashMap<>();
            JLabel messageLabel = new JLabel();
            new SignInFrame(users, messageLabel);
        });
    }
}