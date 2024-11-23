package org.example.pl;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    public Dashboard(String role) {
        setTitle("Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome to the Dashboard, " + role + "!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(welcomeLabel, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }
}