package GUI;

import CustomDataType.SavingObject;
import Sand.Main;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class SaveLoadDatabase extends JPanel {
    JPanel content = new JPanel();
    int target = -1;
    public SaveLoadDatabase(){
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        content.setBackground(Color.BLACK);
        content.setPreferredSize(new Dimension(500, 700));
        content.setLayout(null);

        JPanel label_panel = new JPanel();
        label_panel.setBounds(125, 20, 250, 70);
        label_panel.setBackground(Color.BLACK);
        JLabel label = new JLabel("Save & Load");
        label.setFont(new Font("Comic Sans MS", Font.BOLD, 38));
        label.setForeground(new Color(10, 210, 80));
        label_panel.add(label);
        content.add(label_panel);

        JPanel text_panel = new JPanel();
        text_panel.setBounds(125, 90, 250, 70);
        text_panel.setBackground(Color.BLACK);
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
        text.setText(Main.connectionInfo()+slot_text());
        text_panel.add(text);
        content.add(text_panel);

        JPanel table_panel = new JPanel();
        table_panel.setBounds(125, 160, 250, 30);
        table_panel.setBackground(Color.BLACK);
        JTextField table = new JTextField(20);
        table.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        table.setForeground(Color.WHITE);
        table.setBackground(new Color(51, 51, 51));
        table.setCaretColor(Color.WHITE);
        table.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        table.setMaximumSize(new Dimension(250, 30));
        table.setText("table name!");
        table.setForeground(new Color(140, 140, 140));
        table.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (table.getText().equals("table name!")) {
                    table.setText("");
                    table.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent e) {
                if (table.getText().isEmpty()) {
                    table.setText("table name!");
                    table.setForeground(new Color(140, 140, 140));
                }
            }
        });
        table_panel.add(table);
        content.add(table_panel);

        JPanel slot_panel = new JPanel();
        slot_panel.setLayout(null);
        slot_panel.setBounds(0, 190, 500, 360);
        slot_panel.setBackground(Color.BLACK);
        JButton[] btns = new JButton[5];
        for (int i = 0; i < 5; i++) {
            int slot = i + 1;
            String fileName = getFileName(slot);
            File file = new File(fileName);
            String name = "Slot " + slot + (file.exists() ? " (Saved)" : " (Empty)");
            Color color;
            if(file.exists()){
                color = new Color(46, 104, 13);
            }
            else{
                color = new Color(89, 95, 99);
            }
            JButton btn = getButton(name, color, 16, 50, 20 + i * 70, 400, 50);
            btn.addActionListener(e -> {
                target=slot;
                text.setText(Main.connectionInfo()+slot_text());
            });
            slot_panel.add(btn);
            btns[i] = btn;
        }
        content.add(slot_panel);

        JPanel button_panel = new JPanel();
        button_panel.setLayout(null);
        button_panel.setBounds(0, 550, 500, 150);
        button_panel.setBackground(Color.BLACK);
        JButton back = getButton("Back", Color.DARK_GRAY, 25,200, 90, 100, 50);
        back.addActionListener(e -> Main.RequestScene("main menu"));
        button_panel.add(back);
        JButton delete = getButton("Delete", new Color(180, 20, 100), 25, 200, 20, 100, 50);
        delete.addActionListener(e -> {
            File file = new File(getFileName(target));
            if(!file.exists()) {
                JOptionPane.showMessageDialog(content, "Empty slot!");
                return;
            }
            if (file.delete()) {
                JOptionPane.showMessageDialog(content, "Delete successfully!");
                String name = "Slot " + target + " (Empty)";
                btns[target-1].setLabel(name);
                btns[target-1].setForeground(new Color(89, 95, 99));
                btns[target-1].setBackground(new Color(149, 155, 159));
            } else {
                JOptionPane.showMessageDialog(content, "Delete failed!");
            }
        });
        button_panel.add(delete);
        JButton save = getButton("Save", new Color(100, 180, 20), 25, 50, 20, 100, 50);
        save.addActionListener(e -> {
            File file = new File(getFileName(target));
            if(!file.exists()) {
                JOptionPane.showMessageDialog(content, "Empty slot!");
                return;
            }
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getFileName(target)));
                ArrayList<SavingObject> loaded = (ArrayList<SavingObject>) ois.readObject();
                ois.close();
                Main.Save(content, table.getText(), loaded);
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(content, "Saving error: " + ex.getMessage()));
            }
        });
        button_panel.add(save);
        JButton load = getButton("Load", new Color(100, 20, 180), 25, 350, 20, 100, 50);
        load.addActionListener(e -> {
            try {
                if (!new File("saves").exists()) new File("saves").mkdirs();
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFileName(target)));
                ArrayList<SavingObject> loaded = Main.Load(content, table.getText());
                if(loaded.size()==0) return;
                System.out.println(loaded.size());
                oos.writeObject(loaded);
                oos.close();
                btns[target-1].setText("Slot " + target + " (Saved)");
                btns[target-1].setBackground(new Color(106, 164, 73));
                btns[target-1].setForeground(new Color(46, 104, 13));
                JOptionPane.showMessageDialog(content, "loaded to slot " + target);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(content, "Loading error: " + ex.getMessage());
            }
        });
        button_panel.add(load);
        content.add(button_panel);

        content.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
                if (content.isShowing()) {
                    target=-1;
                    text.setText(Main.connectionInfo()+slot_text());
                    table.setText("table name!");
                    table.setForeground(new Color(140, 140, 140));
                }
                for(int i=0; i<5; i++){
                    int slot = i + 1;
                    String fileName = getFileName(slot);
                    File file = new File(fileName);
                    String name = "Slot " + slot + (file.exists() ? " (Saved)" : " (Empty)");
                    btns[i].setLabel(name);
                    if(file.exists()){
                        btns[i].setForeground(new Color(46, 104, 13));
                        btns[i].setBackground(new Color(106, 164, 73));
                    }
                    else{
                        btns[i].setForeground(new Color(89, 95, 99));
                        btns[i].setBackground(new Color(149, 155, 159));
                    }
                }
            }
        });
        add(content);
    }
    JButton getButton(String name, Color color, int size, int x, int y, int width, int height){
        JButton button = new JButton(name);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Comic Sans MS", Font.BOLD, size));
        button.setBorder(new LineBorder(color, 3));
        button.setForeground(color);
        Color lightColor = new Color(
                Math.min(color.getRed() + 60, 255),
                Math.min(color.getGreen() + 60, 255),
                Math.min(color.getBlue() + 60, 255)
        );
        button.setBackground(lightColor);
        return button;
    }
    String slot_text(){
        if(target<=0) return "\nNo file selected";
        String ans = "\nFile "+target+" selected";
        return ans;
    }
    String getFileName(int slot){
        String ans = "saves" + "/save" + slot + ".sav";
        return ans;
    }
}
