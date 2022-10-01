package ir.maktab.model.entity.items;

import ir.maktab.model.enums.AgeRange;
import ir.maktab.model.enums.CoverType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Readable extends Item{
    private CoverType cover;
    private AgeRange ageRange;
}
