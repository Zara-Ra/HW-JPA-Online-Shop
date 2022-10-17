package ir.maktab.service;

import ir.maktab.model.entity.ShoppingCard;
import ir.maktab.model.entity.User;
import ir.maktab.model.entity.items.Item;
import ir.maktab.util.exceptions.ShoppingCardException;

import java.util.Iterator;
import java.util.Map;

public interface ShoppingCardService {
    void addItem(User user, Item item) throws ShoppingCardException;

    void removeItem(Iterator iterator);

    void confirmShopping(ShoppingCard shoppingCard);

    ShoppingCard findShoppingCard(User user);

    void editNumOfItem(Map<Item, Integer> shoppingItemMap, Item item, int num);

}
