package org.example.pl;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

public class EcommerceHome extends JFrame {
    private static Map<String, User> users = new HashMap<>();
    private JLabel messageLabel;
    private JPanel mainPanel;
    private GridBagConstraints gbc;

    public EcommerceHome() {
        setTitle("E-Commerce Store");
        setSize(800, 600); // Adjusted size for the new box
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(173, 216, 230)); // Light blue background color
        mainPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH; // Align components to the top

        // Title
        JLabel welcomeLabel = new JLabel("Welcome to the E-Commerce Store");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 6;
        mainPanel.add(welcomeLabel, gbc);

        // Reset gridwidth for other components
        gbc.gridwidth = 1;

        // Fields
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel aboutUsLabel = new JLabel("<html><a href=''>About Us</a></html>");
        aboutUsLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        aboutUsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new AboutUsFrame();
            }
        });
        mainPanel.add(aboutUsLabel, gbc);

        gbc.gridx = 1;
        JLabel contactUsLabel = new JLabel("<html><a href=''>Contact Us</a></html>");
        contactUsLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        contactUsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new ContactUsFrame();
            }
        });
        mainPanel.add(contactUsLabel, gbc);

        gbc.gridx = 2;
        JLabel productsLabel = new JLabel("<html><a href=''>Products</a></html>");
        productsLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        productsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new ProductsFrame();
            }
        });
        mainPanel.add(productsLabel, gbc);

        gbc.gridx = 3;
        JLabel signInLabel = new JLabel("<html><a href=''>Sign In</a></html>");
        signInLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signInLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        signInLabel.setForeground(Color.BLUE);
        signInLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new SignInFrame(users, messageLabel);
            }
        });
        mainPanel.add(signInLabel, gbc);

        gbc.gridx = 4;
        JLabel signUpLabel = new JLabel("<html><a href=''>Sign Up</a></html>");
        signUpLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        signUpLabel.setForeground(Color.BLUE);
        signUpLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new SignUpFrame(users, messageLabel);
            }
        });
        mainPanel.add(signUpLabel, gbc);

        gbc.gridx = 5;
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        messageLabel.setForeground(Color.RED);
        mainPanel.add(messageLabel, gbc);

        // New box panel
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 6;
        gbc.fill = GridBagConstraints.BOTH; // Allow the box to expand vertically
        JPanel boxPanel = new JPanel();
        boxPanel.setBackground(new Color(255, 228, 196)); // Bisque background color
        boxPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel boxLabel = new JLabel("<html>Discover the best deals and discounts on our wide range of products!<br>"
                + "Enjoy shopping with exclusive offers and promotions.<br>"
                + "We provide top-notch customer service and fast delivery.<br>"
                + "Join our community and stay updated with the latest trends and offers.<br><br>"
                + "Why choose us?<br>"
                + "1. Wide variety of products<br>"
                + "2. Competitive prices<br>"
                + "3. Excellent customer service<br>"
                + "4. Fast and reliable delivery<br>"
                + "5. Easy returns and exchanges<br>"
                + "6. Secure online shopping experience<br>"
                + "7. Regular updates on new arrivals and special offers<br>"
                + "8. Loyalty rewards for our valued customers<br>"
                + "9. User-friendly website and mobile app<br>"
                + "10. 24/7 customer support</html>");
        boxLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        boxLabel.setForeground(new Color(0, 100, 0)); // Dark green text color
        boxPanel.add(boxLabel);

        mainPanel.add(boxPanel, gbc);

        add(mainPanel);
        setVisible(true);

        // Add component listener to adjust layout on resize
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustLayout();
            }
        });
    }

    private void adjustLayout() {
        gbc.anchor = GridBagConstraints.NORTH; // Ensure components are aligned to the top
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EcommerceHome::new);
    }
}