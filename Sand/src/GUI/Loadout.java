package GUI;

import Sand.Main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class Loadout {
    public Holder loadout_1(String name, int width, int height, int length){
        Holder panel = new Holder(name);
        panel.setSize(new Dimension(width*length, height*length));
        WorldCanvas canvas = new WorldCanvas(width, height, length);
        panel.add(canvas);
        panel.setBackground(new Color(51, 51, 51, 255));
        panel.setLayout(null);
        setButton(panel, canvas);

        panel.changeTheFunction(()->canvas.UpdateWorld());
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                canvas.SetMiddle(panel.getWidth(), panel.getHeight());
            }
        });
        panel.setFocusable(true);
        panel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                panel.requestFocus();
            }
        });
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if(code == 27){
                    Main.RequestScene("main menu");
                }
                if(code == 67){
                    canvas.Clean();
                }
                if (code == 32) {
                    canvas.Pause();
                }
                if(code == 72){
                    canvas.HeatMap();
                }
                if(code == 80){
                    canvas.Setting();
                }
                if(code == 49){
                    canvas.BrushType(1);
                }
                if(code == 50){
                    canvas.BrushType(0);
                }
            }
        });
        JButton saveToBtn = new JButton("Save");
        saveToBtn.setBounds(1320, 728, 100, 50);
        saveToBtn.setBackground(new Color(51, 51, 51, 255));
        saveToBtn.setForeground(Color.WHITE);
        saveToBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
        saveToBtn.setBorder(new LineBorder(Color.WHITE, 3));
        saveToBtn.addActionListener(e -> {
            if(canvas.AbsPause()) return;
            SaveLoadOverlayPanel savePanel = new SaveLoadOverlayPanel(panel, canvas, true, null);
            panel.add(savePanel, 0);
            panel.repaint();
            panel.revalidate();
        });
        panel.add(saveToBtn);

// Nút Load to
        JButton loadToBtn = new JButton("Load");
        loadToBtn.setBounds(1430, 728, 100, 50);
        loadToBtn.setBackground(new Color(51, 51, 51, 255));
        loadToBtn.setForeground(Color.WHITE);
        loadToBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
        loadToBtn.setBorder(new LineBorder(Color.WHITE, 3));
        loadToBtn.addActionListener(e -> {
            if(canvas.AbsPause()) return;
            final SaveLoadOverlayPanel[] loadPanel = new SaveLoadOverlayPanel[1];
            loadPanel[0] = new SaveLoadOverlayPanel(panel, canvas, false, loadedWorld -> {
                canvas.loadData(loadedWorld);
                canvas.repaint();
                panel.remove(loadPanel[0]);
                panel.repaint();
                panel.revalidate();
            });
            panel.add(loadPanel[0], 0);
            panel.repaint();
            panel.revalidate();
        });
        panel.add(loadToBtn);

        return panel;
    }
    public Holder loadout_HomeGame(String name, int width, int height, int length) {
        Holder panel = new Holder(name);
        panel.setSize(new Dimension(width * length, height * length));
        panel.setLayout(new BorderLayout());
        MainMenu gameMenuPanel = new MainMenu();
        panel.add(gameMenuPanel, BorderLayout.CENTER);
        return panel;
    }

    public Holder loadout_Login(String name, int width, int height, int length) {
        Holder panel = new Holder(name);
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(width * length, height * length));
        Login gameLoginPanel = new Login();
        panel.add(gameLoginPanel, BorderLayout.CENTER);
        return panel;
    }
    public Holder loadout_Database(String name, int width, int height, int length) {

        Holder panel = new Holder(name);
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(width * length, height * length));
        SaveLoadDatabase gameDatabasePanel = new SaveLoadDatabase();
        panel.add(gameDatabasePanel, BorderLayout.CENTER);
        return panel;
    }
    public Holder loadout_Setting(String name, int width, int height, int length, String target) {
        Holder panel = new Holder(name);
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(width * length, height * length));
        Setting st=new Setting(target);
        panel.add(st, BorderLayout.CENTER);
        return panel;
    }

    private JButton getBrushButton(String name, WorldCanvas canvas, int brush, int heat,int x, int y, int width, int height, Color Border, Color Internal){
        JButton custom = new JButton(name);
        custom.setBounds(x, y, width, height);
        custom.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
        custom.setBackground(Internal);
        custom.setForeground(Border);
        custom.setBorder(new LineBorder(Border, 3));
        custom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.BrushElement(brush, heat);
            }
        });
        return custom;
    }
    private JButton getButton(String name, WorldCanvas canvas, int type, int x, int y, int width, int height, Color Border, Color Internal){
        JButton custom = new JButton(name);
        custom.setBounds(x, y, width, height);
        custom.setFont(new Font("Comic Sans MS", Font.BOLD, 11));
        custom.setBackground(Internal);
        custom.setForeground(Border);
        custom.setBorder(new LineBorder(Border, 3));
        if(type==0){
            custom.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    canvas.Clean();
                }
            });
        }
        if(type==1){
            custom.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    canvas.Main();
                }
            });
        }
        if(type==2){
            custom.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    canvas.Pause();
                }
            });
        }
        if(type==3){
            custom.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    canvas.BrushType(0);
                }
            });
        }
        if(type==4){
            custom.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    canvas.BrushType(1);
                }
            });
        }
        if(type==5){
            custom.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    canvas.HeatMap();
                }
            });
        }
        if(type==6){
            custom.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    canvas.Setting();}
            });
        }
        return custom;
    }

    private void setButton(Holder panel, WorldCanvas canvas){
        JButton b1 = getBrushButton("Bedrock", canvas, 1, 0, 10, 20, 100, 40, new Color(45, 44, 44, 255), new Color(117, 96, 96, 255));
        panel.add(b1);
        JButton b2 = getBrushButton("Sand", canvas, 2, 0, 10, 70, 100, 40, new Color(189, 126, 45, 255), new Color(246, 215, 176, 255));
        panel.add(b2);
        JButton b3 = getBrushButton("Wetsand", canvas, 3, 0, 10, 120, 100, 40, new Color(112, 54, 2, 255), new Color(148, 99, 56, 255));
        panel.add(b3);
        JButton b4 = getBrushButton("Clay", canvas, 4, 0, 10, 170, 100, 40, new Color(136, 80, 62, 255), new Color(194, 150, 137, 255));
        panel.add(b4);
        JButton b5 = getBrushButton("Water", canvas, 5, 0, 10, 220, 100, 40, new Color(0, 54, 247, 255), new Color(0, 200, 255, 255));
        panel.add(b5);
        JButton b6 = getBrushButton("Magma", canvas, 6, 0, 10, 270, 100, 40, new Color(64, 11, 11, 255), new Color(255, 219, 0, 255));
        panel.add(b6);
        JButton b7 = getBrushButton("Gasoline", canvas, 7, 0, 10, 320, 100, 40, new Color(16,112,80, 255), new Color(199,246,236, 255));
        panel.add(b7);
        JButton b8 = getBrushButton("Steam", canvas, 8,0, 10, 370, 100, 40, new Color(79, 168, 176, 255), new Color(226, 228, 225, 255));
        panel.add(b8);
        JButton b9 = getBrushButton("Terracotta", canvas, 9, 0, 10, 420, 100, 40, new Color(118, 31, 31, 255), new Color(244, 125, 95, 255));
        panel.add(b9);
        JButton b11 = getBrushButton("Increase Heat", canvas, -1, 1, 250, 20, 100, 100, Color.RED, Color.ORANGE);
        panel.add(b11);
        JButton b12 = getBrushButton("Decrease Heat", canvas, -1, -1, 250, 170, 100, 100, Color.BLUE, Color.CYAN);
        panel.add(b12);
        JButton b13 = getBrushButton("Molten Glass", canvas, 10, 0, 10, 470, 100, 40, new Color(255, 102, 0, 255), new Color(242, 242, 23, 255));
        panel.add(b13);
        JButton b14 = getBrushButton("Glass", canvas, 11, 0, 10, 520, 100, 40, new Color(76, 78, 79, 255), new Color(219, 225, 227, 255));
        panel.add(b14);
        JButton b15 = getBrushButton("Ice", canvas, 12, 0, 10, 570, 100, 40, new Color(73, 232, 255, 255), new Color(227, 253, 255, 255));
        panel.add(b15);
        JButton b18 = getBrushButton("Fire", canvas, 15, 0, 10, 620, 100, 40, new Color(255, 64, 0, 255), new Color(255, 165, 0, 255));
        panel.add(b18);
        JButton b19 = getBrushButton("Smoke", canvas, 16, 0, 10, 670, 100, 40, new Color(177,177,177, 255), new Color( 26,26,26, 255));
        panel.add(b19);
        JButton b20 = getBrushButton("Copper", canvas, 17, 0, 10, 720, 100, 40, new Color(120, 53, 30, 255), new Color( 219, 121, 88, 255));
        panel.add(b20);
        JButton b21 = getBrushButton("Molten Copper", canvas, 18, 0, 10, 770, 100, 40, new Color(255, 102, 0, 255), new Color(242, 242, 23, 255));
        panel.add(b21);
        JButton b16 = getBrushButton("Heating Core", canvas, 13, 0, 120, 20, 100, 40, new Color(244, 71, 0, 255), new Color(255, 203, 183, 255));
        panel.add(b16);
        JButton b17 = getBrushButton("Cooling Core", canvas, 14, 0, 120, 70, 100, 40, new Color(57, 30, 255, 255), new Color(191, 182, 255, 255));
        panel.add(b17);
        JButton b22 = getBrushButton("Rusted Copper", canvas, 19, 0, 120, 120, 100, 40, new Color(186,163,127, 255), new Color(113,212,204, 255));
        panel.add(b22);
        JButton b23 = getBrushButton("Wood", canvas, 20, 0, 120, 170, 100, 40, new Color(85,51,17, 255), new Color(204,170,102, 255));
        panel.add(b23);
        JButton b24 = getBrushButton("Ash", canvas, 22, 0, 120, 220, 100, 40, new Color(59, 53, 53, 255), new Color(159, 147, 147, 255));
        panel.add(b24);
        JButton b25 = getBrushButton("Flammable Gas", canvas, 23, 0, 120, 270, 100, 40, new Color(36, 35, 36, 255), new Color(148, 145, 148, 255));
        panel.add(b25);
        JButton b26 = getBrushButton("Absolute Insulator", canvas, 24, 0, 120, 320, 100, 40, new Color(185,188,63, 255), new Color(244,249,255, 255));
        panel.add(b26);
        JButton b27 = getBrushButton("Steel", canvas, 25, 0, 120, 370, 100, 40, new Color(153,163,163, 255), new Color(224,229,229, 255));
        panel.add(b27);
        JButton b28 = getBrushButton("Charcoal", canvas, 26, 0, 120, 420, 100, 40, new Color(34,17,5, 255), new Color(109,94,76, 255));
        panel.add(b28);
        JButton clear = getButton("Clear (c)", canvas, 0, 1430, 20, 100, 50, Color.WHITE, new Color(51, 51, 51, 255));
        panel.add(clear);
        JButton main = getButton("Main menu (esc)", canvas, 1, 1430, 788, 100, 50, Color.WHITE, new Color(51, 51, 51, 255));
        panel.add(main);
        JButton pause = getButton("Pause (space)", canvas, 2, 1310, 20, 100, 50, Color.WHITE, new Color(51, 51, 51, 255));
        panel.add(pause);
        canvas.SetPauseButton(pause);
        JButton circle = getButton("Circle Brush (1)", canvas, 4, 1310, 80, 100, 50, Color.WHITE, new Color(51, 51, 51, 255));
        panel.add(circle);
        JButton square = getButton("Square Brush (2)", canvas, 3, 1430, 80, 100, 50, Color.WHITE, new Color(51, 51, 51, 255));
        panel.add(square);
        JButton HeatMap = getButton("Heat Map (h)", canvas, 5, 1430, 140, 100, 50, Color.WHITE, new Color(51, 51, 51, 255));
        panel.add(HeatMap);
        canvas.SetHeatButton(HeatMap);
        JButton setting = getButton("Info (p)", canvas, 6, 1320, 788, 100, 50, Color.WHITE, new Color(51, 51, 51, 255));
        panel.add(setting);
    }
}
