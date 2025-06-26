package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Gas;

import java.util.Random;

public class Smoke extends Gas {
    long[] colors = {0x7e7e7eff, 0x474747ff, 0x1a1a1aff, 0x474747ff, 0x1a1a1aff};
    int step = 0;
    public Smoke(){
        e_id = 16;
        name = "Smoke";
        type = 3;
        density = 10f;
        inertia_viscosity = 0;
        color = trans(colors[new Random().nextInt(5)]);
        thermal_conductivity = 1f;
        heat_capacity = 0.1f;
        init_temperature = 100;
        melting_point = 1000;
        melting_latent = 0;
        freezing_point = 100;
        freezing_latent = 0;
        melted = 16;
        freezed = 16;
    }

    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {
        world[x][y].setTemperature(init_temperature);
        step++;
        Random random= new Random();
        if(world[x-1][y].element().type()<=0||world[x+1][y].element().type()<=0||world[x][y-1].element().type()<=0||world[x][y+1].element().type()<=0) step=101;
        if(step>100) {
            if(random.nextInt(10)!=2) return;
            world[x][y].changeElement(new VoidE());
        }
    }
}
