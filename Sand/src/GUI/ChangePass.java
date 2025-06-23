package GUI;

import Sand.Main;

import javax.swing.*;
import java.awt.*;

public class ChangePass extends JPanel {
    public ChangePass() {
        setLayout(new BorderLayout());
        setSize(new Dimension(800, 600));

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setOpaque(true);
        backgroundLabel.setBackground(Color.BLACK);
        backgroundLabel.setLayout(new GridBagLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        JLabel title = new JLabel("Change Password");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        title.setForeground(new Color(0, 123, 255));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField usernameField = new JTextField(16);
        styleInput(usernameField);
        addPlaceholder(usernameField, "Username");

        JPasswordField oldPasswordField = new JPasswordField(16);
        styleInput(oldPasswordField);
        setPasswordPlaceholder(oldPasswordField, "Old Password");

        JPasswordField newPasswordField = new JPasswordField(16);
        styleInput(newPasswordField);
        setPasswordPlaceholder(newPasswordField, "New Password");

        JPasswordField confirmPasswordField = new JPasswordField(16);
        styleInput(confirmPasswordField);
        setPasswordPlaceholder(confirmPasswordField, "Confirm New Password");

        JButton changeButton = createStyledButton("Change Password", new Color(0, 123, 255));
        JButton backButton = createStyledButton("Back to Login", new Color(90, 90, 90));

        changeButton.addActionListener(e -> {
            String user = usernameField.getText();
            String oldPass = new String(oldPasswordField.getPassword());
            String newPass = new String(newPasswordField.getPassword());
            String confirmPass = new String(confirmPasswordField.getPassword());

            if (user.isEmpty() || oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()
                    || user.equals("Username") || oldPass.equals("Old Password")
                    || newPass.equals("New Password") || confirmPass.equals("Confirm New Password")) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(this, "New passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, "Password successfully changed!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        backButton.addActionListener(e -> Main.RequestScene("login"));

        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(title);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(usernameField);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(oldPasswordField);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(newPasswordField);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(confirmPasswordField);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(changeButton);
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
