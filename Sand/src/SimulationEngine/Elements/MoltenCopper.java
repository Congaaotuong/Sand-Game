package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Liquid;

import java.util.Random;

public class MoltenCopper extends Liquid {
    long[] colors = {0xffc100ff, 0xffce00ff, 0xffdb00ff, 0xff8100ff};
    public MoltenCopper(){
        e_id = 18;
        name = "Molten Copper";
        density = 896;
        color = trans(colors[new Random().nextInt(4)]);
        inertia_viscosity = 8;
        thermal_conductivity = 4;
        heat_capacity = 1.84f;
        init_temperature = 1600;
        melting_point = 10000;
        melting_latent = 0;
        freezing_point = 1085;
        freezing_latent = 12.43f;
        melted = 18;
        freezed = 17;
    }

    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {

    }
}
