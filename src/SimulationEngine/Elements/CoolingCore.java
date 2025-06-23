package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Solid;
import java.util.Random;

public class CoolingCore extends Solid {
    long[] colors = {0xf1efffff, 0xbfb6ffff, 0x9687ffff, 0x6954ffff, 0x391effff};
    public CoolingCore(){
        e_id = 14;
        name = "CoolingCore";
        density = 270;
        static_object = true;
        color = trans(colors[new Random().nextInt(5)]);
        inertia_viscosity = 0;
        thermal_conductivity = 4;
        heat_capacity = 3.8f;
        init_temperature = -250;
        melting_point = 500;
        melting_latent = 0;
        freezing_point = -250;
        freezing_latent = 0;
        melted = 14;
        freezed = 14;
    }
    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {
        world[x][y].setTemperature(init_temperature);
    }
}
