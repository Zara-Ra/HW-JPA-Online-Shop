package ir.maktab.model.enums;

public enum ItemType {
    BOOK,
    MAGAZINE,
    TV,
    RADIO,
    CASUAL,
    FORMAL;

    public ProductCategory toProductCategory() {
        switch (this) {
            case BOOK, MAGAZINE:
                return ProductCategory.READABLE;
            case TV, RADIO:
                return ProductCategory.ELECTRONICS;
            case CASUAL, FORMAL:
                return ProductCategory.SHOES;
            default:
                return null;
        }
    }
}
