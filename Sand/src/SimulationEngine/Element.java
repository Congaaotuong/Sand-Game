package SimulationEngine;

import SimulationEngine.Elements.*;

import java.awt.*;
import java.util.Random;
public abstract class Element {
    protected int e_id;
    protected String name;
    protected int type;
    protected float density;
    protected int inertia_viscosity;
    protected float thermal_conductivity;
    protected float heat_capacity;
    protected float init_temperature;
    protected float melting_point;
    protected float melting_latent;
    protected int melted;
    protected float freezing_point;
    protected float freezing_latent;
    protected int freezed;

    protected boolean static_object;
    protected Color color;
    public Color color(){return this.color;}
    protected Color trans(long x){
        int a=(int)(x&255);
        int b=(int)((x>>=8)&255);
        int g=(int)((x>>=8)&255);
        int r=(int)((x>>=8)&255);

        return new Color(r, g, b, a);
    }
    public boolean isStatic(){return this.static_object;}
    public int id(){return this.e_id;}
    public int type(){return this.type;}
    public float density(){return this.density;}
    public int inertia_viscosity(){return this.inertia_viscosity;}
    public float thermal_conductivity(){return this.thermal_conductivity;}
    public float heat_capacity(){return this.heat_capacity;}
    public float init_temperature(){return this.init_temperature;}
    public float melting_point(){return this.melting_point;}
    public float freezing_point(){return this.freezing_point;}
    public float melting_latent(){return this.melting_latent;}
    public float freezing_latent(){return this.freezing_latent;}
    public int melted(){return this.melted;}
    public int freezed(){return this.freezed;}
    public abstract void SetDirection(Cell[][] world, int x, int y, int phase);
    public abstract void UpdateElement(Cell[][] world, int x, int y, int phase);
    public abstract void AttributeChange(Cell[][] world, int x, int y);
    public void HeatTransfer(Cell[][] world, int x, int y){
        if(world[x][y].element().thermal_conductivity()<=0) return;
        float mul=10f;

        float self = world[x][y].temperature();
        float l = world[x-1][y].temperature();
        float r = world[x+1][y].temperature();
        float u = world[x][y-1].temperature();
        float d = world[x][y+1].temperature();

        float hs = world[x][y].element().thermal_conductivity()*mul;
        float hl = world[x-1][y].element().thermal_conductivity()*mul;
        float hr = world[x+1][y].element().thermal_conductivity()*mul;
        float hu = world[x][y-1].element().thermal_conductivity()*mul;
        float hd = world[x][y+1].element().thermal_conductivity()*mul;

        float cs = world[x][y].element().heat_capacity();
        float cl = world[x-1][y].element().heat_capacity();
        float cr = world[x+1][y].element().heat_capacity();
        float cu = world[x][y-1].element().heat_capacity();
        float cd = world[x][y+1].element().heat_capacity();

        float ds = world[x][y].element().density();
        float dl = world[x-1][y].element().density();
        float dr = world[x+1][y].element().density();
        float du = world[x][y-1].element().density();
        float dd = world[x][y+1].element().density();

        if(l>=self) cl=0;
        if(r>=self) cr=0;
        if(u>=self) cu=0;
        if(d>=self) cd=0;

        float t = (ds*self*cs+dl*l*cl+dr*r*cr+du*u*cu+dd*d*cd) / (ds*cs+dl*cl+dr*cr+du*cu+dd*cd);

        world[x][y].changeReserved(hs*(t-self) / (ds*cs));
        if(cl>0) world[x-1][y].changeReserved(hl*(t-l) / (dl*cl));
        if(cr>0) world[x+1][y].changeReserved(hr*(t-r) / (dr*cr));
        if(cu>0) world[x][y-1].changeReserved(hu*(t-u) / (du*cu));
        if(cd>0) world[x][y+1].changeReserved(hd*(t-d) / (dd*cd));
    }

    public void ApplyHeat(Cell[][] world, int x, int y){
        world[x][y].changeTemperature(world[x][y].reserved());
        world[x][y].setReserved(0);
        float temp = world[x][y].temperature();
        float melt = world[x][y].element().melting_point();
        float freeze = world[x][y].element().freezing_point();
        if(temp>melt){
            if(world[x][y].element().melted()==world[x][y].element().id()) return;
            float left = temp-melt;
            if(left<world[x][y].element().melting_latent()){
                world[x][y].setTemperature(melt);
                world[x][y].setReserved(left);
                return;
            }
            world[x][y].changeElementS(getElement(world[x][y].element().melted()));
            world[x][y].changeTemperature(left-world[x][y].element().melting_latent());
            return;
        }
        if(temp<freeze){
            if(world[x][y].element().freezed()==world[x][y].element().id()) return;
            float left = freeze-temp;
            if(left<world[x][y].element().freezing_latent()){
                world[x][y].setTemperature(freeze);
                world[x][y].setReserved(-left);
                return;
            }
            world[x][y].changeElementS(getElement(world[x][y].element().freezed()));
            world[x][y].changeTemperature(world[x][y].element().freezing_latent()-left);
            return;
        }
    }
    protected boolean CalPercentage(int iv){
        int x = (iv & 0x0000ffff);
        int offset = iv>>16;
        if(x==0) return true;
        int a = new Random().nextInt(x+1);
        int b = new Random().nextInt(x+1);
        if(Math.abs(a-b)<=offset) return true;
        return false;
    }
    protected void switchCell(Cell[][] world, int x1, int y1, int x2, int y2) {
        Element el1 = world[x1][y1].element();
        int dir1 = world[x1][y1].direction();
        boolean fall1 = world[x1][y1].isFalling();
        float temp1 = world[x1][y1].temperature();
        float ener1 = world[x1][y1].reserved();

        world[x1][y1].changeElementS(world[x2][y2].element());
        world[x1][y1].changeDirection(world[x2][y2].direction());
        world[x1][y1].changeFalling(world[x2][y2].isFalling());
        world[x1][y1].setTemperature(world[x2][y2].temperature());
        world[x1][y1].setReserved(world[x2][y2].reserved());

        world[x2][y2].changeElementS(el1);
        world[x2][y2].changeDirection(dir1);
        world[x2][y2].changeFalling(fall1);
        world[x2][y2].setTemperature(temp1);
        world[x2][y2].setReserved(ener1);
    }
    protected Element getElement(int x){
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
            case 15: return new Fire();
            case 16: return new Smoke();
            case 17: return new Copper();
            case 18: return new MoltenCopper();
            case 19: return new RustedCopper();
        }
        return new VoidE();
    }
}
