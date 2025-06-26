package GUI;

import javax.swing.*;
import java.awt.*;
import Sand.Main;

public class MainMenu extends JPanel {

    public MainMenu() {
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK); // Nền đen

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Căn giữa

        // Thêm tiêu đề "Sand Game"
        gbc.gridy = 0;
        JLabel title = new JLabel("Sand Game");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, gbc);

        // Thêm khoảng cách giữa tiêu đề và nút
        gbc.gridy = 1;
        gbc.insets = new Insets(30, 0, 0, 0); // cách trên 30px
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setOpaque(false);
        add(buttonPanel, gbc);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 15, 15));
        panel.setOpaque(false);

        JButton btnStart = createStyledButton("Start", new Color(34, 139, 34));
        JButton btnSave = createStyledButton("Save & Load", new Color(70, 130, 180));
        JButton btnSetting = createStyledButton("Info", new Color(128, 0, 128));
        JButton btnConnect = createStyledButton("Connect", new Color(200, 200, 200));

        btnStart.addActionListener(e -> Main.RequestScene("world"));
        btnSave.addActionListener(e -> {
            Main.RequestScene("database");
        });
        btnSetting.addActionListener(e -> Main.RequestScene("Setting"));
        btnConnect.addActionListener(e -> Main.RequestScene("login"));

        panel.add(btnStart);
        panel.add(btnSave);
        panel.add(btnSetting);
        panel.add(btnConnect);

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
