package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Liquid;
import java.io.Serializable;
import java.util.Random;


public class Water extends Liquid  implements Serializable {
    long[] colors = {0x0070ffff, 0x1588ffff, 0x0097ffff};
    public Water(){
        e_id = 5;
        name = "Water";
        density = 100;
        color = trans(colors[new Random().nextInt(3)]);
        inertia_viscosity = 0;
        thermal_conductivity = 0.6f;
        heat_capacity = 20;
        init_temperature = 25;
        melting_point = 100;
        melting_latent = 11.3f;
        freezing_point = 0;
        freezing_latent = 6.7f;
        melted = 8;
        freezed = 12;
    }

    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {

    }
}
