package org.example.pl;

import org.example.bll.SignInUser;
import org.example.dal.SignIn;
import org.example.dto.SignInDto;
import org.example.dto.SignUpDto;
import org.example.helper.UserSession;
import org.example.ui.UiUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class SignInFrame extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final SignInUser signInUser;
    private final Map<String, SignUpDto> users;
    private final JLabel messageLabel;
    private JButton loginButton = createStyledButton("Login");

    public SignInFrame(Map<String, SignUpDto> users, JLabel messageLabel) {
        this.users = users;
        this.messageLabel = messageLabel;
        setTitle("Sign In");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        signInUser = new SignInUser(new SignIn());

        BackgroundPanel signInPanel = new BackgroundPanel("src/main/resources/bg.jpg");
        signInPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        signInPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        signInPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        signInPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        signInPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton.addActionListener(new LoginAction());
        signInPanel.add(loginButton, gbc);

        gbc.gridy = 3;
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        signInPanel.add(messageLabel, gbc);

        add(signInPanel);
        setVisible(true);
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(Color.GREEN.darker());
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        return button;
    }

    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = usernameField.getText();
            String password = new String(passwordField.getPassword());
            SignInDto signInDto = new SignInDto(email, password);

            User user = signInUser.loginUser(email, password);

            if (user != null) {
                UserSession userSession = UserSession.getInstance();
                userSession.setUserDetails(user.getUserId(), user.getUsername(),user.getRole());

                String userRole = signInUser.getUserRole(email);
                NavigationHelper.navigateToHomePage(SignInFrame.this, userRole, user, users, messageLabel);
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
}

// Utility class for navigation
class NavigationHelper {
    public static void navigateToHomePage(JFrame currentFrame, String role, User user, Map<String, SignUpDto> users, JLabel messageLabel) {
        messageLabel.setText("Login successful");
        messageLabel.setForeground(new Color(0, 153, 0));
        JOptionPane.showMessageDialog(currentFrame, "Login successful! Welcome, " + user.getUsername(), "Login Successful", JOptionPane.INFORMATION_MESSAGE);

        // Adjust HomePageFrame creation according to user role
        new HomePageFrame(role, user.getUsername(), user.getUserId(), users, messageLabel).setVisible(true);
        currentFrame.dispose();
    }
}
