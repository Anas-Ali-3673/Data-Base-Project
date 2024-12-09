package org.example.pl;

import org.example.bll.ProductManager;
import org.example.bll.SignInUser;
import org.example.dal.ProductDal;
import org.example.bll.OrderManager;
import org.example.dal.ShoppingCart;
import org.example.dto.SignUpDto;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;

public class HomePageFrame extends JFrame {
    private String userRole;
    private String username;
    private int userId;
    private Map<String, SignUpDto> users;
    private JLabel messageLabel;
    private SignInUser signInUser;

    public HomePageFrame(String userRole, String username, int userId, Map<String, SignUpDto> users, JLabel messageLabel) {
        this.userRole = userRole;
        this.username = username;
        this.userId = userId;
        this.users = users;
        this.messageLabel = messageLabel;
        this.signInUser = new SignInUser(new org.example.dal.SignIn());
        setTitle("Home Page");
        setSize(600, 500);  // Adjusted frame size for better layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add buttons
        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton inventoryButton = createStyledButton("Inventory Management");
        inventoryButton.addActionListener(e -> openInventoryManagement());
        mainPanel.add(inventoryButton, gbc);

        gbc.gridy = 1;
        JButton cartButton = createStyledButton("Shopping Cart");
        cartButton.addActionListener(e -> openShoppingCart());
        mainPanel.add(cartButton, gbc);

        gbc.gridy = 2;
        JButton orderTrackingButton = createStyledButton("Order Tracking");
        orderTrackingButton.addActionListener(e -> openOrderTracking());
        mainPanel.add(orderTrackingButton, gbc);

        gbc.gridy = 3;
        JButton profileButton = createStyledButton("User Profile");
        profileButton.addActionListener(e -> openUserProfile());
        mainPanel.add(profileButton, gbc);

        // Add a Logout button
        gbc.gridy = 4;
        JButton logoutButton = createStyledButton("Logout");
        logoutButton.addActionListener(e -> logout());
        mainPanel.add(logoutButton, gbc);

        add(mainPanel);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(46, 204, 113));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        return button;
    }

    private void openInventoryManagement() {
        new InventoryManagementFrame(new ProductManager(new ProductDal()), userRole, userId, username, users, messageLabel).setVisible(true);
    }
    private void openShoppingCart() {
        new ShoppingCartPanel(new ShoppingCart(), this, userId).setVisible(true);
    }

    private void openOrderTracking() {
        new OrderTrackingFrame(new OrderManager(), userId).setVisible(true);
    }

    private void openUserProfile() {
        // Create the profile window
        JFrame profileFrame = new JFrame("User Profile");
        profileFrame.setSize(400, 350);  // Adjusted size for better layout
        profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        profileFrame.setLocationRelativeTo(this);

        // Main panel for the profile window
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(new Color(250, 250, 250));

        // Create a panel for the profile picture
        JPanel picturePanel = new JPanel();
        picturePanel.setBackground(Color.WHITE);
        JLabel pictureLabel = new JLabel();
        pictureLabel.setHorizontalAlignment(JLabel.CENTER);
        pictureLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Load and set the profile image
        try {
            ImageIcon profileImage = new ImageIcon("src/main/resources/profile.jpg");
            Image scaledImage = profileImage.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            pictureLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            pictureLabel.setText("Profile Picture Not Found");
        }
        
        picturePanel.add(pictureLabel);
        profilePanel.add(picturePanel);

        // Create a panel for user information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3, 2, 10, 10));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Display the user info in labels (this can be updated dynamically)
        infoPanel.add(new JLabel("Username:"));
        infoPanel.add(new JLabel(username));  // Replace with dynamic data
        infoPanel.add(new JLabel("Email:"));
        infoPanel.add(new JLabel("user123@example.com"));  // Replace with dynamic data
        infoPanel.add(new JLabel("Role:"));
        infoPanel.add(new JLabel(userRole));  // Display the role (Admin, Customer, etc.)

        profilePanel.add(infoPanel);

        // Add action buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        JButton editButton = createStyledButton("Edit Profile");
        editButton.addActionListener(e -> editProfile());
        buttonPanel.add(editButton);

        JButton changePasswordButton = createStyledButton("Change Password");
        changePasswordButton.addActionListener(e -> changePassword());
        buttonPanel.add(changePasswordButton);

        JButton logoutButton = createStyledButton("Logout");
        logoutButton.addActionListener(e -> logout());
        buttonPanel.add(logoutButton);

        profilePanel.add(buttonPanel);

        // Finalize the profile frame
        profileFrame.add(profilePanel);
        profileFrame.setVisible(true);
    }

    private void editProfile() {
        // Create a dialog for editing profile information
        JDialog editDialog = new JDialog(this, "Edit Profile", true);
        editDialog.setSize(300, 200);
        editDialog.setLocationRelativeTo(this);

        JPanel editPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        editPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField usernameField = new JTextField(username);
        JTextField emailField = new JTextField("user123@example.com"); // Replace with dynamic data

        editPanel.add(new JLabel("Username:"));
        editPanel.add(usernameField);
        editPanel.add(new JLabel("Email:"));
        editPanel.add(emailField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            // Update the user information
            String newUsername = usernameField.getText();
            String newEmail = emailField.getText();
            // Implement logic to save the updated information
            JOptionPane.showMessageDialog(this, "Profile updated successfully.", "Edit Profile", JOptionPane.INFORMATION_MESSAGE);
            editDialog.dispose();
        });

        editPanel.add(new JLabel()); // Empty label for spacing
        editPanel.add(saveButton);

        editDialog.add(editPanel);
        editDialog.setVisible(true);
    }

    private void changePassword() {
        String newPassword = JOptionPane.showInputDialog(this, "Enter new password:");
        if (newPassword != null && !newPassword.isEmpty()) {
            if (signInUser.changePassword(username, newPassword)) {
                JOptionPane.showMessageDialog(this, "Password changed successfully.", "Change Password", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to change password.", "Change Password", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void logout() {
        // Implement logout functionality, can be customized as needed
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose(); // Close the current frame
            new SignInFrame(users, messageLabel).setVisible(true); // Navigate back to sign-in page
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Map<String, SignUpDto> users = new HashMap<>();
            JLabel messageLabel = new JLabel();

            // Prompt the user for the username and role
            String username = JOptionPane.showInputDialog(null, "Enter your username:", "Username", JOptionPane.QUESTION_MESSAGE);
            String[] roles = {"customer", "admin", "manager"};
            String userRole = (String) JOptionPane.showInputDialog(null, "Select your role:", "Role", JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);

            if (username != null && userRole != null) {
                int userId = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter your user ID:", "User ID", JOptionPane.QUESTION_MESSAGE));
                new HomePageFrame(userRole, username, userId, users, messageLabel).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Username, role, and user ID are required to proceed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}