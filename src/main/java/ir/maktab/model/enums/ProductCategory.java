package ir.maktab.model.enums;

public enum ProductCategory {
    ELECTRONICS,
    READABLE,
    SHOES;

    public String tableName() {
        switch (this) {
            case ELECTRONICS:
                return "item_electronics";
            case READABLE:
                return "item_readable";
            case SHOES:
                return "item_shoes";
            default:
                return null;
        }
    }
}
