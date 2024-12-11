package org.example.ui;

import java.awt.Component;
import java.awt.Cursor;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class UiUtils {
    // All Buttons Cursor Changer, by pass the JFrame
    public static void setButtonCursor(JFrame frame) {
        for (Component c : frame.getComponents()) {
           if(c.getClass().getName().equals(JButton.class)) {
               c.setCursor(new Cursor(Cursor.HAND_CURSOR));
           }
        }
    }
    public static void setButtonCursor(JComponent frame) {
        for (Component c : frame.getComponents()) {
           if(c.getClass().getName().equals(JButton.class)) {
               c.setCursor(new Cursor(Cursor.HAND_CURSOR));
           }
        }
    }

}
