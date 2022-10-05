package ir.maktab.model.entity.items;

import ir.maktab.model.enums.Color;
import ir.maktab.model.enums.Gender;
import ir.maktab.model.enums.ItemType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
public class Shoes extends Item{
    private int size;
    private Color color;
    private Gender gender;

    public Shoes(ItemType type, String name, double price, String description, int count, int size, Color color, Gender gender) {
        super(type, name, price, description, count);
        this.size = size;
        this.color = color;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "\n*** " + getType() +" *** "+
                " Price: " + getPrice() +
                " Brand: " + getName() +
                " Size: " + this.size +
                " Color: "+this.color+
                " Gender: "+this.gender+
                " Description: " + getDescription();

    }
}
