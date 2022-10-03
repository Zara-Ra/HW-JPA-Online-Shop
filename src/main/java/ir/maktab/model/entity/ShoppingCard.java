package ir.maktab.model.entity;

import ir.maktab.model.entity.items.Item;
import ir.maktab.model.enums.ConfirmStatus;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ShoppingCard {
    private User user;
    private Map<Item, Integer> shoppingItemsMap = new HashMap<>();
    private ConfirmStatus confirmStatus;
    private Date date;
}
