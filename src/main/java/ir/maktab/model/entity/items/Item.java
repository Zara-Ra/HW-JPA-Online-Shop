package ir.maktab.model.entity.items;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Item {
    private int ID;
    private String name; //title of book,brand name
    private double price;
    private String description;
    private int count;
    public boolean isAvailable(){
        if(this.count == 0)
            throw new RuntimeException("This Item is not currently available...");
        return true;
    }
}
