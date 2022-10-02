package ir.maktab.model.entity.items;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TV extends Electronics{
    private int inch;
    public TV(String name, double price, String description, int count, String model, int inch) {
        super(name, price, description, count, model);
        this.inch = inch;
    }

    public TV() {
        super();
    }
}
