package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Solid;

import java.util.Random;

public class Terracotta extends Solid {
    long[] colors = {0xf47d5fff, 0xc34b36ff, 0xa73c2fff, 0x8d2828ff, 0x761f1fff, 0xa73c2fff, 0x8d2828ff, 0x761f1fff};
    public Terracotta(){
        e_id = 9;
        name = "Terracotta";
        density = 163;
        static_object = true;
        color = trans(colors[new Random().nextInt(8)]);
        inertia_viscosity = 0;
        thermal_conductivity = 4;
        heat_capacity = 4.8f;
        init_temperature = 25;
        melting_point = 1000;
        melting_latent = 0;
        freezing_point = -250;
        freezing_latent = 0;
        melted = 9;
        freezed = 9;
    }
    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {

    }
}
