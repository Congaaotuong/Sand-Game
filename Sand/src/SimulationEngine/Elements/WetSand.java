package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Solid;

import java.util.Random;

public class WetSand extends Solid {
    long[] colors = {0x986e52ff, 0xb68967ff, 0x946338ff};
    public WetSand(){
        e_id = 3;
        name = "Wet Sand";
        density = 130;
        static_object = false;
        color = trans(colors[new Random().nextInt(3)]);
        inertia_viscosity = 10;
        thermal_conductivity = 2;
        heat_capacity = 12;
        init_temperature = 25;
        melting_point = 100;
        melting_latent = 5.65f;
        freezing_point = -250;
        freezing_latent = 0;
        melted = 2;
        freezed = 3;
    }

    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {

    }
}
