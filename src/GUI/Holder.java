package GUI;

import java.awt.*;

public class Holder extends Panel {
    String Name;
    TheFunction func;
    public Holder(String Name){
        this.Name = Name;
        func = ()->{System.out.println("executed!");};
    }
    public String Name(){return this.Name;}
    public void changeTheFunction(TheFunction func){this.func = func;}
    public void Activate(){this.func.Execute();}
}
