package GUI;

import javax.swing.*;
import java.awt.*;
import Sand.Main;

public class MainMenu extends JPanel {
    private boolean isSoundOn;
    private javax.sound.sampled.Clip clip;
    private ElementsList elementsInfo;

    public MainMenu(javax.sound.sampled.Clip clip, boolean isSoundOn, ElementsList elementsInfo) {
        this.clip = clip;
        this.isSoundOn = isSoundOn;
        this.elementsInfo = elementsInfo;

        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 15, 15)); // 5 nút, cách nhau 15px
        panel.setOpaque(false); // Trong suốt
        panel.setPreferredSize(new Dimension(300, 350));

        JButton btnStart = createStyledButton("Start", new Color(34, 139, 34));
        JButton btnSave = createStyledButton("Save", new Color(70, 130, 180));
        JButton btnMode = createStyledButton("Mode", new Color(218, 165, 32));
        JButton btnVolume = createStyledButton(isSoundOn ? "Volume: ON" : "Volume: OFF", new Color(255, 140, 0));
        JButton btnInfo = createStyledButton("Info", new Color(128, 0, 128));

        btnStart.addActionListener(e -> Main.RequestScene("world"));
        btnSave.addActionListener(e -> Main.RequestScene("login"));
        btnMode.addActionListener(e -> JOptionPane.showMessageDialog(this, "Chế độ chưa được thiết lập."));
        btnVolume.addActionListener(e -> {
            isSoundOn = !isSoundOn;
            if (isSoundOn && clip != null) {
                clip.start();
                btnVolume.setText("Volume: ON");
            } else {
                if (clip != null) clip.stop();
                btnVolume.setText("Volume: OFF");
            }
        });
        btnInfo.addActionListener(e -> elementsInfo.showElementInfo());

        panel.add(btnStart);
        panel.add(btnSave);
        panel.add(btnMode);
        panel.add(btnVolume);
        panel.add(btnInfo);

        return panel;
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
