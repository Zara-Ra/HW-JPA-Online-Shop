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

    private String model;

    @Override
    public String toString() {
        return "\n*** " + getType() +" *** "+
                " Brand: " + getName() +
                " Model: " + this.model +
                " Price: " + getPrice() +
                " Description: " + getDescription();
    }
}
