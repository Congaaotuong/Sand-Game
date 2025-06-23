package SimulationEngine;

import CustomDataType.*;
import SimulationEngine.Elements.*;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class World {
    public World self = this;
    int Width;
    int Height;
    int Length;
    double Cycle;
    Pair<Integer, Integer> first_point;
    Pair<Integer, Integer> second_point;
    Cell[][] world;
    float[][] thermal;
    public World(int Width, int Height, int Length, double Cycle){
        this.Width = Width;
        this.Height = Height;
        this.Length = Length;
        this.Cycle = Cycle;
        this.first_point = new Pair<Integer, Integer>(-1, -1);
        this.second_point = new Pair<Integer, Integer>(-1, -1);
        this.world = new Cell[Width+2][Height+2];;
        this.thermal = new float[Width+2][Height+2];

        for(int x=0; x<Width+2; x++){
            for(int y=0; y<Height+2; y++){
                this.world[x][y] = new Cell(x, y);;
                this.thermal[x][y] = 0;
            }
        }
        for(int i=0; i<Width+2; i++){
            this.world[i][0].changeElement(new Bedrock());
            this.world[i][Height+1].changeElement(new Bedrock());
            this.world[i][0].setTemperature(10000);
            this.world[i][Height+1].setTemperature(10000);
        }
        for(int i=0; i<Height+2; i++){
            this.world[0][i].changeElement(new Bedrock());
            this.world[Width+1][i].changeElement(new Bedrock());
            this.world[0][i].setTemperature(10000);
            this.world[Width+1][i].setTemperature(10000);
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
        int x_min = Math.max(1, x-(brush_size>>1));
        int x_max = Math.min(this.Width, x-(brush_size>>1)+brush_size);
        int y_min = Math.max(1, y-(brush_size>>1));
        int y_max = Math.min(this.Height, y-(brush_size>>1)+brush_size);
        for(int i=x_min; i<x_max; i++){
            for(int j=y_min; j<y_max; j++){
                if(heat!=0){
                    if(world[i][j].element().id() == 0) continue;
                    this.world[i][j].changeTemperature(heat);
                    continue;
                }
                Element element = getElement(el);
                if(world[i][j].element().id() == element.id() || (element.id()>0 && world[i][j].element().id()>0)) continue;
                this.world[i][j].changeElement(element);
                if(element.id()==0) this.world[i][j].setTemperature(0);
            }
        }
    }
    private void DrawCircle(int x, int y, int brush_size, int el, int heat){
        int x_min = Math.max(1, x-(brush_size>>1));
        int x_max = Math.min(this.Width, x-(brush_size>>1)+brush_size);
        int y_min = Math.max(1, y-(brush_size>>1));
        int y_max = Math.min(this.Height, y-(brush_size>>1)+brush_size);
        double r2=(brush_size*brush_size)/4;
        double offset = ((brush_size&1)==1)? 0:0.5;
        for(int i=x_min; i<x_max; i++){
            for(int j=y_min; j<y_max; j++){
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
                if(element.id()==0) this.world[i][j].setTemperature(0);
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
                world[x][y].element().HeatTransfer(world, thermal, x, y);
            }
        }
        for(int x=1; x<=Width; x++){
            for(int y=1; y<=Height; y++){
                world[x][y].element().ApplyHeat(world, x, y);
                world[x][y].element().AttributeChange(world, x, y);
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
            case 7: return new Diesel();
            case 8: return new Steam();
            case 9: return new Terracotta();
            case 10: return new MoltenGlass();
            case 11: return new Glass();
            case 12: return new Ice();
            case 13: return new HeatingCore();
            case 14: return new CoolingCore();
        }
        return new VoidE();
    }
    // Kết nối MySQL
    // Hàm kết nối tới MySQL
    private static Connection conn;
    public void connectToDB() {
        synchronized (World.class) {  // Use class-level synchronization for static conn
            try {
                if (conn != null && !conn.isClosed()) {
                    return;  // Reuse existing connection if valid
                }
                conn = Database.MySQLConnection.getConnection();
                conn.setAutoCommit(true);  // Ensure consistent initial state
                System.out.println("Connected to MySQL!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Lưu trạng thái world vào MySQL
    public void saveWorldToDB(String sand) {
        synchronized (World.class) {  // Use class-level synchronization
            if (conn == null) return;
            if (!sand.matches("[a-zA-Z0-9_]+")) {
                System.err.println("Invalid table name!");
                return;
            }

            String deleteSQL = "DELETE FROM " + sand;
            String insertSQL = "INSERT INTO " + sand + " (x, y, element, temperature, direction, falling, reserved) VALUES (?, ?, ?, ?, ?, ?, ?)";

            Connection localConn = null;
            try {
                // Create a new connection for this transaction
                localConn = Database.MySQLConnection.getConnection();
                localConn.setAutoCommit(false);

                try (PreparedStatement delStmt = localConn.prepareStatement(deleteSQL);
                     PreparedStatement stmt = localConn.prepareStatement(insertSQL)) {

                    delStmt.executeUpdate();

                    int count = 0;
                    for (int x = 1; x <= Width; x++) {
                        for (int y = 1; y <= Height; y++) {
                            if (world[x][y].element() instanceof SimulationEngine.Elements.VoidE) continue;
                            stmt.setInt(1, x - 1);
                            stmt.setInt(2, y - 1);
                            stmt.setString(3, world[x][y].element().toString());

                            float temp = world[x][y].temperature();
                            if (Float.isNaN(temp)) {
                                temp = 0.0f;
                            }
                            stmt.setFloat(4, temp);

                            stmt.setInt(5, world[x][y].direction());
                            stmt.setBoolean(6, world[x][y].isFalling());

                            float reserved = world[x][y].reserved();
                            if (Float.isNaN(reserved)) {
                                reserved = 0.0f;
                            }
                            stmt.setFloat(7, reserved);

                            stmt.addBatch();
                            count++;
                            if (count % 1000 == 0) {
                                stmt.executeBatch();
                            }
                        }
                    }
                    stmt.executeBatch();
                    localConn.commit();
                    System.out.println("World saved to DB!");
                }
            } catch (SQLException e) {
                try {
                    if (localConn != null && !localConn.getAutoCommit()) {
                        localConn.rollback();
                    }
                } catch (SQLException ex) {
                    System.err.println("Error during rollback: " + ex.getMessage());
                }
                e.printStackTrace();
            } finally {
                try {
                    if (localConn != null) {
                        localConn.setAutoCommit(true);
                        localConn.close();
                    }
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }




    // Load trạng thái world từ MySQL
    public void loadWorldFromDB(String sand) {
        if (conn == null) return;
        try {
            String sql = "SELECT x, y, element ,temperature , direction , falling ,reserved FROM " + sand;
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                String elementName = rs.getString("element");
                float temp = rs.getFloat("temperature");
                int dir = rs.getInt("direction");
                boolean fall = rs.getBoolean("falling");
                float res = rs.getFloat("reserved");

                Element e = Element.fromString(elementName);
                if (x >= 0 && x < Width && y >= 0 && y < Height && e != null) {
                    world[x +1 ][y + 1].changeCell(e);
                    world[x + 1 ][y +1].setTemperature(temp);
                    world[x + 1][y + 1].changeDirection(dir);
                    world[x + 1][y + 1].changeFalling(fall);
                    world[x + 1][y + 1].changeReserved(res);
                }
            }
            rs.close();
            stmt.close();
            System.out.println("World loaded from DB!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}