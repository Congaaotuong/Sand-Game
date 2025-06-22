package GUI;

import Sand.Main;

import javax.swing.plaf.basic.BasicTableUI;
import java.awt.*;
import java.awt.event.*;

public class Loadout {
    public Holder loadout_1(String name, int width, int height, int length){
        Holder panel = new Holder(name);
        panel.setSize(new Dimension(width*length, height*length));
        WorldCanvas canvas = new WorldCanvas(width, height, length);
        panel.setBackground(new Color(122, 122, 122, 255));
        panel.setLayout(null);
        setButton(panel, canvas);

        panel.add(canvas);
        panel.changeTheFunction(()->canvas.UpdateWorld());
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                canvas.SetMiddle(panel.getWidth(), panel.getHeight());
            }
        });
        return panel;
    }
    public Holder loadout_2(String name){
        Holder panel = new Holder(name);
        panel.setBackground(Color.BLUE);
        Button button = new Button("change scene");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.RequestScene("world");
            }
        });
        panel.add(button);
        return panel;
    }

    private Button getBrushButton(String name, WorldCanvas canvas, int brush, int heat,int x, int y, int width, int height){
        Button custom = new Button(name);
        custom.setBounds(x, y, width, height);
        custom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.BrushElement(brush, heat);
            }
        });
        return custom;
    }
    private Button getButton(String name, WorldCanvas canvas, int type, int x, int y, int width, int height){
        Button custom = new Button(name);
        custom.setBounds(x, y, width, height);
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
        return custom;
    }

    private void setButton(Holder panel, WorldCanvas canvas){
        Button b1 = getBrushButton("Bedrock", canvas, 1, 0, 10, 8, 150, 40);
        panel.add(b1);
        Button b2 = getBrushButton("Sand", canvas, 2, 0, 10, 58, 150, 40);
        panel.add(b2);
        Button b3 = getBrushButton("Wetsand", canvas, 3, 0, 10, 108, 150, 40);
        panel.add(b3);
        Button b4 = getBrushButton("Clay", canvas, 4, 0, 10, 158, 150, 40);
        panel.add(b4);
        Button b5 = getBrushButton("Water", canvas, 5, 0, 10, 208, 150, 40);
        panel.add(b5);
        Button b6 = getBrushButton("Magma", canvas, 6, 0, 10, 258, 150, 40);
        panel.add(b6);
        Button b7 = getBrushButton("Diesel", canvas, 7, 0, 10, 308, 150, 40);
        panel.add(b7);
        Button b8 = getBrushButton("Steam", canvas, 8,0, 10, 358, 150, 40);
        panel.add(b8);
        Button b9 = getBrushButton("Terracotta", canvas, 9, 0, 10, 408, 150, 40);
        panel.add(b9);
        Button b11 = getBrushButton("Increase Heat", canvas, 0, 1, 170, 8, 150, 40);
        panel.add(b11);
        Button b12 = getBrushButton("Decrease Heat", canvas, 0, -1, 170, 58, 150, 40);
        panel.add(b12);
        Button b13 = getBrushButton("Molten Glass", canvas, 10, 0, 10, 458, 150, 40);
        panel.add(b13);
        Button b14 = getBrushButton("Glass", canvas, 11, 0, 10, 508, 150, 40);
        panel.add(b14);
        Button b15 = getBrushButton("Ice", canvas, 12, 0, 10, 558, 150, 40);
        panel.add(b15);
        Button b16 = getBrushButton("Heating Core", canvas, 13, 0, 170, 108, 150, 40);
        panel.add(b16);
        Button b17 = getBrushButton("Cooling Core", canvas, 14, 0, 170, 158, 150, 40);
        panel.add(b17);
        Button clear = getButton("Clear (c)", canvas, 0, 1380, 8, 150, 40);
        panel.add(clear);
        Button main = getButton("Main menu (m)", canvas, 1, 1380, 788, 150, 40);
        panel.add(main);
        Button pause = getButton("Pause (space)", canvas, 2, 1220, 8, 150, 40);
        panel.add(pause);
        Button circle = getButton("Circle Brush", canvas, 4, 1220, 58, 150, 40);
        panel.add(circle);
        Button square = getButton("Square Brush", canvas, 3, 1380, 58, 150, 40);
        panel.add(square);
        Button HeatMap = getButton("Heat Map (h)", canvas, 5, 1380, 108, 150, 40);
        panel.add(HeatMap);
    }
}
