package ir.maktab.service;

import ir.maktab.model.entity.items.Item;
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
}
