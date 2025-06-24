package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Gas;

import java.util.Random;

public class Fire extends Gas {
    long[] colors = {0xffbf00ff, 0xffa500ff, 0xff8000ff, 0xff4000ff, 0xff1500ff};
    int step = 0;
    public Fire(){
        e_id = 15;
        name = "Fire";
        type = 3;
        density = 1f;
        inertia_viscosity = 0;
        color = trans(colors[new Random().nextInt(5)]);
        thermal_conductivity = 1f;
        heat_capacity = 1000f;
        init_temperature = 1200;
        melting_point = 1000;
        melting_latent = 0;
        freezing_point = 100;
        freezing_latent = 0;
        melted = 15;
        freezed = 15;
    }

    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {
        world[x][y].setTemperature(init_temperature);
        step++;
        Random random= new Random();
        if(step>5) {
            if(random.nextInt(5)!=2) return;
            world[x][y].changeElement(new Smoke());
        }
    }
}
