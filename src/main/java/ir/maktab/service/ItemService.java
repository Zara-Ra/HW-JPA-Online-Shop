package ir.maktab.service;

import ir.maktab.model.entity.ShoppingCard;
import ir.maktab.model.entity.items.Electronics;
import ir.maktab.model.entity.items.Item;
import ir.maktab.model.entity.items.Readable;
import ir.maktab.model.entity.items.Shoes;
import ir.maktab.model.enums.ProductCategory;
import ir.maktab.model.repository.ElectronicsRepo;
import ir.maktab.model.repository.ReadableRepo;
import ir.maktab.model.repository.ShoesRepo;
import ir.maktab.util.exceptions.DataBaseException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemService {
    private ItemService() {
    }

    private static final ItemService instance = new ItemService();

    public static ItemService getInstance() {
        return instance;
    }

    ElectronicsRepo electronicsRepo = ElectronicsRepo.getInstance();
    ReadableRepo readableRepo = ReadableRepo.getInstance();
    ShoesRepo shoesRepo = ShoesRepo.getInstance();

    public List<Item> itemsByCategory(ProductCategory productCategory) {
        List<Item> itemList = new ArrayList<>();
        switch (productCategory) {
            case ELECTRONICS:
                try {
                    itemList.addAll(electronicsRepo.availableItems());
                } catch (SQLException e) {
                    throw new DataBaseException(e.getMessage());
                }
                break;
            case READABLE:
                try {
                    itemList.addAll(readableRepo.availableItems());
                } catch (SQLException e) {
                    throw new DataBaseException(e.getMessage());
                }
                break;
            case SHOES:
                try {
                    itemList.addAll(shoesRepo.availableItems());
                } catch (SQLException e) {
                    throw new DataBaseException(e.getMessage());
                }
                break;
        }
        return itemList;
    }

    public void increaseShopItemsCount(Map<ProductCategory, List<Item>> shopItems, Item item, Integer increaseNumber) {
        List<Item> itemList = shopItems.get(item.getType().toPrdoductCategory());
        for (int i = 0; i < itemList.size(); i++) {
            if (item.equals(itemList.get(i))) {
                itemList.get(i).increaseCount(increaseNumber);
                return;
            }
        }
    }

    public void updateShopItemsCount(ShoppingCard shoppingCard, Map<ProductCategory, List<Item>> shopItems) {
        Map<Item, Integer> shoppingItemsMap = shoppingCard.getShoppingItemsMap();
        for (Map.Entry<Item, Integer> entry : shoppingItemsMap.entrySet()) {
            int num = findItemInShop(entry.getKey(),shopItems);
            switch (entry.getKey().getType().toPrdoductCategory()){
                case ELECTRONICS:
                    try {
                        electronicsRepo.editCount((Electronics) entry.getKey(),num);
                    } catch (SQLException e) {
                        throw new DataBaseException(e.getMessage());
                    }
                    break;
                case READABLE:
                    try {
                        readableRepo.editCount((Readable) entry.getKey(),num);
                    } catch (SQLException e) {
                        throw new DataBaseException(e.getMessage());
                    }
                    break;
                case SHOES:
                    try {
                        shoesRepo.editCount((Shoes) entry.getKey(),num);
                    } catch (SQLException e) {
                        throw new DataBaseException(e.getMessage());
                    }
                    break;
            }
        }
    }

    private int findItemInShop(Item item, Map<ProductCategory, List<Item>> shopItems) {
        for (Map.Entry<ProductCategory,List<Item>> i: shopItems.entrySet()) {
            List<Item> tempList = i.getValue();
            for (int j = 0; j < tempList.size(); j++) {
                if(item.equals(tempList.get(j)))
                    return tempList.get(j).getCount();
            }
        }
        return 0;
    }
}
