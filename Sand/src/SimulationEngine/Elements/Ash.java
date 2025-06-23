package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Solid;

import java.util.Random;

public class Ash extends Solid {
    long[] colors = {0xd0c6c6ff, 0xc3b9b9ff, 0xb4a8a8ff, 0xa99d9dff, 0x9f9393ff};
    public Ash(){
        e_id = 22;
        name = "Ash";
        density = 160;
        static_object = false;
        color = trans(colors[new Random().nextInt(5)]);
        inertia_viscosity = 0;
        thermal_conductivity = 0.2f;
        heat_capacity = 4.2f;
        init_temperature = 25;
        melting_point = 1500;
        freezing_point = -250;
        melting_latent = 0;
        freezing_latent = 0;
        melted = 22;
        freezed = 22;
    }
    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {

    }
}
