package GUI;

import Sand.Main;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JPanel {
    public Login() {
        setLayout(new BorderLayout());
        setSize(new Dimension(800, 600));

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setOpaque(true);
        backgroundLabel.setBackground(Color.BLACK);
        backgroundLabel.setLayout(new GridBagLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        JLabel title = new JLabel("Sand Game - Database");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        title.setForeground(new Color(0, 123, 255));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField databaseField = new JTextField(16);
        styleInput(databaseField);
        addPlaceholder(databaseField, "Database");

        JTextField usernameField = new JTextField(16);
        styleInput(usernameField);
        addPlaceholder(usernameField, "Username");

        JPasswordField passwordField = new JPasswordField(16);
        styleInput(passwordField);
        setPasswordPlaceholder(passwordField, "Password");

        JButton loginButton = createStyledButton("Connect to Database", new Color(0, 123, 255));
        JButton backButton = createStyledButton("Back to Main Menu", new Color(90, 90, 90));

        JTextPane text = new JTextPane();
        text.setEditable(false);
        text.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        StyledDocument doc = text.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        text.setForeground(new Color(0, 123, 255));
        text.setBackground(Color.BLACK);
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        text.setText(Main.connectionInfo());

        loginButton.addActionListener(e -> {
            String database = databaseField.getText();
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            boolean check = Main.Connect(database, user, pass);
            if(check){
                loginButton.setLabel("Database connected");
            }
            else{
                loginButton.setLabel("Connected Failed");
            }
            text.setText(Main.connectionInfo());
        });
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setLabel("Connect to Database");
            }
        });
        backButton.addActionListener(e ->{
            ResetPlaceholder(databaseField, usernameField, passwordField);
            Main.RequestScene("main menu");
        } );

        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(title);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(databaseField);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(usernameField);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(passwordField);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(loginButton);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(backButton);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(text);

        backgroundLabel.add(contentPanel);
        add(backgroundLabel, BorderLayout.CENTER);
    }

    private void styleInput(JTextField field) {
        field.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
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

    private void ResetPlaceholder(JTextField databaseField, JTextField usernameField, JPasswordField passwordField){
        databaseField.setText("Database");
        databaseField.setForeground(new Color(140, 140, 140));
        usernameField.setText("Username");
        usernameField.setForeground(new Color(140, 140, 140));
        passwordField.setEchoChar((char) 0);
        passwordField.setText("Password");
        passwordField.setForeground(new Color(140, 140, 140));
    }
}