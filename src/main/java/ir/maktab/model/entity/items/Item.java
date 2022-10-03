package ir.maktab.model.entity.items;

import ir.maktab.model.enums.ItemType;
import ir.maktab.util.exceptions.ItemUnavailableException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor

public abstract class Item {
    private ItemType type;
    private String name; //title of book,brand name
    private double price;
    private String description;
    private int count;

    public Item() {
    }

    public boolean isAvailable() throws ItemUnavailableException {
        if(this.count == 0)
            throw new ItemUnavailableException("This Item is not currently available...");
        return true;
    }
}
