package Sand;

import GUI.*;

import java.awt.*;

public class Main {
    static int width=400;
    static int height=400;
    static int length=2;
    static FrameController wnd = new FrameController("Sand", "D:\\Java\\Sand\\Images\\images.jpg", true, width*length, height*length);
    static Loadout loadout = new Loadout();

    public static void main(String[] args) {
        Start();

        new Thread(()->{
            while(true){
                Update();
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {}
            }
        }).start();
    }
    static void Start(){
        wnd.addScene(loadout.loadout_1("world", width, height, length), 0);
        wnd.addScene(loadout.loadout_2("main menu"), 1);

        wnd.InitFrame();
        wnd.ChangeScene("world");
    }

    static void Update(){
        wnd.update();
    }

    public static void RequestScene(String name){
        wnd.ChangeScene(name);
    }
}