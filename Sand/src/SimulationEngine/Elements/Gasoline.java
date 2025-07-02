package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Liquid;
import SimulationEngine.World;

import java.util.Random;

public class Gasoline extends Liquid {
    long[] colors = {0xc7f6ecff, 0x55d9c0ff, 0xa6f5e6ff};
    int vapor=0;
    public Gasoline(){
        e_id = 7;
        name = "Gasoline";
        density = 75;
        color = trans(colors[new Random().nextInt(3)]);
        inertia_viscosity = 0;
        thermal_conductivity = 0.15f;
        heat_capacity = 10;
        init_temperature = 25;
        melting_point = 210;
        melting_latent = 0;
        freezing_point = -250;
        freezing_latent = 0;
        melted = 15;
        freezed = 7;
    }

    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {
        if(world[x][y-1].element().id()!=0 && world[x][y-1].element().id()!=16) return;
        int bound = 1;
        if(world[x][y].temperature()<15) bound=2;
        if(world[x][y].temperature()<0) bound=4;
        if(world[x][y].temperature()<-15) bound=8;
        if(world[x][y].temperature()<-30) bound=16;
        if(world[x][y].temperature()<-43) return;
        if(new Random().nextInt(bound)==0){
            World.UpdateNextElement(x, y-1, 23);
            vapor++;
            if(vapor>50){
                if(new Random().nextInt(8)==4) World.UpdateNextElement(x, y, 23);
                vapor=0;
            }
        }
    }
}
