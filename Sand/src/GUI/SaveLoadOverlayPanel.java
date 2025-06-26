package GUI;

import CustomDataType.SavingObject;
import Sand.Main;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class SaveLoadOverlayPanel extends JPanel  implements Serializable {
    public interface Callback {
        void onLoad(ArrayList<SavingObject> world);
    }

    private static final int SLOT_COUNT = 5;
    private final String saveDir = "saves";
    private final WorldCanvas canvas;
    private final boolean isSave;
    private final Callback callback;
    private final Holder parentPanel;

    public SaveLoadOverlayPanel(Holder _parentPanel, WorldCanvas _canvas, boolean _isSave, Callback _callback) {
        this.parentPanel = _parentPanel;
        this.canvas = _canvas;
        this.isSave = _isSave;
        this.callback = _callback;

        setLayout(null);
        setBackground(new Color(30, 30, 30, 220));
        setBounds(560, 220, 400, 350); // vị trí nổi trên giao diện chính

        JLabel title = new JLabel(_isSave ? "Select a slot to save" : "Select a slot to load", SwingConstants.CENTER);
        title.setBounds(0, 10, 400, 30);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        add(title);
        canvas.AbsPause(true);

        for (int i = 0; i < SLOT_COUNT; i++) {
            int slot = i + 1;
            String fileName = saveDir + "/save" + slot + ".sav";
            File file = new File(fileName);
            JButton btn = new JButton("Slot " + slot + (file.exists() ? " (Saved)" : " (Empty)"));
            btn.setBounds(50, 60 + i * 45, 300, 40);
            btn.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
            btn.setBackground(file.exists() ? new Color(46, 204, 113) : new Color(189, 195, 199));
            btn.setForeground(Color.BLACK);
            btn.setBorder(new LineBorder(Color.BLACK, 3));

            btn.addActionListener(e -> {
                if (isSave) {
                    try {
                        if (!new File(saveDir).exists()) new File(saveDir).mkdirs();
                        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
                        oos.writeObject(canvas.getData());
                        oos.close();
                        btn.setText("Slot " + slot + " (Saved)");
                        btn.setBackground(new Color(46, 204, 113));
                        JOptionPane.showMessageDialog(parentPanel, "Saved to slot " + slot);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(parentPanel, "Saving error: " + ex.getMessage());
                    }
                } else {
                    if (file.exists()) {
                        new Thread(() -> {
                            try {
                                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
                                ArrayList<SavingObject> loaded = (ArrayList<SavingObject>) ois.readObject();
                                ois.close();
                                canvas.AbsPause(false);
                                canvas.Pause();
                                SwingUtilities.invokeLater(() -> {
                                    if (callback != null) callback.onLoad(loaded);
                                    parentPanel.remove(this);
                                    parentPanel.repaint();
                                    parentPanel.revalidate();
                                });
                            } catch (Exception ex) {
                                SwingUtilities.invokeLater(() ->
                                        JOptionPane.showMessageDialog(parentPanel, "Loading error: " + ex.getMessage())
                                );
                            }
                        }).start();
                    } else {
                        JOptionPane.showMessageDialog(parentPanel, "Empty slot!");
                    }
                }
            });
            add(btn);
        }

        JButton exitBtn = new JButton("Back");
        exitBtn.setBounds(150, 290, 100, 40);
        exitBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        exitBtn.setBackground(new Color(231, 76, 60));
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setBorder(new LineBorder(Color.BLACK, 3));
        exitBtn.addActionListener(e -> {
            canvas.AbsPause(false);
            parentPanel.remove(this);
            parentPanel.repaint();
            parentPanel.revalidate();
        });
        add(exitBtn);
    }
}
