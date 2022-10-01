package ir.maktab.model.entity.items;

import ir.maktab.model.enums.Color;
import ir.maktab.model.enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Shoes extends Item{
    private int size;
    private Color color;
    private Gender gender;
}
