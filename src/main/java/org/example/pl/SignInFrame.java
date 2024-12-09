package org.example.pl;

import org.example.bll.SignInUser;
import org.example.dto.SignInDto;
import org.example.dto.SignUpDto;
import org.example.helper.UserSession;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

public class SignInFrame extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final SignInUser signInUser; // Reference to the BLL SignInUser class
    private final Map<String, SignUpDto> users;
    private final JLabel messageLabel;

    public SignInFrame(Map<String, SignUpDto> users, JLabel messageLabel) {
        this.users = users;
        this.messageLabel = messageLabel;
        setTitle("Sign In");
        setSize(400, 300);  // Adjusted frame size for better alignment
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        signInUser = new SignInUser(new org.example.dal.SignIn()); // Initialize the SignInUser instance

        // Custom panel for the background image
        BackgroundPanel signInPanel = new BackgroundPanel("src/main/resources/bg.jpg");  // Path to your background image
        signInPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Increased padding for better spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add Username label and field
        gbc.gridx = 0;
        gbc.gridy = 0;
        signInPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));  // Added font for clarity
        signInPanel.add(usernameField, gbc);

        // Add Password label and field
        gbc.gridx = 0;
        gbc.gridy = 1;
        signInPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));  // Added font for clarity
        signInPanel.add(passwordField, gbc);

        // Add Login button with customized style
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = createStyledButton("Login");
        loginButton.addActionListener(new LoginAction(users, messageLabel));
        signInPanel.add(loginButton, gbc);

        // Add message label for feedback
        gbc.gridy = 3;
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        signInPanel.add(messageLabel, gbc);

        // Add the panel to the frame
        add(signInPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(46, 204, 113));  // Green button color
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        return button;
    }

    private class LoginAction implements ActionListener {
        private final Map<String, SignUpDto> users;
        private final JLabel messageLabel;

        public LoginAction(Map<String, SignUpDto> users, JLabel messageLabel) {
            this.users = users;
            this.messageLabel = messageLabel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String email = usernameField.getText();
            String password = new String(passwordField.getPassword());
            SignInDto signInDto = new SignInDto(email, password);

            // Call the BLL to login the user
            User user = signInUser.loginUser(email, password);

            if (user != null) {
               
                UserSession userSession = UserSession.getInstance();
                userSession.setUserDetails(user.getUserId(), user.getUsername());

                
                System.out.println("User logged in: " + userSession.getUsername() + " (ID: " + userSession.getUserId() + ")");

               
                String userRole = signInUser.getUserRole(email);

              
                messageLabel.setText("Login successful");
                messageLabel.setForeground(new Color(0, 153, 0)); 
                JOptionPane.showMessageDialog(SignInFrame.this, "Login successful! Welcome, " + user.getUsername(), "Login Successful", JOptionPane.INFORMATION_MESSAGE);

                
                new HomePageFrame(userRole, user.getUsername(), user.getUserId(), users, messageLabel).setVisible(true);
                dispose();
            } else {
                
                messageLabel.setText("Invalid username or password");
                messageLabel.setForeground(Color.RED); 
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