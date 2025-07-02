package SimulationEngine;

import CustomDataType.*;
import SimulationEngine.Elements.*;

import java.awt.*;
import java.util.ArrayList;
import java.lang.*;

public class World {
    public World self = this;
    int Width;
    int Height;
    int Length;
    double Cycle;
    Pair<Integer, Integer> first_point;
    Pair<Integer, Integer> second_point;
    Cell[][] world;
    static int[][] nextEl;
    public World(int Width, int Height, int Length, double Cycle){
        this.Width = Width;
        this.Height = Height;
        this.Length = Length;
        this.Cycle = Cycle;
        this.first_point = new Pair<Integer, Integer>(-1, -1);
        this.second_point = new Pair<Integer, Integer>(-1, -1);
        this.world = new Cell[Width+2][Height+2];
        this.nextEl = new int[Width+2][Height+2];

        for(int x=0; x<Width+2; x++){
            for(int y=0; y<Height+2; y++){
                this.world[x][y] = new Cell(x, y);;
                this.nextEl[x][y] = 0;
            }
        }
        for(int i=0; i<Width+2; i++){
            this.world[i][0].changeElement(new AbsoluteInsulator());
            this.world[i][Height+1].changeElement(new AbsoluteInsulator());
        }
        for(int i=0; i<Height+2; i++){
            this.world[0][i].changeElement(new AbsoluteInsulator());
            this.world[Width+1][i].changeElement(new AbsoluteInsulator());
        }
    }
    public int Width(){return this.Width;}
    public int Height(){return this.Height;}
    public int Length(){return this.Length;}
    public void changeCycle(double Cycle){this.Cycle = Cycle;}
    public Cell cell(int x, int y){return this.world[x][y];}
    public Color[][] animateGrid(){
        Color[][] grid = new Color[this.Width][this.Height];
        for(int x=1; x<=this.Width; x++){
            for(int y=1; y<=this.Height; y++){
                grid[x-1][y-1] = world[x][y].element().color();
            }
        }
        return grid;
    }
    public Color[][] heatmap(){
        Color[][] grid = new Color[this.Width][this.Height];
        for(int x=1; x<=this.Width; x++){
            for(int y=1; y<=this.Height; y++){
                float g = world[x][y].temperature();
                grid[x-1][y-1] = getColor(g);
            }
        }
        return grid;
    }
    public void Draw(int x, int y, int length, int brush, int brush_size, int element, int heat){
        addPoint(x, y);
        int x1 =(int)(first_point.first() / length);
        if(first_point.first()<0) x1=-1;
        if(x1>=Width) x1=Width-1;
        int x2 =(int)(second_point.first() / length);
        if(second_point.first()<0) x2=-1;
        if(x2>=Width) x2=Width-1;
        int y1 =(int)(first_point.second() / length);
        if(first_point.second()<0) y1=-1;
        if(y1>=Height) y1=Height-1;
        int y2 =(int)(second_point.second() / length);
        if(second_point.second()<0) y2=-1;
        if(y2>=Height) y2=Height-1;
        DrawWorld(x1, y1, x2, y2, brush, brush_size, element, heat);
    }
    private void addPoint(int x, int y){
        this.first_point.change(second_point.first(), second_point.second());
        this.second_point.change(x, y);
    }
    private ArrayList<Pair<Integer, Integer>> GetDrawPoint(int x1, int y1, int x2, int y2){
        if(x1<0 || x2<0 || y1<0 || y2<0) return new ArrayList<Pair<Integer, Integer>>();
        ArrayList<Pair<Integer, Integer>> ans = new ArrayList<Pair<Integer, Integer>>();
        x1++; y1++; x2++; y2++;
        int ax = Math.abs(x1-x2);
        int ay = Math.abs(y1-y2);
        if(ax==0 && ay==0) {
            ans.add(new Pair<Integer, Integer>(x1, y1));
            return ans;
        }
        if(ax==0){
            int min = Math.min(y1, y2);
            int max = Math.max(y1, y2);
            for(int i=min; i<=max; i++){
                ans.add(new Pair<Integer, Integer>(x1, i));
            }
            return ans;
        }
        if(ay==0){
            int min = Math.min(x1, x2);
            int max = Math.max(x1, x2);
            for(int i=min; i<=max; i++){
                ans.add(new Pair<Integer, Integer>(i, y1));
            }
            return ans;
        }
        if(ay<ax){
            for(int i=0; i<=ax; i++){
                double fy= (i*ay)/ax;
                int _y = (int) fy;
                if((fy-_y)>=0.5) _y++;
                if(y1<y2) _y = y1+_y;
                else _y=y1-_y;
                if(x1<x2) ans.add(new Pair<Integer, Integer>(x1+i, _y));
                else ans.add(new Pair<Integer, Integer>(x1-i, _y));
            }
            return ans;
        }
        for(int i=0; i<=ay; i++){
            double fx= (i*ax)/ay;
            int _x = (int) fx;
            if((fx-_x)>0.5) _x++;
            if(x1<x2) _x = x1+_x;
            else _x=x1-_x;
            if(y1<y2) ans.add(new Pair<Integer, Integer>(_x, y1+i));
            else ans.add(new Pair<Integer, Integer>(_x, y1-i));
        }
        return ans;
    }

    public void DrawWorld(int x1, int y1, int x2, int y2, int brush, int brush_size, int element, int heat){
        ArrayList<Pair<Integer, Integer>> points = GetDrawPoint(x1, y1, x2, y2);
        for(Pair<Integer, Integer> i : points){
            if(brush==0) DrawSquare(i.first(), i.second(), brush_size, element, heat);
            else if(brush==1) DrawCircle(i.first(), i.second(), brush_size, element, heat);
        }
    }
    private void DrawSquare(int x, int y, int brush_size, int el, int heat){
        if(el==0) heat=0;
        int x_min = Math.max(1, x-(brush_size>>1));
        int x_max = Math.min(this.Width, x-(brush_size>>1)+brush_size);
        int y_min = Math.max(1, y-(brush_size>>1));
        int y_max = Math.min(this.Height, y-(brush_size>>1)+brush_size);
        for(int i=x_min; i<=x_max; i++){
            for(int j=y_min; j<=y_max; j++){
                if(heat!=0){
                    if(world[i][j].element().id() == 0) continue;
                    this.world[i][j].changeTemperature(heat);
                    continue;
                }
                Element element = getElement(el);
                if(world[i][j].element().id() == element.id() || (element.id()>0 && world[i][j].element().id()>0)) continue;
                this.world[i][j].changeElement(element);
            }
        }
    }
    private void DrawCircle(int x, int y, int brush_size, int el, int heat){
        if(el==0) heat=0;
        int x_min = Math.max(1, x-(brush_size>>1));
        int x_max = Math.min(this.Width, x-(brush_size>>1)+brush_size);
        int y_min = Math.max(1, y-(brush_size>>1));
        int y_max = Math.min(this.Height, y-(brush_size>>1)+brush_size);
        double r2=(brush_size*brush_size)/4;
        double offset = ((brush_size&1)==1)? 0:0.5;
        for(int i=x_min; i<=x_max; i++){
            for(int j=y_min; j<=y_max; j++){
                if(heat!=0){
                    if(world[i][j].element().id() == 0) continue;
                    double rx, ry;
                    rx = i-x + offset;
                    ry = j-y + offset;
                    if(rx*rx+ry*ry>r2) continue;
                    this.world[i][j].changeTemperature(heat);
                    continue;
                }
                Element element = getElement(el);
                if(world[i][j].element().id() == element.id() || (element.id()>0 && world[i][j].element().id()>0)) continue;
                double rx, ry;
                rx = i-x + offset;
                ry = j-y + offset;
                if(rx*rx+ry*ry>r2) continue;
                this.world[i][j].changeElement(element);
            }
        }
    }
    public void resetPoint(){
        first_point.change(-1, -1);
        second_point.change(-1, -1);
    }
    public void cleanScreen(){
        for(int i=1; i<=Width; i++){
            for(int j=1; j<=Height; j++){
                world[i][j].reset();;
            }
        }
    }
    public void UpdateWorld(){

        for(int x=1; x<=Width; x++){
            for(int y=1; y<=Height; y++){
                world[x][y].element().HeatTransfer(world, x, y);
            }
        }
        for(int x=1; x<=Width; x++){
            for(int y=1; y<=Height; y++){
                world[x][y].element().ApplyHeat(world, x, y);
                world[x][y].element().AttributeChange(world, x, y);
            }
        }
        for(int x=1; x<=Width; x++){
            for(int y=1; y<=Height; y++){
                if(nextEl[x][y]==0) continue;
                world[x][y].changeElement(getElement(nextEl[x][y]));
                nextEl[x][y] = 0;
            }
        }
        for(int y=Height; y>=1; y--){
            UpdateRow(y, 1);
            UpdateRow(Height-y+1, 0);
        }
    }

    public void UpdateRow(int y, int phase){
        ArrayList<Integer> vertical = new ArrayList<Integer>();
        ArrayList<Integer> horizontal = new ArrayList<Integer>();
        for(int x=1; x<=Width; x++){
            world[x][y].element().SetDirection(world, x, y, phase);
            if(world[x][y].direction()==2||world[x][y].direction()==4){
                horizontal.add(x);
                continue;
            }
            vertical.add(x);
        }
        for(int x:vertical){
            world[x][y].element().UpdateElement(world, x, y, phase);
        }
        for(int x:horizontal){
            world[x][y].element().UpdateElement(world, x, y, phase);
        }
    }

    public static void UpdateNextElement(int x, int y, int element){
        nextEl[x][y] = element;
    }

    private Color getColor(float g){
        if(g>=500) return Color.WHITE;
        if(g>=400) return new Color(255, 170, 170);
        if(g>=300) return new Color(139, 0, 0);
        if(g>=200) return Color.RED;
        if(g>=180) return new Color(255, 68, 0);
        if(g>=160) return new Color(255, 136, 0);
        if(g>=140) return Color.ORANGE;
        if(g>=120) return new Color(255, 221, 0);
        if(g>=100) return Color.YELLOW;
        if(g>=85) return new Color(187, 255, 0);
        if(g>=70) return Color.GREEN;
        if(g>=55) return new Color(0, 255, 128);
        if(g>=40) return Color.CYAN;
        if(g>=25) return new Color(0, 128, 255);
        if(g>=0) return new Color(0, 0, 255);
        return new Color(0, 0, 190);
    }

    private Element getElement(int x){
        switch (x){
            case 0: return new VoidE();
            case 1: return new Bedrock();
            case 2: return new Sand();
            case 3: return new WetSand();
            case 4: return new Clay();
            case 5: return new Water();
            case 6: return new Magma();
            case 7: return new Gasoline();
            case 8: return new Steam();
            case 9: return new Terracotta();
            case 10: return new MoltenGlass();
            case 11: return new Glass();
            case 12: return new Ice();
            case 13: return new HeatingCore();
            case 14: return new CoolingCore();
            case 15: return new Fire();
            case 16: return new Smoke();
            case 17: return new Copper();
            case 18: return new MoltenCopper();
            case 19: return new RustedCopper();
            case 20: return new Wood();
            case 21: return new BurningCoal();
            case 22: return new Ash();
            case 23: return new FlammableGas();
            case 24: return new AbsoluteInsulator();
            case 25: return new Steel();
            case 26: return new Charcoal();
            case 27: return new AshDust();
        }
        return new VoidE();
    }

    public ArrayList<SavingObject> getData(){
        ArrayList<SavingObject> data = new ArrayList<SavingObject>();
        for(int x=1; x<=Width; x++){
            for(int y=1;y<=Height; y++){
                if(world[x][y].element().id()==0) continue;
                data.add(world[x][y].data());
            }
        }
        return data;
    }
    public void loadData(ArrayList<SavingObject> data){
        cleanScreen();
        for(SavingObject d : data){
            int Int = d.getFirst();
            long Flt = d.getSecond();
            int id = (Int&511);
            int fall = (Int>>=9)&1;
            int dir = (Int>>=1)&7;
            int y_pos = (Int>>=3)&511;
            int x_pos = (Int>>9)&511;
            int res = (int)Flt;
            int temp = (int)(Flt>>>32);

            world[x_pos][y_pos].changeDirection(dir);
            world[x_pos][y_pos].changeFalling(fall==1);
            world[x_pos][y_pos].changeElementS(getElement(id));
            world[x_pos][y_pos].setTemperature(Float.intBitsToFloat(temp));
            world[x_pos][y_pos].setReserved(Float.intBitsToFloat(res));
        }
    }
}
