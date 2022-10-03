package ir.maktab.model.entity.items;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class Electronics extends Item {

    public Electronics() {
    }

    private String brand;

    @Override
    public String toString() {
        return "\n*** " + getType() +" *** "+
                " Model: " + getName() +
                " Brand: " + this.brand +
                " Price: " + getPrice() +
                " Description: " + getDescription();
    }
}
