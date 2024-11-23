package org.example.pl;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class AboutUsFrame extends JFrame {
    public AboutUsFrame() {
        setTitle("About Us");
        setSize(600, 400); // Adjusted size for better display
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel aboutPanel = new JPanel();
        aboutPanel.setBackground(new Color(173, 216, 230)); // Light blue background color
        aboutPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH; // Allow the component to expand both horizontally and vertically

        // About Us content
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JLabel aboutLabel = new JLabel("<html>Welcome to our E-Commerce Store!<br><br>"
                + "We are dedicated to providing you with the best online shopping experience. "
                + "Our store offers a wide range of products, from electronics to fashion, "
                + "home appliances, and more. We pride ourselves on our excellent customer service "
                + "and fast delivery. Join our community and stay updated with the latest trends and offers.<br><br>"
                + "Our Mission:<br>"
                + "To provide high-quality products at competitive prices while ensuring a seamless shopping experience.<br><br>"
                + "Our Vision:<br>"
                + "To be the leading e-commerce platform known for exceptional customer service and innovative solutions.</html>");
        aboutLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(aboutLabel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        aboutPanel.add(scrollPane, gbc);

        add(aboutPanel);
        setVisible(true);
    }
}