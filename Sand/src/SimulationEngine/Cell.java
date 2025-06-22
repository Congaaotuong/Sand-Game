package SimulationEngine;

import SimulationEngine.Elements.*;
public class Cell {
    int x_pos;
    int y_pos;
    int direction;
    boolean falling;
    float temperature;
    float reserved;
    Element element;
    public Cell(int x_pos, int y_pos){
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.direction = 0;
        this.falling=true;
        this.element = new VoidE();
        this.temperature = this.element().init_temperature();
        this.reserved = 0;
    }
    public Element element(){return this.element;}
    public void changeElement(Element element){

        this.element = element;
        this.temperature = element.init_temperature();
    }
    public void changeElementS(Element element){

        this.element = element;
    }
    public int direction(){return this.direction;}
    public void changeDirection(int x){this.direction = x;}
    public boolean isFalling(){return this.falling;}
    public void changeFalling(boolean x){this.falling=x;}
    public float temperature(){return this.temperature;}
    public float reserved(){return this.reserved;}
    public void changeReserved(float x){this.reserved +=x;}
    public void setReserved(float x){this.reserved = x;}
    public void changeTemperature(float x){this.temperature+=x;}
    public void setTemperature(float x){this.temperature=x;}
    public int x_pos(){return this.x_pos;}
    public int y_pos(){return this.y_pos;}
    public void setPosition(int x, int y){
        this.x_pos = x;
        this.y_pos = y;
    }
    public void reset(){
        this.direction = 0;
        this.element = new VoidE();
        this.temperature = this.element.init_temperature();
    }
}
