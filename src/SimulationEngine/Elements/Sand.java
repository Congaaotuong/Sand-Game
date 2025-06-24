package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Solid;
import java.io.Serializable;
import java.util.Random;


public class Sand extends Solid  implements Serializable {
    long[] colors = {0xf6d7b0ff, 0xeccca2ff, 0xe1bf92ff};
    public Sand(){
        e_id = 2;
        name = "Sand";
        density = 160;
        static_object = false;
        color = trans(colors[new Random().nextInt(3)]);
        inertia_viscosity = 0;
        thermal_conductivity = 0.2f;
        heat_capacity = 4.2f;
        init_temperature = 25;
        melting_point = 1500;
        freezing_point = -250;
        melting_latent = 23.8f;
        freezing_latent = 0;
        melted = 10;
        freezed = 2;
    }

    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {
        if(world[x-1][y].element().id()==5||world[x+1][y].element().id()==5||
                world[x][y-1].element().id()==5||world[x][y-1].element().id()==5){
            world[x][y].changeElementS(new WetSand());
            return;
        }
    }
}
