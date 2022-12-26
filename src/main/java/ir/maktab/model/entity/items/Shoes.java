package ir.maktab.model.entity.items;

import ir.maktab.model.enums.Color;
import ir.maktab.model.enums.Gender;
import ir.maktab.model.enums.ItemType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Shoes extends Item {
    private int size;
    @Enumerated(value = EnumType.STRING)
    private Color color;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    public Shoes(ItemType type, String name, double price, String description, int count, int size, Color color, Gender gender) {
        super(type, name, price, description, count);
        this.size = size;
        this.color = color;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "\n*** " + getType() + " *** " +
                " Price: " + getPrice() +
                " Brand: " + getName() +
                " Size: " + this.size +
                " Color: " + this.color +
                " Gender: " + this.gender +
                " Description: " + getDescription();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shoes shoes)) return false;
        if (!super.equals(o)) return false;
        return getSize() == shoes.getSize() && getColor() == shoes.getColor() && getGender() == shoes.getGender();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSize(), getColor(), getGender());
    }
}
