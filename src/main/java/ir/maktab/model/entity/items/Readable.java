package ir.maktab.model.entity.items;

import ir.maktab.model.enums.AgeRange;
import ir.maktab.model.enums.CoverType;
import ir.maktab.model.enums.ItemType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Readable extends Item {
    @Enumerated(value = EnumType.STRING)
    private CoverType cover;
    @Enumerated(value = EnumType.STRING)
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
        return "\n*** " + getType() + " *** " +
                " Price: " + getPrice() +
                " Title: " + getName() +
                " Cover: " + this.cover +
                " Age Range: " + this.ageRange +
                " Number Of Pages: " + this.numOfPage +
                " Description: " + getDescription();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Readable readable)) return false;
        if (!super.equals(o)) return false;
        return getNumOfPage() == readable.getNumOfPage() && getCover() == readable.getCover() && getAgeRange() == readable.getAgeRange();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCover(), getAgeRange(), getNumOfPage());
    }
}
