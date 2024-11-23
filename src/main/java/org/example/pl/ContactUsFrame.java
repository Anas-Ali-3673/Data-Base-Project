package org.example.pl;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ContactUsFrame extends JFrame {
    public ContactUsFrame() {
        setTitle("Contact Us");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contactPanel = new JPanel();
        contactPanel.setBackground(new Color(173, 216, 230)); // Light blue background color
        contactPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name field
        gbc.gridx = 0;
        gbc.gridy = 0;
        contactPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        JTextField nameField = new JTextField(15);
        contactPanel.add(nameField, gbc);

        // Email field
        gbc.gridx = 0;
        gbc.gridy = 1;
        contactPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        JTextField emailField = new JTextField(15);
        contactPanel.add(emailField, gbc);

        // Message field
        gbc.gridx = 0;
        gbc.gridy = 2;
        contactPanel.add(new JLabel("Message:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 3;
        JTextArea messageArea = new JTextArea(5, 15);
        contactPanel.add(new JScrollPane(messageArea), gbc);

        // Submit button
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridheight = 1;
        JButton submitButton = new JButton("Submit");
        contactPanel.add(submitButton, gbc);

        add(contactPanel);
        setVisible(true);
    }
}