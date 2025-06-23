package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Liquid;

import java.util.Random;

public class MoltenGlass extends Liquid {
    long[] colors = {0xf2f217ff, 0xecd638ff, 0xff6600ff, 0xff2500ff};
    public MoltenGlass(){
        e_id = 10;
        name = "Molten Glass";
        density = 250;
        color = trans(colors[new Random().nextInt(4)]);
        inertia_viscosity = 10;
        thermal_conductivity = 5.5f;
        heat_capacity = 4.3f;
        init_temperature = 1500;
        melting_point = 2000;
        melting_latent = 0;
        freezing_point = 1000;
        freezing_latent = 4.87f;
        melted = 10;
        freezed = 11;
    }
    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {

    }
}
