package ir.maktab.model.entity.items;

import ir.maktab.model.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Electronics extends Item {
    private String brand;//Could be enum

    private String tableName;
    public Electronics(ItemType type, String name, double price, String description, int count, String brand) {
        super(type, name, price, description, count);
        this.brand = brand;
        this.tableName = "item_electronics";
    }

    @Override
    public String toString() {
        return "\n*** " + getType() + " *** " +
                " Price: " + getPrice() +
                " Model: " + getName() +
                " Brand: " + this.brand +
                " Description: " + getDescription();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Electronics that)) return false;
        if (!super.equals(o)) return false;
        return getBrand().equals(that.getBrand());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getBrand());
    }

}
