package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Liquid;

import java.util.Random;

public class Magma extends Liquid {
    long[] colors = {0xffdb00ff, 0xffa904ff, 0xa12424ff, 0xee7b06ff, 0xffdb00ff, 0x400b0bff, 0xffa904ff, 0xee7b06ff};
    public Magma(){
        e_id = 6;
        name = "Magma";
        density = 270;
        color = trans(colors[new Random().nextInt(8)]);
        inertia_viscosity = 10;
        thermal_conductivity = 1.5f;
        heat_capacity = 7;
        init_temperature = 600;
        melting_point = 1000;
        melting_latent = 0;
        freezing_point = 500;
        freezing_latent = 4.87f;
        melted = 6;
        freezed = 1;
    }

    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {

    }
}
