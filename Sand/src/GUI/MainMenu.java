package GUI;

import javax.swing.*;
import java.awt.*;
import Sand.Main;

public class MainMenu extends JPanel {

    public MainMenu() {
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK); // Nền đen

        // Panel chứa nút
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setOpaque(false); // Đảm bảo trong suốt để nền đen hiển thị
        add(buttonPanel); // GridBagLayout sẽ căn giữa


    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 15, 15));
        panel.setOpaque(false);

        JButton btnStart = createStyledButton("Start", new Color(34, 139, 34));
        JButton btnSave = createStyledButton("Save", new Color(70, 130, 180));
        JButton btnSetting = createStyledButton("Setting", new Color(128, 0, 128));

        btnStart.addActionListener(e -> Main.RequestScene("world"));
        btnSave.addActionListener(e -> Main.RequestScene("login"));
        btnSetting.addActionListener(e -> Main.RequestScene("Setting"));

        panel.add(btnStart);
        panel.add(btnSave);
        panel.add(btnSetting);

        return panel;
    }

    private JButton createStyledButton(String text, Color borderColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 28));

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
        button.setMargin(new Insets(20, 40, 20, 40));
        button.setPreferredSize(new Dimension(250, 80));

        return button;
    }
}
