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
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JTextField emailField;
    private final JComboBox<String> roleComboBox;
    private final SignUpUser signUp;
    private final Map<String, SignUpDto> users;
    private final JLabel messageLabel;

    public SignUpFrame(Map<String, SignUpDto> users, JLabel messageLabel) {
        this.users = users;
        this.messageLabel = messageLabel;
        setTitle("Sign Up");
        setSize(450, 400);  // Updated frame size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        signUp = new SignUpUser();

        // Create a background panel
        BackgroundPanel signUpPanel = new BackgroundPanel("src/main/resources/bg.jpg");
        signUpPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // More spacing for better UI
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username label and text field
        gbc.gridx = 0;
        gbc.gridy = 0;
        signUpPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(20);  // Increased width for better input
        signUpPanel.add(usernameField, gbc);

        // Password label and text field
        gbc.gridx = 0;
        gbc.gridy = 1;
        signUpPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);  // Increased width
        signUpPanel.add(passwordField, gbc);

        // Email label and text field
        gbc.gridx = 0;
        gbc.gridy = 2;
        signUpPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);  // Increased width
        signUpPanel.add(emailField, gbc);

        // Role label and combo box
        gbc.gridx = 0;
        gbc.gridy = 3;
        signUpPanel.add(new JLabel("Role:"), gbc);

        gbc.gridx = 1;
        roleComboBox = new JComboBox<>(new String[]{"customer", "admin", "manager"});
        signUpPanel.add(roleComboBox, gbc);

        // Register button with enhanced UI
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;  // Full width
        gbc.anchor = GridBagConstraints.CENTER;
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));  // Set font style
        registerButton.setBackground(new Color(46, 204, 113));  // Green color
        registerButton.setForeground(Color.WHITE);  // White text
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createRaisedBevelBorder());
        registerButton.addActionListener(new RegisterAction(users, messageLabel));
        signUpPanel.add(registerButton, gbc);

        // Add the message label for feedback
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        signUpPanel.add(messageLabel, gbc);

        // "Sign In" link for users who already have an account
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        JLabel signInLink = new JLabel("<html><a href=''>Already have an account? Sign In</a></html>");
        signInLink.setForeground(new Color(0, 122, 255)); // Blue color
        signInLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signInLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new SignInFrame(users, messageLabel).setVisible(true); // Open Sign In frame
                dispose(); // Close the Sign Up frame
            }
        });
        signUpPanel.add(signInLink, gbc);

        // Add the panel to the frame
        add(signUpPanel);
        setVisible(true);
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
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String email = emailField.getText();
            String role = (String) roleComboBox.getSelectedItem();

            if (users.containsKey(username)) {
                messageLabel.setText("Username already exists. Please try again.");
                messageLabel.setForeground(Color.RED);
            } else {
                SignUpDto signUpDto = new SignUpDto(username, password, email, role);
                boolean success = signUp.registerUser(signUpDto);
                if (success) {
                    messageLabel.setText("Registration successful!");
                    messageLabel.setForeground(new Color(0, 153, 0));  // Green color
                    int userId = signUp.getUserId(username); // Assuming getUserId method exists in SignUpUser
                    new HomePageFrame(role, username, userId, users, messageLabel).setVisible(true);
                    dispose();
                } else {
                    messageLabel.setText("Registration failed. Please try again.");
                    messageLabel.setForeground(Color.RED);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Map<String, SignUpDto> users = new HashMap<>();
            JLabel messageLabel = new JLabel();
            new SignUpFrame(users, messageLabel);
        });
    }

    // Custom JPanel to handle background image painting
    public static class BackgroundPanel extends JPanel {
        private final Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            this.backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Scale the image to fit the panel
        }
    }
}