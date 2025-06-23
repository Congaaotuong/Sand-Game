package GUI;

import CustomDataType.*;
import Sand.Main;
import SimulationEngine.Element;
import SimulationEngine.Elements.*;
import SimulationEngine.World;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class WorldCanvas extends Canvas {
    WorldCanvas self = this;
    public World world;
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
    int ChosenE;
    int heat;
    boolean pause;

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
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(Width*Length, Height*Length));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getButton() == MouseEvent.BUTTON1) ChosenE = LeftE;
                if(e.getButton() == MouseEvent.BUTTON3) ChosenE = RightE;
                world.Draw(e.getX(), e.getY(), Length, brush_type, brush_size, ChosenE, heat);
                world.Draw(e.getX(), e.getY(), Length, brush_type, brush_size, ChosenE, heat);
                repaint();
                world.resetPoint();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                world.resetPoint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                isMouseInScreen=false;
                world.resetPoint();
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
                int mod = e.getModifiersEx();
                if((mod & MouseEvent.BUTTON1_DOWN_MASK) != 0) ChosenE = LeftE;
                if((mod & MouseEvent.BUTTON3_DOWN_MASK) != 0) ChosenE = RightE;
                world.Draw(e.getX(), e.getY(), Length, brush_type, brush_size, ChosenE, heat);
                repaint();
            }
            @Override
            public void mouseMoved(MouseEvent e){
                isMouseInScreen=true;
            }
        });
        this.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                brush_size -= e.getWheelRotation();
                if(brush_size<1) brush_size=1;
                if(brush_size>25) brush_size=25;
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
                int code = e.getKeyCode();
                if (code == 77) {
                    Main.RequestScene("main menu");
                }
                if (code == 80) { // P
                    Main.RequestScene("settings");
                }
                if(code == 67){
                    Clean();
                }
                if (code == 32) {
                    pause = !pause;
                }
                if(code == 72){
                    heatmap = !heatmap;
                }
                if(code>=96 && code<=97){
                    brush_type = code-96;
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
        LeftE = x;
        this.heat = heat;
    }
    public void Clean(){
        world.cleanScreen();
        repaint();
    }
    public void BrushType(int x){
        brush_type=x;
    }
    public void Main(){
        Main.RequestScene("main menu");
    }
    public void Setting(){
        Main.RequestScene("Setting");
    }

    public void Pause(){
        pause = !pause;
    }

    public void HeatMap(){
        heatmap = !heatmap;
        repaint();
    }
}
