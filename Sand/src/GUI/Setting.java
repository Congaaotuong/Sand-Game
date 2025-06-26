package GUI;

import GUI.ElementsList;
import Sand.Main;

import javax.swing.*;
import java.awt.*;

public class Setting extends JPanel {

    private JPanel rightPanel; // Dùng để thay đổi nội dung bên phải
    private ElementsList elementsList = new ElementsList(); // Panel danh sách nguyên tố
    private JScrollPane tutorialPanel; // Panel hướng dẫn để lưu lại
    private String target;

    public Setting(String target) {
        this.target =target;
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.gridy = 0;


        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JButton btnButton = createStyledButton("Control", new Color(34, 139, 34));
        JButton btnElementList = createStyledButton("Elements", new Color(70, 130, 180));
        JButton btnBackToMenu = createStyledButton("Back", new Color(255, 69, 0));

        btnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnElementList.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBackToMenu.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnButton.setMaximumSize(new Dimension(250, 60));
        btnElementList.setMaximumSize(new Dimension(250, 60));
        btnBackToMenu.setMaximumSize(new Dimension(250, 60));

        leftPanel.add(btnButton);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(btnElementList);
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(btnBackToMenu);


        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.GRAY);
        rightPanel.setOpaque(true);


        JLabel titleLabel = new JLabel(
                "<html><h2 style='color:white;'>Tutorial</h2></html>",
                SwingConstants.CENTER
        );
        JTextArea guideArea = new JTextArea(
                "[ESC] Return to Main menu\n"
                        + "[C] Clear the board\n"
                        + "[SPACE] Pause/Continue simulation\n"
                        + "[H] Open Heat/Normal map\n"
                        + "[P] Open information table\n"
                        + "[1] Circle brush\n"
                        + "[2] Square brush\n"
                        + "[LEFT MOUSE] Draw\n"
                        + "[RIGHT MOUSE] Erase\n"
        );
        guideArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        guideArea.setForeground(Color.WHITE);
        guideArea.setBackground(Color.GRAY);
        guideArea.setEditable(false);
        tutorialPanel = new JScrollPane(guideArea);
        tutorialPanel.setBorder(null);

        rightPanel.add(titleLabel, BorderLayout.NORTH);
        rightPanel.add(tutorialPanel, BorderLayout.CENTER);


        btnElementList.addActionListener(e -> {
            rightPanel.removeAll();
            rightPanel.add(elementsList, BorderLayout.CENTER);
            rightPanel.revalidate();
            rightPanel.repaint();
        });


        btnButton.addActionListener(e -> {
            rightPanel.removeAll();
            rightPanel.add(titleLabel, BorderLayout.NORTH);
            rightPanel.add(tutorialPanel, BorderLayout.CENTER);
            rightPanel.revalidate();
            rightPanel.repaint();
        });


        btnBackToMenu.addActionListener(e -> Main.RequestScene(target));


        // ==== ADD TO MAIN PANEL ====
        gbc.gridx = 0;
        gbc.weightx = 0.33;
        add(leftPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.67;
        add(rightPanel, gbc);
    }

    private JButton createStyledButton(String text, Color borderColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        Color lightColor = new Color(
                Math.min(borderColor.getRed() + 60, 255),
                Math.min(borderColor.getGreen() + 60, 255),
                Math.min(borderColor.getBlue() + 60, 255)
        );
        button.setBackground(lightColor);
        button.setForeground(borderColor);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(borderColor, 4));
        return button;
    }
}
