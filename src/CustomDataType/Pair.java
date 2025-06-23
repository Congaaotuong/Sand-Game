package CustomDataType;

public class Pair <F, S>{
    F first;
    S second;
    public Pair(F first, S second){
        this.first = first;
        this.second = second;
    }

    public F first(){return this.first;}
    public S second(){return this.second;}
    public void change(F new_first, S new_second){
        this.first = new_first;
        this.second = new_second;
    }
}
