package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ecommerce {
    JFrame frame;

    public Ecommerce() {
        frame = new JFrame("Antb Com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.getContentPane().setBackground(new Color(173, 216, 230));

        frame.add(createLogoPanel(), BorderLayout.NORTH);
        frame.add(createButtonPanel(), BorderLayout.CENTER);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    JPanel createLogoPanel() {
        ImageIcon icon = new ImageIcon("resources/logo.png");
        Image logoImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        logoLabel.setBackground(Color.WHITE);
        logoLabel.setOpaque(true);
        logoLabel.setBorder(BorderFactory.createLineBorder(new Color(78, 31, 152), 2));

        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(new Color(173, 216, 230));
        logoPanel.setPreferredSize(new Dimension(150, 150));
        logoPanel.add(logoLabel);

        return logoPanel;
    }

    JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(173, 216, 230));
        buttonPanel.setLayout(new GridLayout(2, 2, 25, 25)); // Smaller gaps between buttons
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));

        String[] buttonLabels = {"Sign In", "Sign Up", "About", "Help"};
        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = createButton(buttonLabels[i]);
            buttonPanel.add(button);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String actionCommand = e.getActionCommand();
                    switch (actionCommand) {
                        case "Sign In":
                            showMessage(frame, "Sign In - Please enter your credentials.");
                            break;
                        case "Sign Up":
                            showMessage(frame, "Sign Up - Create a new account.");
                            break;
                        case "About":
                            showMessage(frame, "About - Welcome to our E-Commerce Store!");
                            break;
                        case "Help":
                            showMessage(frame, "Help - For assistance, please contact our support.");
                            break;
                    }
                }
            });
        }
        return buttonPanel;
    }

    JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setForeground(Color.white);
        button.setBackground(new Color(78, 106, 156));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setActionCommand(label);
        button.setPreferredSize(new Dimension(50, 30));

        return button;
    }

    void showMessage(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}