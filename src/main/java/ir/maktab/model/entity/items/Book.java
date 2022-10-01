package ir.maktab.model.entity.items;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book extends Readable{
    private String author;
    private String publication;
}
