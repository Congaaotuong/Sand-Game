package CustomDataType;

import java.io.Serializable;

public class SavingObject implements Serializable {
    int first;
    long second;
    public SavingObject(int first, long second){
        this.first=first;
        this.second=second;
    }
    public int getFirst(){return this.first;}
    public long getSecond(){return this.second;}
}
