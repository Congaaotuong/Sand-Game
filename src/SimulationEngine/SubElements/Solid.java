package SimulationEngine.SubElements;

import SimulationEngine.Cell;
import SimulationEngine.Element;
import java.util.Random;

public abstract class Solid extends Element {
    public Solid(){
        type = 0;
    }

    @Override
    public void SetDirection(Cell[][] world, int x, int y, int phase) {
        if (static_object) return;
        if(phase==0) return;
        int dir = getDirection(world[x-1][y].element().type(), world[x+1][y].element().type(), world[x][y+1].element().type(),
                world[x-1][y+1].element().type(), world[x+1][y+1].element().type());
        if(dir==1) world[x][y].changeFalling(true);
        if(!world[x][y].isFalling()||dir==2||dir==4) {
            dir=0;
        }
        world[x][y].changeDirection(dir);
    }

    @Override
    public void UpdateElement(Cell[][] world, int x, int y, int phase) {
        if(static_object) return;
        if(phase==0) return;
        int direction = world[x][y].direction();
        if(direction==0){
            return;
        }
        if(direction==1){
            switchCell(world, x, y, x, y+1);
            Turn(world, x, y+1);
            return;
        }
        int dir=direction-4;
        world[x][y].changeFalling(CalPercentage(inertia_viscosity));
        switchCell(world, x, y, x+dir, y+1);
        Turn(world, x+dir, y+1);
    }

    private void Turn(Cell[][] world, int x, int y){
        int width = world.length;
        int height = world[0].length;
    
        for(int i = x - 1; i <= x + 1; i++){
            for(int j = y - 1; j <= y + 1; j++){
                // Kiểm tra i, j có nằm trong giới hạn không
                if (i < 0 || i >= width || j < 0 || j >= height) continue;
    
                if(world[i][j].element().isStatic() || (i == x && j == y)) continue;
                if(!CalPercentage(world[i][j].element().inertia_viscosity())) continue;
                world[i][j].changeFalling(true);
            }
        }
    }
    
    private int getDirection(int l, int r, int d, int ld, int rd){
        if(d>0) return 1;
        int dir=15;
        if(l<=0 || ld<=0) dir/=3;
        if(r<=0 || rd<=0) dir/=5;
        switch(dir){
            case 1: return 0;
            case 15:
                dir = (new Random().nextInt(2)==0)? 3:5;
                return dir;
            default: return dir;
        }
    }
}
