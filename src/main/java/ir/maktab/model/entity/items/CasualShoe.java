package ir.maktab.model.entity.items;

import ir.maktab.model.enums.CasualType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CasualShoe extends Shoes{
    private CasualType type;
}
