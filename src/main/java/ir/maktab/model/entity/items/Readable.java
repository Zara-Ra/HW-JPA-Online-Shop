package ir.maktab.model.entity.items;

import ir.maktab.model.enums.AgeRange;
import ir.maktab.model.enums.CoverType;
import ir.maktab.model.enums.ItemType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
public class Readable extends Item{
    private CoverType cover;
    private AgeRange ageRange;
    private int numOfPage;

    public Readable(ItemType type, String name, double price, String description, int count, CoverType cover, AgeRange ageRange, int numOfPage) {
        super(type, name, price, description, count);
        this.cover = cover;
        this.ageRange = ageRange;
        this.numOfPage = numOfPage;
    }

    @Override
    public String toString() {
        return "\n*** " + getType() +" *** "+
                " Price: " + getPrice() +
                " Title: " + getName() +
                " Cover: " + this.cover +
                " Age Range: " + this.ageRange +
                " Number Of Pages: " + this.numOfPage +
                " Description: " + getDescription();

    }
}
