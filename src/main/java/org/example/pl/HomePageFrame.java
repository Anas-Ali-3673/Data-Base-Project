package org.example.pl;

import org.example.bll.ProductManager;
import org.example.bll.SignInUser;
import org.example.dal.ProductDal;
import org.example.bll.OrderManager;
import org.example.dto.SignUpDto;
import org.example.ui.UiUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

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
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        UiUtils.setButtonCursor(this);
    }

    private void initComponents() {
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Header with animation
        JLabel headerLabel = new JLabel("Welcome to Your Dashboard", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 24));
        headerLabel.setForeground(new Color(0, 102, 204));
        Timer animationTimer = new Timer(100, null);
        animationTimer.addActionListener(e -> {
            headerLabel.setForeground(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
        });
        animationTimer.start();
        contentPanel.add(headerLabel, BorderLayout.NORTH);

        // Center Panel for cards
        JPanel cardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(140, 0, 0, 0)); // Top margin added
        cardPanel.setOpaque(false);
        cardPanel.add(createFeatureCard("Inventory Management", "Manage your inventory efficiently.", e -> openInventoryManagement(), new Color(255, 223, 186)));
        cardPanel.add(createFeatureCard("Order Tracking", "Track your order statuses.", e -> openOrderTracking(), new Color(186, 223, 255)));
        cardPanel.add(createFeatureCard("Logout", "Sign out of your account.", e -> logout(), new Color(186, 255, 186)));
        contentPanel.add(cardPanel, BorderLayout.CENTER);

        // Footer
        JLabel footerLabel = new JLabel("Copyright © E-Commerce Store | All Rights Reserved", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setForeground(Color.GRAY);
        contentPanel.add(footerLabel, BorderLayout.SOUTH);

        // Gradient background
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(135, 206, 250), getWidth(), getHeight(), new Color(0, 191, 255));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientPanel.setLayout(new BorderLayout());
        gradientPanel.add(contentPanel);

        add(gradientPanel);
    }

    private JPanel createFeatureCard(String title, String description, ActionListener action, Color cardColor) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        card.setBackground(cardColor);
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.DARK_GRAY);

        JLabel descriptionLabel = new JLabel("<html><center>" + description + "</center></html>", SwingConstants.CENTER);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionLabel.setForeground(Color.GRAY);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(descriptionLabel, BorderLayout.CENTER);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                action.actionPerformed(null);
            }
        });

        card.setPreferredSize(new Dimension(200, 150));
        return card;
    }

    private void openInventoryManagement() {
        new InventoryManagementFrame(new ProductManager(new ProductDal()), userRole, userId, username, users, messageLabel).setVisible(true);
    }

    private void openOrderTracking() {
        new OrderTrackingFrame(new OrderManager(), userId).setVisible(true);
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new SignInFrame(users, messageLabel).setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Map<String, SignUpDto> users = new HashMap<>();
            JLabel messageLabel = new JLabel();

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
