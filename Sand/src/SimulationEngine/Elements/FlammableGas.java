package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Gas;

import java.util.Random;

public class FlammableGas extends Gas {
    long[] colors = {0x242324ff, 0x302d30ff, 0x292429ff};
    public FlammableGas(){
        e_id = 23;
        name = "Flammable Gas";
        type = 3;
        density = 10f;
        inertia_viscosity = 0;
        color = trans(colors[new Random().nextInt(3)]);
        thermal_conductivity = 1f;
        heat_capacity = 9.6f;
        init_temperature = 25;
        melting_point = 255;
        melting_latent = 0;
        freezing_point = 0;
        freezing_latent = 0;
        melted = 15;
        freezed = 23;
    }

    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {

    }
}
