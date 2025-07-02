package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Solid;
import SimulationEngine.World;

import java.util.Random;

public class Wood extends Solid {
    long[] colors = {0x553311ff, 0x664433ff,  0x332211ff, 0x996633ff, 0xccaa66ff};
    public Wood(){
        e_id = 20;
        name = "Wood";
        density = 150;
        static_object = true;
        color = trans(colors[new Random().nextInt(5)]);
        inertia_viscosity = 0;
        thermal_conductivity = 3.2f;
        heat_capacity = 8.1f;
        init_temperature = 25;
        melting_point = 500;
        melting_latent = 0;
        freezing_point = -250;
        freezing_latent = 0;
        melted = 21;
        freezed = 20;
    }

    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {
        if(world[x][y].temperature()>=400) world[x][y].changeElementS(new Charcoal());
    }
}
