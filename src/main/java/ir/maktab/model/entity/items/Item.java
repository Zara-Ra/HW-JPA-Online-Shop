package ir.maktab.model.entity.items;

import ir.maktab.util.exceptions.ItemUnavailableException;
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

    public Item(String name, double price, String description, int count) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.count = count;
    }

    public boolean isAvailable() throws ItemUnavailableException {
        if(this.count == 0)
            throw new ItemUnavailableException("This Item is not currently available...");
        return true;
    }
}
