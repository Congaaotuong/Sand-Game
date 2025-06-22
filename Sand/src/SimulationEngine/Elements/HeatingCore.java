package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Solid;

import java.util.Random;

public class HeatingCore extends Solid {
    long[] colors = {0xf44700ff, 0xf87e4cff, 0xffaa88ff, 0xffcbb7ff, 0xffe6ddff};
    public HeatingCore(){
        e_id = 13;
        name = "Heating Core";
        density = 270;
        static_object = true;
        color = trans(colors[new Random().nextInt(5)]);
        inertia_viscosity = 0;
        thermal_conductivity = 4;
        heat_capacity = 3.8f;
        init_temperature = 5000;
        melting_point = 500;
        melting_latent = 0;
        freezing_point = -250;
        freezing_latent = 0;
        melted = 13;
        freezed = 13;
    }
    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {
        world[x][y].setTemperature(init_temperature);
    }
}
