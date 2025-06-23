package SimulationEngine.Elements;

import SimulationEngine.Cell;
import SimulationEngine.SubElements.Solid;

import java.util.Random;

public class Copper extends Solid {
    long[] colors = {0xbb785bff, 0xbf816aff, 0xa96148ff, 0xb27862ff, 0xbe7a57ff};
    int oxidize =0;
    public Copper(){
        e_id = 17;
        name = "Copper";
        density = 896;
        static_object = true;
        color = trans(colors[new Random().nextInt(5)]);
        inertia_viscosity = 0;
        thermal_conductivity = 4;
        heat_capacity = 1.84f;
        init_temperature = 25;
        melting_point = 1085;
        melting_latent = 12.43f;
        freezing_point = -250;
        freezing_latent = 0;
        melted = 18;
        freezed = 17;
    }

    @Override
    public void AttributeChange(Cell[][] world, int x, int y) {
        if(oxidize >=200) {
            if(new Random().nextInt(10)!=2) return;
            world[x][y].changeElementS(new RustedCopper());
        }
        if(world[x-1][y].element().id()==5 || world[x+1][y].element().id()==5 || world[x][y-1].element().id()==5 || world[x][y+1].element().id()==5){
            if(new Random().nextInt(2)==0) oxidize++;
        }
        if(world[x-1][y].element().type()>0 || world[x+1][y].element().type()>0 || world[x][y-1].element().type()>0 || world[x][y+1].element().type()>0){
            if(new Random().nextInt(4)==2) oxidize++;
        }
    }
}
