package org.example.pl;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

public class UserManagementFrame extends JFrame {
    public UserManagementFrame() {
        setTitle("User Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel userPanel = new JPanel();
        userPanel.setBackground(new Color(173, 216, 230)); // Light blue background color
        userPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add user management fields and buttons here

        add(userPanel);
        setVisible(true);
    }
}