package ir.maktab.model.entity.items;

import ir.maktab.model.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@MappedSuperclass
public abstract class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private ItemType type;
    private String name;
    private double price;
    private String description;
    private int count;

    protected Item() {//Constructor of an abstract class can never be called, except in extended classes so access modifier could be protected
    }

    public Item(ItemType type, String name, double price, String description, int count) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.description = description;
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        return Double.compare(item.getPrice(), getPrice()) == 0 && getType() == item.getType() && Objects.equals(getName(), item.getName()) && Objects.equals(getDescription(), item.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getName(), getPrice(), getDescription());
    }

    public void countMinus() {
        this.count--;
    }

    public void increaseCount(Integer increaseNumber) {
        this.count += increaseNumber;
    }
}
