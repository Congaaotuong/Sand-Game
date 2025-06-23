package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Solid;

import java.util.Random;

public class Ice extends Solid {
    long[] colors = {0xe3fdffff, 0xd1f7ffff, 0xc0f7ffff, 0x94f7ffff, 0x49e8ffff};
    public Ice(){
        e_id = 12;
        name = "Ice";
        density = 270;
        static_object = true;
        color = trans(colors[new Random().nextInt(5)]);
        inertia_viscosity = 0;
        thermal_conductivity = 2.2f;
        heat_capacity = 10f;
        init_temperature = -25;
        melting_point = 0;
        melting_latent = 6.7f;
        freezing_point = -250;
        freezing_latent = 0;
        melted = 5;
        freezed = 12;
    }
    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {

    }
}
