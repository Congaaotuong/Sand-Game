package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Liquid;
import SimulationEngine.World;

import java.util.Random;

public class Diesel extends Liquid {
    long[] colors = {0xf76381ff, 0xfa708cff, 0xf07890ff};
    public Diesel(){
        e_id = 7;
        name = "Diesel";
        density = 83;
        color = trans(colors[new Random().nextInt(3)]);
        inertia_viscosity = 2;
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
        if(world[x-1][y].element().id()==15 || world[x+1][y].element().id()==15 || world[x][y-1].element().id()==15 || world[x][y+1].element().id()==15){
            World.UpdateNextElement(x, y, 15);
        }
    }
}
