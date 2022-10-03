package ir.maktab.model.entity.items;

import ir.maktab.model.enums.AgeRange;
import ir.maktab.model.enums.CoverType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class Readable extends Item{
    private CoverType cover;
    private AgeRange ageRange;
    private int numOfPage;

    @Override
    public String toString() {
        return "\n*** " + getType() +" *** "+
                " Title: " + getName() +
                " Cover: " + this.cover +
                " Age Range: " + this.ageRange +
                " Number Of Pages: " + this.numOfPage +
                " Price: " + getPrice() +
                " Description: " + getDescription();

    }
}
