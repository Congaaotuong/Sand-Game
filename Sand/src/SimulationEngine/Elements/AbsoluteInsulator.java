package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Solid;

import java.util.Random;

public class AbsoluteInsulator extends Solid {
    long[] colors = {0xffe43fff, 0xffeda3ff, 0xb6e6fdff, 0xe9f4ffff, 0xf4f9ffff};
    public AbsoluteInsulator(){
        e_id = 24;
        name = "Absolute Insulator";
        density = 0;
        static_object = true;
        color = trans(colors[new Random().nextInt(5)]);
        inertia_viscosity = 0;
        thermal_conductivity = 0;
        heat_capacity = 0;
        init_temperature = 25;
        melting_point = 0;
        melting_latent = 0;
        freezing_point = 0;
        freezing_latent = 0;
        melted = 24;
        freezed = 24;
    }

    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {

    }
}
