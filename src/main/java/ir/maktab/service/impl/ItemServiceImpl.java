package ir.maktab.service.impl;

import ir.maktab.model.entity.ShoppingCard;
import ir.maktab.model.entity.items.Electronics;
import ir.maktab.model.entity.items.Item;
import ir.maktab.model.entity.items.Readable;
import ir.maktab.model.entity.items.Shoes;
import ir.maktab.model.enums.ProductCategory;
import ir.maktab.model.repository.Impl.ElectronicsRepo;
import ir.maktab.model.repository.Impl.ReadableRepo;
import ir.maktab.model.repository.Impl.ShoesRepo;
import ir.maktab.service.ItemService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemServiceImpl implements ItemService {
    private ItemServiceImpl() {
    }

    private static final ItemServiceImpl instance = new ItemServiceImpl();

    public static ItemServiceImpl getInstance() {
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
                    System.err.println("DataBase Error,Electronics");
                }
                break;
            case READABLE:
                try {
                    itemList.addAll(readableRepo.availableItems());
                } catch (SQLException e) {
                    System.err.println("DataBase Error,Readable");
                }
                break;
            case SHOES:
                try {
                    itemList.addAll(shoesRepo.availableItems());
                } catch (SQLException e) {
                    System.err.println("DataBase Error,Shoes");
                }
                break;
        }
        return itemList;
    }

    public void increaseShopItemsCount(Map<ProductCategory, List<Item>> shopItems, Item item, Integer increaseNumber) {
        List<Item> itemList = shopItems.get(item.getType().toProductCategory());
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
            int num = findItemInShop(entry.getKey(), shopItems);
            switch (entry.getKey().getType().toProductCategory()) {
                case ELECTRONICS:
                    try {
                        electronicsRepo.editCount((Electronics) entry.getKey(), num);
                    } catch (SQLException e) {
                        System.err.println("DataBase Error,Unable to Update Items Count");
                    }
                    break;
                case READABLE:
                    try {
                        readableRepo.editCount((Readable) entry.getKey(), num);
                    } catch (SQLException e) {
                        System.err.println("DataBase Error,Unable to Update Items Count");
                    }
                    break;
                case SHOES:
                    try {
                        shoesRepo.editCount((Shoes) entry.getKey(), num);
                    } catch (SQLException e) {
                        System.err.println("DataBase Error,Unable to Update Items Count");
                    }
                    break;
            }
        }
    }

    public int findItemInShop(Item item, Map<ProductCategory, List<Item>> shopItems) {
        for (Map.Entry<ProductCategory, List<Item>> i : shopItems.entrySet()) {
            List<Item> tempList = i.getValue();
            for (int j = 0; j < tempList.size(); j++) {
                if (item.equals(tempList.get(j)))
                    return tempList.get(j).getCount();
            }
        }
        return 0;
    }
}
