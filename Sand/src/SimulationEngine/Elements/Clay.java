package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Solid;

import java.util.Random;

public class Clay extends Solid {
    long[] colors = {0xc29689ff, 0x9d604dff, 0x88503eff};
    public Clay(){
        e_id = 4;
        name = "Clay";
        density = 180;
        static_object = false;
        color = trans(colors[new Random().nextInt(3)]);
        inertia_viscosity = 64;
        thermal_conductivity = 0.4f;
        heat_capacity = 4.8f;
        init_temperature = 25;
        melting_point = 350;
        melting_latent = 0;
        freezing_point = -250;
        freezing_latent = 0;
        melted = 9;
        freezed = 4;
    }

    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {

    }
}
