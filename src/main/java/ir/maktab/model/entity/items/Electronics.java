package ir.maktab.model.entity.items;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Electronics extends Item{

    public Electronics(String name, double price, String description, int count, String model) {
        super(name, price, description, count);
        this.model = model;
    }

    public Electronics() {
    }

    private String model;
}
