package GUI;

import CustomDataType.*;
import Sand.Main;
import SimulationEngine.World;
import com.mysql.cj.xdevapi.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class WorldCanvas extends Canvas {
    WorldCanvas self = this;
    World world;
    int Width, Height, Length;
    Pair<Integer, Integer> MousePosition;
    boolean isMouseInScreen;
    Color[][] currentGrid;
    BufferedImage buffer;
    int brush_size;
    int brush_type;
    boolean heatmap;
    int LeftE = 1;
    int RightE = 0;
    int brush_max_size = 50;
    int ChosenE;
    int heat;
    boolean pause;
    boolean AbsPause;
    Pair<Integer, Integer> mousePos = new Pair<Integer, Integer>(-1, -1);
    JButton pauseButton = new JButton();
    JButton heatButton = new JButton();

    public WorldCanvas(int Width, int Height, int Length){
        this.Width = Width;
        this.Height = Height;
        this.Length = Length;
        world = new World(Width, Height, Length, 0.1);
        MousePosition = new Pair<Integer, Integer>(-1, -1);
        this.isMouseInScreen = false;
        this.currentGrid = world.animateGrid();
        this.buffer = new BufferedImage(Width*Length, Height*Length, BufferedImage.TYPE_INT_ARGB);
        this.brush_size=5;
        this.brush_type=1;
        this.heatmap = false;
        this.ChosenE = LeftE;
        this.pause = false;
        this.AbsPause = false;
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(Width*Length, Height*Length));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) ChosenE = LeftE;
                if(e.getButton() == MouseEvent.BUTTON3) ChosenE = RightE;
                world.Draw(e.getX(), e.getY(), Length, brush_type, brush_size, ChosenE, heat);
                world.Draw(e.getX(), e.getY(), Length, brush_type, brush_size, ChosenE, heat);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                world.resetPoint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                isMouseInScreen=false;
                world.resetPoint();
                mousePos.change(-1, -1);
                repaint();
            }
            @Override
            public void mouseEntered(MouseEvent e){

                isMouseInScreen=true;
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(!isMouseInScreen) return;
                mousePos.change(e.getX()/Length, e.getY()/Length);
                int mod = e.getModifiersEx();
                if((mod & MouseEvent.BUTTON1_DOWN_MASK) != 0) ChosenE = LeftE;
                if((mod & MouseEvent.BUTTON3_DOWN_MASK) != 0) ChosenE = RightE;
                world.Draw(e.getX(), e.getY(), Length, brush_type, brush_size, ChosenE, heat);
                repaint();
            }
            @Override
            public void mouseMoved(MouseEvent e){
                mousePos.change(e.getX()/Length, e.getY()/Length);
                repaint();
                isMouseInScreen=true;
            }
        });
        this.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                brush_size -= e.getWheelRotation();
                if(brush_size<1) brush_size=1;
                if(brush_size>brush_max_size) brush_size=brush_max_size;
            }
        });
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                createBuffer();
                repaint();
            }
        });
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (AbsPause) return;
                int code = e.getKeyCode();
                if (code == 27) {
                    Main.RequestScene("main menu");
                }
                if(code == 67){
                    Clean();
                }
                if (code == 32) {
                    Pause();
                }
                if(code == 72){
                    HeatMap();
                }
                if(code == 80){
                    Setting();
                }
                if(code == 49){
                    brush_type = 1;
                }
                if(code == 50){
                    brush_type = 0;
                }
            }
        });
    }

    @Override
    public void paint(Graphics g){
        if(buffer != null) g.drawImage(buffer, 0, 0, this);
    }
    @Override
    public void update(Graphics g){
        if(heatmap) BufferDraw(world.heatmap());
        else BufferDraw(world.animateGrid());
        paint(g);
    }

    private void BufferDraw(Color grid[][]){
        AddCursor(grid);
        Graphics g = buffer.createGraphics();
        for(int x=0; x<Width; x++){
            for(int y=0; y<Height; y++){
                if(grid[x][y]==currentGrid[x][y]) continue;
                g.setColor(grid[x][y]);
                g.fillRect(x*Length, y*Length, Length, Length);
            }
        }
        currentGrid = grid;
    }
    private void createBuffer() {
        int w = getWidth(), h = getHeight();
        if (w > 0 && h > 0) {
            BufferedImage newBuffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            if (buffer != null) {
                newBuffer.getGraphics().drawImage(buffer, 0, 0, null);
            }
            buffer = newBuffer;
        }
    }
    public void SetMiddle(int maxWidth, int maxHeight){
        int w=Width*Length;
        int h=Height*Length;
        int offw = (maxWidth-w)>>1;
        int offh = (maxHeight-h)>>1;
        this.setBounds(offw, offh, w, h);
    }
    public void UpdateWorld(){
        if(this.pause) return;
        world.UpdateWorld();
        repaint();
    }
    public void BrushElement(int x, int heat){
        if(AbsPause) return;
        LeftE = x;
        this.heat = heat;
    }
    public void Clean(){
        if(AbsPause) return;
        world.cleanScreen();
        repaint();
    }
    public void BrushType(int x){
        if(AbsPause) return;
        brush_type=x;
    }
    public void Main(){
        if(AbsPause) return;
        Main.RequestScene("main menu");
    }
    public void Setting(){
        if(AbsPause) return;
        Main.RequestScene("Setting2");
    }
    public void Pause(){
        if(AbsPause) return;
        pause = !pause;
        if(pause) pauseButton.setLabel("Resume (space)");
        else pauseButton.setLabel("Pause (space)");
    }
    public void AbsPause(boolean bool){
        AbsPause = bool;
        pause = bool;
        if(pause) pauseButton.setLabel("Resume (space)");
        else pauseButton.setLabel("Pause (space)");
    }

    public void HeatMap(){
        if(AbsPause) return;
        heatmap = !heatmap;
        if(heatmap) heatButton.setLabel("Normal Map (h)");
        else heatButton.setLabel("Heat Map (h)");
        repaint();
    }
    public World getWorld() {
        return this.world;
    }

    void AddCursor(Color[][] grid){
        if(mousePos.first()<0 || mousePos.second()<0 || !isMouseInScreen) return;
        if(brush_type==0){
            int x_min = Math.max(0, mousePos.first()-(brush_size>>1));
            int x_max = Math.min(this.Width-1, mousePos.first()-(brush_size>>1)+brush_size);
            int y_min = Math.max(0, mousePos.second()-(brush_size>>1));
            int y_max = Math.min(this.Height-1, mousePos.second()-(brush_size>>1)+brush_size);
            for(int i=x_min; i<=x_max; i++){
                for(int j=y_min; j<=y_max; j++){
                    Color a=grid[i][j];
                    grid[i][j] = new Color(Math.abs(145-a.getRed()), Math.abs(145-a.getBlue()), Math.abs(145-a.getGreen()), a.getAlpha());
                }
            }
        }
        else{
            int x_min = Math.max(0, mousePos.first()-(brush_size>>1));
            int x_max = Math.min(this.Width-1, mousePos.first()-(brush_size>>1)+brush_size);
            int y_min = Math.max(0, mousePos.second()-(brush_size>>1));
            int y_max = Math.min(this.Height-1, mousePos.second()-(brush_size>>1)+brush_size);
            double r2=(brush_size*brush_size)/4;
            double offset = ((brush_size&1)==1)? 0:0.5;
            for(int i=x_min; i<=x_max; i++){
                for(int j=y_min; j<=y_max; j++){
                    double rx, ry, rx2, ry2;
                    rx = i-mousePos.first() + offset;
                    ry = j-mousePos.second() + offset;
                    if(rx*rx+ry*ry>r2) continue;
                    Color a=grid[i][j];
                    grid[i][j] = new Color(Math.abs(145-a.getRed()), Math.abs(145-a.getBlue()), Math.abs(145-a.getGreen()), a.getAlpha());
                }
            }
        }
    }
    public void SetPauseButton(JButton button){this.pauseButton = button;}
    public void SetHeatButton(JButton button){this.heatButton = button;}
    public ArrayList<SavingObject> getData(){
        return world.getData();
    }
    public void loadData(ArrayList<SavingObject> data){
        world.loadData(data);
    }
    public boolean AbsPause(){return this.AbsPause;}
}
