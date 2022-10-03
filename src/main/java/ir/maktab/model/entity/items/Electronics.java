package ir.maktab.model.entity.items;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Electronics extends Item{

    public Electronics() {
    }

    private String model;
}
