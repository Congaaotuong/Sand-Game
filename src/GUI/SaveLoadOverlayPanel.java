package GUI;

import SimulationEngine.World;
import java.awt.*;
import java.io.*;
import javax.swing.*;

public class SaveLoadOverlayPanel extends JPanel  implements Serializable {
    public interface Callback {
        void onLoad(World world);
    }

    private static final int SLOT_COUNT = 5;
    private final String saveDir = "saves";
    private final WorldCanvas canvas;
    private final boolean isSave;
    private final Callback callback;
    private final Holder parentPanel;

    public SaveLoadOverlayPanel(Holder parentPanel, WorldCanvas canvas, boolean isSave, Callback callback) {
        this.parentPanel = parentPanel;
        this.canvas = canvas;
        this.isSave = isSave;
        this.callback = callback;

        setLayout(null);
        setBackground(new Color(30, 30, 30, 220));
        setBounds(400, 200, 400, 350); // vị trí nổi trên giao diện chính

        JLabel title = new JLabel(isSave ? "Chọn slot để lưu" : "Chọn slot để tải", SwingConstants.CENTER);
        title.setBounds(0, 10, 400, 30);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title);

        for (int i = 0; i < SLOT_COUNT; i++) {
            int slot = i + 1;
            String fileName = saveDir + "/save" + slot + ".sav";
            File file = new File(fileName);
            JButton btn = new JButton("Slot " + slot + (file.exists() ? " (Đã lưu)" : " (Trống)"));
            btn.setBounds(50, 60 + i * 45, 300, 40);
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setBackground(file.exists() ? new Color(46, 204, 113) : new Color(189, 195, 199));
            btn.setForeground(Color.BLACK);

            btn.addActionListener(e -> {
                if (isSave) {
                    try {
                        if (!new File(saveDir).exists()) new File(saveDir).mkdirs();
                        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
                        oos.writeObject(canvas.getWorld());
                        oos.close();
                        btn.setText("Slot " + slot + " (Đã lưu)");
                        btn.setBackground(new Color(46, 204, 113));
                        JOptionPane.showMessageDialog(parentPanel, "Đã lưu vào slot " + slot);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(parentPanel, "Lỗi lưu: " + ex.getMessage());
                    }
                } else {
                    if (file.exists()) {
                        new Thread(() -> {
                            try {
                                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
                                World loaded = (World) ois.readObject();
                                ois.close();
                                SwingUtilities.invokeLater(() -> {
                                    if (callback != null) callback.onLoad(loaded);
                                    parentPanel.remove(this);
                                    parentPanel.repaint();
                                    parentPanel.revalidate();
                                });
                            } catch (Exception ex) {
                                SwingUtilities.invokeLater(() ->
                                    JOptionPane.showMessageDialog(parentPanel, "Lỗi tải: " + ex.getMessage())
                                );
                            }
                        }).start();
                    } else {
                        JOptionPane.showMessageDialog(parentPanel, "Slot này chưa có dữ liệu!");
                    }
                }
            });
            add(btn);
        }

        JButton exitBtn = new JButton("Thoát");
        exitBtn.setBounds(150, 290, 100, 40);
        exitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        exitBtn.setBackground(new Color(231, 76, 60));
        exitBtn.setForeground(Color.WHITE);
        exitBtn.addActionListener(e -> {
            parentPanel.remove(this);
            parentPanel.repaint();
            parentPanel.revalidate();
        });
        add(exitBtn);
    }
}
