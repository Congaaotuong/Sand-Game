package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Gas;

import java.util.Random;

public class AshDust extends Gas {
    long[] colors = {0xd0c6c6ff, 0xc3b9b9ff, 0xb4a8a8ff, 0xa99d9dff, 0x9f9393ff};
    public AshDust(){
        e_id = 27;
        name = "Ash Dust";
        density = 10;
        color = trans(colors[new Random().nextInt(5)]);
        inertia_viscosity = 0;
        thermal_conductivity = 4f;
        heat_capacity = 42f;
        init_temperature = 500;
        melting_point = 1500;
        freezing_point = 500;
        melting_latent = 0;
        freezing_latent = 0;
        melted = 27;
        freezed = 22;
    }
    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {

    }
}
