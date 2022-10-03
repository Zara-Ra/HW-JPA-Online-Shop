package ir.maktab.model.entity.items;

import ir.maktab.model.enums.ItemType;
import ir.maktab.util.exceptions.ItemUnavailableException;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public abstract class Item {
    private ItemType type;
    private String name; //title of book,brand name
    private double price;
    private String description;
    private int count;

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Double.compare(item.getPrice(), getPrice()) == 0 && getType() == item.getType() && Objects.equals(getName(), item.getName()) && Objects.equals(getDescription(), item.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getName(), getPrice(), getDescription());
    }*/

    public Item() {
    }

    public boolean isAvailable() throws ItemUnavailableException {
        if(this.count == 0)
            throw new ItemUnavailableException("This Item is not currently available...");
        return true;
    }
}
