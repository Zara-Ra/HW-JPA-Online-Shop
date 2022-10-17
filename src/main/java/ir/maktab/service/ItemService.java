package ir.maktab.service;

import ir.maktab.model.entity.ShoppingCard;
import ir.maktab.model.entity.items.Item;
import ir.maktab.model.enums.ProductCategory;

import java.util.List;
import java.util.Map;

public interface ItemService {
    List<Item> itemsByCategory(ProductCategory productCategory);

    void increaseShopItemsCount(Map<ProductCategory, List<Item>> shopItems, Item item, Integer increaseNumber);

    void updateShopItemsCount(ShoppingCard shoppingCard, Map<ProductCategory, List<Item>> shopItems);

    int findItemInShop(Item item, Map<ProductCategory, List<Item>> shopItems);

}
