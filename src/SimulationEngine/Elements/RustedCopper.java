package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Solid;
import java.util.Random;

public class RustedCopper extends Solid {
    long[] colors = {0xbaa37fff, 0xa1baa1ff, 0x71d4ccff, 0x7fe7ceff};
    public RustedCopper(){
        e_id = 19;
        name = "RustedCopper";
        density = 896;
        static_object = true;
        color = trans(colors[new Random().nextInt(4)]);
        inertia_viscosity = 0;
        thermal_conductivity = 4;
        heat_capacity = 1.84f;
        init_temperature = 25;
        melting_point = 1085;
        melting_latent = 12.43f;
        freezing_point = -250;
        freezing_latent = 0;
        melted = 18;
        freezed = 19;
    }

    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {

    }
}
