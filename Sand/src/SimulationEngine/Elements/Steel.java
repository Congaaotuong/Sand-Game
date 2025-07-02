package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Solid;

import java.util.Random;

public class Steel extends Solid {
    long[] colors = {0xe0e5e5ff, 0xced3d4ff, 0xc0c6c7ff, 0xa8b0b2ff, 0x99a3a3ff};
    public Steel(){
        e_id = 25;
        name = "Steel";
        density = 785;
        static_object = true;
        color = trans(colors[new Random().nextInt(5)]);
        inertia_viscosity = 0;
        thermal_conductivity = 4;
        heat_capacity = 2.34f;
        init_temperature = 25;
        melting_point = 1530;
        melting_latent = 16.16f;
        freezing_point = -250;
        freezing_latent = 0;
        melted = 25;
        freezed = 25;
    }

    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {

    }
}
