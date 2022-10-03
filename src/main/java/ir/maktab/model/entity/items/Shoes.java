package ir.maktab.model.entity.items;

import ir.maktab.model.enums.Color;
import ir.maktab.model.enums.Gender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class Shoes extends Item{
    private int size;
    private Color color;
    private Gender gender;

    @Override
    public String toString() {
        return "\n*** " + getType() +" *** "+
                " Brand: " + getName() +
                " Size: " + this.size +
                " Color: "+this.color+
                " Gender: "+this.gender+
                " Price: " + getPrice() +
                " Description: " + getDescription();

    }
}
