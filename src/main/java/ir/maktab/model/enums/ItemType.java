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
            case BOOK:
            case MAGAZINE:
                return ProductCategory.READABLE;
            case TV:
            case RADIO:
                return ProductCategory.ELECTRONICS;
            case CASUAL:
            case FORMAL:
                return ProductCategory.SHOES;
            default:
                return null;
        }
    }
}
