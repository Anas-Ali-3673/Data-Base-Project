package org.example.pl;

import org.example.bll.SignUpUser;
import org.example.dto.SignUpDto;
import org.example.ui.UiUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpFrame extends JFrame {
    private final JTextField usernameField;
    private final JLabel usernameErrorLabel;
    private final JPasswordField passwordField;
    private final JLabel passwordErrorLabel;
    private final JTextField emailField;
    private final JLabel emailErrorLabel;
    private final JTextField addressField;
    private final JLabel addressErrorLabel;
    private final JComboBox<String> roleComboBox;
    private final SignUpUser signUp;
    private final Map<String, SignUpDto> users;
    private final JLabel messageLabel;

    public SignUpFrame(Map<String, SignUpDto> users, JLabel messageLabel) {
        this.users = users;
        this.messageLabel = messageLabel;
        setTitle("Sign Up");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        signUp = new SignUpUser();

        BackgroundPanel signUpPanel = new BackgroundPanel("src/main/resources/bg.jpg");
        signUpPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username field and validation
        gbc.gridx = 0;
        gbc.gridy = 0;
        signUpPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(20);
        signUpPanel.add(usernameField, gbc);

        gbc.gridy = 1;
        usernameErrorLabel = new JLabel();
        usernameErrorLabel.setForeground(Color.RED);
        signUpPanel.add(usernameErrorLabel, gbc);

        // Password field and validation
        gbc.gridx = 0;
        gbc.gridy = 2;
        signUpPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        signUpPanel.add(passwordField, gbc);

        gbc.gridy = 3;
        passwordErrorLabel = new JLabel();
        passwordErrorLabel.setForeground(Color.RED);
        signUpPanel.add(passwordErrorLabel, gbc);

        // Email field and validation
        gbc.gridx = 0;
        gbc.gridy = 4;
        signUpPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        signUpPanel.add(emailField, gbc);

        gbc.gridy = 5;
        emailErrorLabel = new JLabel();
        emailErrorLabel.setForeground(Color.RED);
        signUpPanel.add(emailErrorLabel, gbc);

        // Address field and validation
        gbc.gridx = 0;
        gbc.gridy = 6;
        signUpPanel.add(new JLabel("Address:"), gbc);

        gbc.gridx = 1;
        addressField = new JTextField(20);
        signUpPanel.add(addressField, gbc);

        gbc.gridy = 7;
        addressErrorLabel = new JLabel();
        addressErrorLabel.setForeground(Color.RED);
        signUpPanel.add(addressErrorLabel, gbc);

        // Role field
        gbc.gridx = 0;
        gbc.gridy = 8;
        signUpPanel.add(new JLabel("Role:"), gbc);

        gbc.gridx = 1;
        roleComboBox = new JComboBox<>(new String[]{"customer", "cashier"});
        signUpPanel.add(roleComboBox, gbc);

        // Register button
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterAction(users, messageLabel));
        signUpPanel.add(registerButton, gbc);

        // Message label
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        signUpPanel.add(messageLabel, gbc);

        // Add listeners
        addListeners();

        // Add panel and listeners
        add(signUpPanel);
        setVisible(true);
        UiUtils.setButtonCursor(this);
    }

    private void addListeners() {
        usernameField.getDocument().addDocumentListener(new ValidationListener(() -> {
            String username = usernameField.getText().trim();
            if (username.isEmpty()) {
                usernameErrorLabel.setText("Username cannot be empty.");
            } else if (users.containsKey(username)) {
                usernameErrorLabel.setText("Username already exists.");
            } else {
                usernameErrorLabel.setText("");
            }
        }));

        passwordField.getDocument().addDocumentListener(new ValidationListener(() -> {
            String password = new String(passwordField.getPassword()).trim();
            if (password.isEmpty()) {
                passwordErrorLabel.setText("Password cannot be empty.");
            } else if (password.length() < 8) {
                passwordErrorLabel.setText("Password must be at least 8 characters.");
            } else {
                passwordErrorLabel.setText("");
            }
        }));

        emailField.getDocument().addDocumentListener(new ValidationListener(() -> {
            String email = emailField.getText().trim();
            if (email.isEmpty()) {
                emailErrorLabel.setText("Email cannot be empty.");
            } else if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", email)) {
                emailErrorLabel.setText("Invalid email format.");
            } else {
                emailErrorLabel.setText("");
            }
        }));

        addressField.getDocument().addDocumentListener(new ValidationListener(() -> {
            String address = addressField.getText().trim();
            if (address.isEmpty()) {
                addressErrorLabel.setText("Address cannot be empty.");
            } else {
                addressErrorLabel.setText("");
            }
        }));
    }

    private class RegisterAction implements ActionListener {
        private final Map<String, SignUpDto> users;
        private final JLabel messageLabel;

        public RegisterAction(Map<String, SignUpDto> users, JLabel messageLabel) {
            this.users = users;
            this.messageLabel = messageLabel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String email = emailField.getText().trim();
            String address = addressField.getText().trim();
            String role = (String) roleComboBox.getSelectedItem();

            if (usernameErrorLabel.getText().isEmpty() &&
                passwordErrorLabel.getText().isEmpty() &&
                emailErrorLabel.getText().isEmpty() &&
                addressErrorLabel.getText().isEmpty()) {

                SignUpDto signUpDto = new SignUpDto(username, password, email, role, address);
                boolean success = signUp.registerUser(signUpDto);
                if (success) {
                    messageLabel.setText("Registration successful!");
                    messageLabel.setForeground(new Color(0, 153, 0));
                    SignInFrame signInFrame = new SignInFrame(users, messageLabel); // Use shared instances
                    signInFrame.setVisible(true);
                    dispose();
                } else {
                    messageLabel.setText("Registration failed. Please try again.");
                    messageLabel.setForeground(Color.RED);
                }
            } else {
                messageLabel.setText("Please fix the errors above.");
                messageLabel.setForeground(Color.RED);
            }
        }
    }

    private static class ValidationListener implements DocumentListener {
        private final Runnable validationTask;

        public ValidationListener(Runnable validationTask) {
            this.validationTask = validationTask;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            validationTask.run();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            validationTask.run();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            validationTask.run();
        }
    }

    public static class BackgroundPanel extends JPanel {
        private final Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            this.backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Map<String, SignUpDto> users = new HashMap<>();
            JLabel messageLabel = new JLabel();
            new SignUpFrame(users, messageLabel);
        });
    }
}