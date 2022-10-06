package ir.maktab.model.entity.items;

import ir.maktab.model.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Electronics extends Item {

    public Electronics(ItemType type, String name, double price, String description, int count, String brand) {
        super(type, name, price, description, count);
        this.brand = brand;
    }

    private String brand;

    @Override
    public String toString() {
        return "\n*** " + getType() + " *** " +
                " Price: " + getPrice() +
                " Model: " + getName() +
                " Brand: " + this.brand +
                " Description: " + getDescription();
    }
}
