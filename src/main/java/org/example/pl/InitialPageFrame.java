package org.example.pl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.example.dto.SignUpDto;
import org.example.ui.UiUtils;

public class InitialPageFrame extends JFrame {
    private JLabel headerLabel;
    private JLabel footerLabel;
    private JLabel discountsLabel;
    private JPanel productPanel;
    private final Map<String, SignUpDto> users; // Shared users map
    private final JLabel messageLabel; // Shared message label
    private int headerX = -500; // Starting position for header animation
    private float productAlpha = 0.0f; // Transparency for fade-in effect

    public InitialPageFrame(Map<String, SignUpDto> users, JLabel messageLabel) {
        this.users = users; // Reuse existing users map
        this.messageLabel = messageLabel; // Reuse existing message label

        setTitle("E-Commerce Store");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        UiUtils.setButtonCursor(this);

        // Start animations
        startHeaderAnimation();
        startProductAnimation();
    }

    private void initComponents() {
        // Custom background panel with rounded edges and gradient
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(50, 50, 50), getWidth(), getHeight(), new Color(30, 30, 30));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
            }
        };
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(mainPanel);

        // Header section
        JPanel headerPanel = new JPanel(null); // Use null layout for animation
        headerPanel.setOpaque(false);
        headerLabel = new JLabel("Welcome to Our E-Commerce Store!", JLabel.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 32));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBounds(headerX, 10, 800, 50); // Start off-screen
        headerPanel.add(headerLabel);

        discountsLabel = new JLabel("Exclusive Tech Deals: Upto 70% Off!", JLabel.CENTER);
        discountsLabel.setFont(new Font("Serif", Font.BOLD, 24));
        discountsLabel.setForeground(new Color(255, 204, 0));
        discountsLabel.setBounds(100, 70, 700, 30);
        headerPanel.add(discountsLabel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center section with product-like design
        productPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, productAlpha));
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(50, 50, 50));
                g2d.fillRoundRect(50, 50, getWidth() - 100, getHeight() - 100, 20, 20);
            }
        };
        productPanel.setOpaque(false);
        productPanel.setLayout(new GridBagLayout());

        JLabel productLabel = new JLabel("<html><center>Latest Tech Gadgets<br>Experience the Future</center></html>", JLabel.CENTER);
        productLabel.setFont(new Font("Sans Serif", Font.PLAIN, 18));
        productLabel.setForeground(Color.WHITE);
        productPanel.add(productLabel);

        mainPanel.add(productPanel, BorderLayout.CENTER);

        // Footer section with Sign In and Sign Up links
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setOpaque(false);

        footerLabel = new JLabel("Copyright © E-Commerce Store | All Rights Reserved ", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        footerLabel.setForeground(new Color(200, 200, 200));

        // Create the Sign In and Sign Up links
        JPanel linksPanel = new JPanel(new GridLayout());
        linksPanel.setOpaque(false);

        JLabel signInLabel = createLinkLabel("Sign In");
        JLabel signUpLabel = createLinkLabel("Sign Up");

        linksPanel.add(signInLabel);
        linksPanel.add(new JLabel(" | "));
        linksPanel.add(signUpLabel);

        footerPanel.add(linksPanel, BorderLayout.CENTER);
        footerPanel.add(footerLabel, BorderLayout.SOUTH);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    private JLabel createLinkLabel(String text) {
        JLabel linkLabel = new JLabel(text);
        linkLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        linkLabel.setForeground(new Color(0, 122, 204)); // Blue color
        linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                linkLabel.setForeground(new Color(0, 90, 180)); // Darker blue on hover
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                linkLabel.setForeground(new Color(0, 122, 204)); // Default blue color
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                SwingUtilities.invokeLater(() -> {
                    if (text.equals("Sign In")) {
                        SignInFrame signInFrame = new SignInFrame(users, messageLabel); // Use shared instances
                        signInFrame.setVisible(true);
                    } else if (text.equals("Sign Up")) {
                        SignUpFrame signUpFrame = new SignUpFrame(users, messageLabel); // Use shared instances
                        signUpFrame.setVisible(true);
                    }
                    setVisible(false); // Hide InitialPageFrame instead of disposing it
                });
            }
        });
        return linkLabel;
    }

    private void startHeaderAnimation() {
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                headerX += 5;
                headerLabel.setBounds(headerX, 10, 800, 50);
                if (headerX >= 50) { // Stop when the header reaches the final position
                    ((Timer) e.getSource()).stop();
                }
                headerLabel.repaint();
            }
        });
        timer.start();
    }

    private void startProductAnimation() {
        Timer timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productAlpha += 0.05f;
                if (productAlpha >= 1.0f) { // Stop when fully opaque
                    productAlpha = 1.0f;
                    ((Timer) e.getSource()).stop();
                }
                productPanel.repaint();
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Map<String, SignUpDto> users = new HashMap<>();
            JLabel messageLabel = new JLabel();
            new InitialPageFrame(users, messageLabel).setVisible(true);
        });
    }
}
