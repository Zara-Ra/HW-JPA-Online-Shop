package ir.maktab.model.enums;

import lombok.EqualsAndHashCode;


public enum ProductCategory {
    ELECTRONICS,
    READABLE,
    SHOES;

    public String tableName() {
        switch (this){
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
