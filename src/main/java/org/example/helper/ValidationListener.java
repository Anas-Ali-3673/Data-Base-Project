package org.example.helper;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.function.Predicate;

public class ValidationListener implements DocumentListener {

    private final JTextField field;
    private final JLabel errorLabel;
    private final String errorMessage;
    private final Predicate<String> validationRule;

    /**
     * Constructor with default validation rule (non-empty)
     */
    public ValidationListener(JTextField field, JLabel errorLabel, String errorMessage) {
        this(field, errorLabel, errorMessage, value -> !value.trim().isEmpty());
    }

    /**
     * Constructor with custom validation rule
     */
    public ValidationListener(JTextField field, JLabel errorLabel, String errorMessage, Predicate<String> validationRule) {
        this.field = field;
        this.errorLabel = errorLabel;
        this.errorMessage = errorMessage;
        this.validationRule = validationRule;
    }


    @Override
    public void insertUpdate(DocumentEvent e) {
        validate();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        validate();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        validate();
    }

    /**
     * Performs validation based on the provided validation rule
     */
    private void validate() {
        if (!validationRule.test(field.getText())) {
            errorLabel.setText(errorMessage);
        } else {
            errorLabel.setText("");
        }
    }

    /**
     * Optional: Interface for validation callbacks (if needed)
     */
    public interface ValidationCallback {
        void onValidation(boolean isValid);
    }
}