package org.example.pl;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignInFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public SignInFrame(Map<String, User> users, JLabel messageLabel) {
        setTitle("Sign In");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

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
        private Map<String, User> users;
        private JLabel messageLabel;

        public LoginAction(Map<String, User> users, JLabel messageLabel) {
            this.users = users;
            this.messageLabel = messageLabel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            User user = users.get(username);
            if (user != null && user.getPassword().equals(password)) {
                messageLabel.setText("Login successful! Welcome, " + user.getRole());
                new Dashboard(user.getRole());
                dispose();
            } else {
                messageLabel.setText("Invalid username or password. Please try again.");
            }
        }
    }
}