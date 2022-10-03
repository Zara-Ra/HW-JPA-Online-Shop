package ir.maktab.model.entity.items;

import ir.maktab.model.enums.ItemType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book extends Readable{
    private String author;
    private String publication;
}
