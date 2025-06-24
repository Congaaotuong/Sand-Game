package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Solid;
import SimulationEngine.World;
import java.io.Serializable;
import java.util.Random;

public class BurningWood extends Solid implements Serializable {
    long[] colors = {0xffbf00ff, 0xffa500ff, 0xff8000ff, 0xff4000ff, 0xff1500ff};
    int burned=0;
    public BurningWood(){
        e_id = 21;
        name = "BurningWood";
        density = 50;
        static_object = true;
        color = trans(colors[new Random().nextInt(5)]);
        inertia_viscosity = 0;
        thermal_conductivity = 0.1f;
        heat_capacity = 8.1f;
        init_temperature = 500;
        melting_point = 1000;
        melting_latent = 0;
        freezing_point = -250;
        freezing_latent = 0;
        melted = 21;
        freezed = 21;
    }

    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {
        Random random =new Random();
        world[x][y].setTemperature(init_temperature);
        color = trans(colors[new Random().nextInt(5)]);
        if(burned>=200){
            if(random.nextInt(4)!=2) return;
            world[x][y].changeElementS(new Ash());
        }
        if(random.nextInt(4)==2){
            burned++;
        }
        if(world[x][y-1].element().id()==0){
            if(random.nextInt(4)==2){
                World.UpdateNextElement(x, y-1, 15);
                return;
            }
        }
        if(world[x-1][y].element().id()==0){
            if(random.nextInt(4)==2){
                World.UpdateNextElement(x-1, y, 15);
                return;
            }
        }
        if(world[x+1][y].element().id()==0){
            if(random.nextInt(4)==2){
                World.UpdateNextElement(x+1, y, 15);
                return;
            }
        }
    }
}
