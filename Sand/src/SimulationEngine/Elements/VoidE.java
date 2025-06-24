package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.Element;

public class VoidE extends Element {
    public VoidE(){
        e_id = 0;
        name = "Void";
        type = 3;
        density = 0.1f;
        static_object = true;
        inertia_viscosity = 0;
        color = trans(0x000000ff);
        thermal_conductivity = 1f;
        heat_capacity = 10f;
        init_temperature = 25;
        melting_point = 0;
        freezing_point = 0;
        melting_latent = 0;
        freezing_latent = 0;
        melted=0;
        freezed=0;
    }
    @Override
    public void SetDirection(Cell[][] world, int x, int y, int phase) {
        return;
    }
    @Override
    public void UpdateElement(Cell[][] world, int x, int y, int phase) {
        return;
    }

    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {
        world[x][y].setTemperature(25);
    }
}
