package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Solid;
import SimulationEngine.World;

import java.util.Random;

public class Charcoal extends Solid {
    long[] colors = {0x000000ff, 0x221105ff, 0x000000ff, 0x221105ff, 0x2d1d10ff, 0x403122ff, 0x221105ff, 0x4d402fff, 0x635442ff};
    int burned=0;
    public Charcoal(){
        e_id = 26;
        name = "Burning Wood";
        density = 40;
        static_object = true;
        color = trans(colors[new Random().nextInt(9)]);
        inertia_viscosity = 0;
        thermal_conductivity = 4f;
        heat_capacity = 9.1f;
        init_temperature = 25;
        melting_point = 1000;
        melting_latent = 0;
        freezing_point = -250;
        freezing_latent = 0;
        melted = 26;
        freezed = 26;
    }

    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {
        Random rand = new Random();
        boolean l = (world[x-1][y].element().id()==0);
        boolean r = (world[x+1][y].element().id()==0);
        boolean u = (world[x][y-1].element().id()==0);
        boolean d = (world[x][y+1].element().id()==0);
        if(l || r || u || d){
            if(world[x][y].temperature()>=1000) world[x][y].changeElementS(new BurningCoal());
        }
    }
}
