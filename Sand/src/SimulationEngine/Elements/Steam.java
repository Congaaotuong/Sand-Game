package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Gas;

import java.util.Random;

public class Steam extends Gas {
    long[] colors = {0xe2e4e1ff, 0xd2dbdcff, 0xc9d0d1ff};
    public Steam(){
        e_id = 8;
        name = "Steam";
        type = 3;
        density = 10f;
        inertia_viscosity = 0;
        color = trans(colors[new Random().nextInt(3)]);
        thermal_conductivity = 0.02f;
        heat_capacity = 9.6f;
        init_temperature = 120;
        melting_point = 1000;
        melting_latent = 0;
        freezing_point = 100;
        freezing_latent = 11.3f;
        melted = 8;
        freezed = 5;
    }

    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {

    }
}
