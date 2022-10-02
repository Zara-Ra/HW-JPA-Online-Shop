package ir.maktab.model.entity.items;

import ir.maktab.model.enums.AgeRange;
import ir.maktab.model.enums.CoverType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Readable extends Item{
    private CoverType cover;
    private AgeRange ageRange;
}
