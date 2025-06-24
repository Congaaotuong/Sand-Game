package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Solid;
import java.io.Serializable;
import java.util.Random;


public class Glass extends Solid  implements Serializable {
    long[] colors = {0xdbe1e3ff, 0xc0cbcdff, 0xd8e4e9ff,0xdbe1e3ff, 0xc0cbcdff, 0xd8e4e9ff, 0xa7c7cbff};
    public Glass(){
        e_id = 11;
        name = "Glass";
        density = 250;
        static_object = true;
        color = trans(colors[new Random().nextInt(7)]);
        inertia_viscosity = 0;
        thermal_conductivity = 5.5f;
        heat_capacity = 4.3f;
        init_temperature = 25;
        melting_point = 1126;
        melting_latent = 4.87f;
        freezing_point = -250;
        freezing_latent = 0;
        melted = 10;
        freezed = 11;
    }
    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {

    }
}
