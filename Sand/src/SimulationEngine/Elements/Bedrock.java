package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.*;

import java.util.Random;

public class Bedrock extends Solid {
    long[] colors = {0x2d2c2cff, 0x3a3232ff, 0x493c3cff, 0x5c4949ff, 0x655353ff};
    public Bedrock(){
        e_id = 1;
        name = "Bedrock";
        density = 270;
        static_object = true;
        color = trans(colors[new Random().nextInt(5)]);
        inertia_viscosity = 0;
        thermal_conductivity = 4;
        heat_capacity = 3.8f;
        init_temperature = 25;
        melting_point = 500;
        melting_latent = 4.87f;
        freezing_point = -250;
        freezing_latent = 0;
        melted = 6;
        freezed = 1;
    }

    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {

    }
}
