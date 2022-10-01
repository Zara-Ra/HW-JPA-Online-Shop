package ir.maktab.model.entity;

import ir.maktab.model.entity.items.Item;
import ir.maktab.model.enums.ConfirmStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class ShoppingCart {
    User user;
    List<Item> shoppingList = new ArrayList<>();
    ConfirmStatus confirmStatus;
}
