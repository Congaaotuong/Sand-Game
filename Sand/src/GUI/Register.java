package GUI;

import Sand.Main;

import javax.swing.*;
import java.awt.*;

public class Register extends JPanel {
    public Register() {
        setLayout(new BorderLayout());
        setSize(new Dimension(800, 600));

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setOpaque(true);
        backgroundLabel.setBackground(Color.BLACK);
        backgroundLabel.setLayout(new GridBagLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        JLabel title = new JLabel("Sand Game - Register");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        title.setForeground(new Color(0, 123, 255));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField usernameField = new JTextField(16);
        styleInput(usernameField);
        addPlaceholder(usernameField, "Username");

        JPasswordField passwordField = new JPasswordField(16);
        styleInput(passwordField);
        setPasswordPlaceholder(passwordField, "Password");

        JPasswordField confirmField = new JPasswordField(16);
        styleInput(confirmField);
        setPasswordPlaceholder(confirmField, "Confirm Password");

        JButton registerButton = createStyledButton("Register", new Color(0, 123, 255));
        JButton backButton = createStyledButton("Back to Login", new Color(90, 90, 90));

        registerButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Account created (dummy logic)", "Register", JOptionPane.INFORMATION_MESSAGE);
        });

        backButton.addActionListener(e -> Main.RequestScene("login"));

        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(title);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(usernameField);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(passwordField);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(confirmField);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(registerButton);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(backButton);

        backgroundLabel.add(contentPanel);
        add(backgroundLabel, BorderLayout.CENTER);
    }

    private void styleInput(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(Color.WHITE);
        field.setBackground(new Color(51, 51, 51));
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        field.setMaximumSize(new Dimension(250, 35));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private JButton createStyledButton(String text, Color borderColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        Color lightColor = new Color(
                Math.min(borderColor.getRed() + 60, 255),
                Math.min(borderColor.getGreen() + 60, 255),
                Math.min(borderColor.getBlue() + 60, 255)
        );
        button.setBackground(lightColor);
        button.setForeground(borderColor);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createLineBorder(borderColor, 3));
        button.setMaximumSize(new Dimension(250, 40));
        return button;
    }

    private void addPlaceholder(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(new Color(140, 140, 140));

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.WHITE);
                }
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(new Color(140, 140, 140));
                }
            }
        });
    }

    private void setPasswordPlaceholder(JPasswordField field, String placeholder) {
        field.setEchoChar((char) 0);
        field.setText(placeholder);
        field.setForeground(new Color(140, 140, 140));

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (String.valueOf(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.WHITE);
                    field.setEchoChar('•');
                }
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if (String.valueOf(field.getPassword()).isEmpty()) {
                    field.setEchoChar((char) 0);
                    field.setText(placeholder);
                    field.setForeground(new Color(140, 140, 140));
                }
            }
        });
    }
}
