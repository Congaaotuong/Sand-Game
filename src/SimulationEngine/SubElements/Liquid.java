package SimulationEngine.SubElements;

import SimulationEngine.Cell;
import SimulationEngine.Element;

import java.util.ArrayList;
import java.util.Random;

public abstract class Liquid extends Element {
    public Liquid(){
        type = 1;
        static_object=false;
    }

    @Override
    public void SetDirection(Cell[][] world, int x, int y, int phase) {
        if(phase==0) return;
        int direction = getDirection(world[x][y].element(), world[x-1][y].element(), world[x+1][y].element(),
                world[x][y+1].element(), world[x-1][y+1].element(), world[x+1][y+1].element());
        if(!CalPercentage(inertia_viscosity) && direction!=1) direction=0;
        world[x][y].changeDirection(direction);
    }

    @Override
    public void UpdateElement(Cell[][] world, int x, int y, int phase) {
        if(phase==0) return;
        int _x=0; int _y=0;
        int direction = world[x][y].direction();
        if(direction==0) return;
        else if(direction==1){
            _y=1;
        }
        else if(direction==3||direction==5){
            _x=direction-4;
            _y=1;
        }
        else{
            _x=direction-3;
            if(world[x+1][y].element().type()<=0)_x=0;
        }
        switchCell(world, x, y, x+_x, y+_y);
    }

    private int getDirection(Element self, Element l, Element r, Element d, Element ld, Element rd){
        float[] dir = {self.density(), d.density(), l.density(), ld.density(), r.density(), rd.density()};
        if(d.type()>1) return 1;
        float max =100000;
        if(d.type()<=0) dir[1]=max;
        if(l.type()<=0) {
            dir[2]=max;
            if(dir[1]==max) dir[3]=max;
        }
        if(ld.type()<=0) dir[3]=max;
        if(r.type()<=0) {
            dir[4]=max;
            if(dir[1]==max) dir[5]=max;
        }
        if(rd.type()<=0) dir[5]=max;
        float min=dir[0];
        for(int i=1; i<6; i++){
            if(dir[i]==max) continue;
            if(dir[i]<min) min=dir[i];
        }
        ArrayList<Integer> direction = new ArrayList<Integer>();
        for(int i=0; i<6; i++){
            if(dir[i]==min) direction.add(i);
        }
        return direction.get(new Random().nextInt(direction.size()));
    }
}
