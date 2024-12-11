package org.example.pl;

import org.example.dto.Feedback;
import org.example.dto.ProductDto;
import org.example.ui.UiUtils;
import org.example.bll.FeedbackManager;

import javax.swing.*;
import java.awt.*;

public class FeedbackFrame extends JFrame {
    private ProductDto product;
    private FeedbackManager feedbackManager;

    public FeedbackFrame(ProductDto product) {
        this.product = product;
        this.feedbackManager = new FeedbackManager();

        setTitle("Feedback for " + product.getName());
        setSize(500, 400);  // Increased height for better spacing
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Customer Name field
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("Customer Name: "));
        JTextField customerNameField = new JTextField(25);
        customerNameField.setFont(new Font("Arial", Font.PLAIN, 14));  // Styled font
        namePanel.add(customerNameField);
        mainPanel.add(namePanel);

        // Comments area
        JPanel commentsPanel = new JPanel(new BorderLayout());
        commentsPanel.setBorder(BorderFactory.createTitledBorder("Comments"));
        JTextArea commentsArea = new JTextArea(5, 25);
        commentsArea.setFont(new Font("Arial", Font.PLAIN, 14));  // Styled font for comments
        commentsArea.setLineWrap(true);
        commentsArea.setWrapStyleWord(true);
        commentsPanel.add(new JScrollPane(commentsArea), BorderLayout.CENTER);
        mainPanel.add(commentsPanel);

        // Rating dropdown
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ratingPanel.add(new JLabel("Rating (1-5): "));
        JComboBox<Integer> ratingComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        ratingComboBox.setFont(new Font("Arial", Font.PLAIN, 14));  // Styled font for dropdown
        ratingPanel.add(ratingComboBox);
        mainPanel.add(ratingPanel);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        // Styled buttons
        submitButton.setBackground(new Color(46, 204, 113));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setFocusPainted(false);

        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setFocusPainted(false);

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel);

        // Add action listeners
        submitButton.addActionListener(e -> {
            String customerName = customerNameField.getText().trim();
            String comments = commentsArea.getText().trim();

            if (customerName.isEmpty() || comments.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int rating = (int) ratingComboBox.getSelectedItem();
            Feedback feedback = new Feedback(product.getId(), customerName, comments, rating);

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to submit this feedback?",
                "Confirm Submission",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                if (feedbackManager.saveFeedback(feedback)) {
                    JOptionPane.showMessageDialog(this, "Feedback submitted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to submit feedback.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> dispose());

        // Add the main panel to the frame
        add(mainPanel);
        UiUtils.setButtonCursor(this);
    }

}