package ir.maktab.service;

import ir.maktab.model.entity.items.Item;
import ir.maktab.model.enums.ItemType;
import ir.maktab.model.repository.ElectronicsRepo;
import ir.maktab.model.repository.ReadableRepo;
import ir.maktab.model.repository.ShoesRepo;
import ir.maktab.util.exceptions.DataBaseException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Item> availableItems(ItemType type) {
        List<Item> itemList = new ArrayList<>();
        switch (type){
            case TV:
            case RADIO:
                try {
                    itemList.addAll(electronicsRepo.availableItems());
                } catch (SQLException e) {
                    throw new DataBaseException(e.getMessage());
                }
                break;
            case BOOK:
            case MAGAZINE:
                try {
                    itemList.addAll(readableRepo.availableItems());
                } catch (SQLException e) {
                    throw new DataBaseException(e.getMessage());
                }
                break;
            case FORMAL:
            case CASUAL:
                try {
                    itemList.addAll(shoesRepo.availableItems());
                } catch (SQLException e) {
                    throw new DataBaseException(e.getMessage());
                }
                break;
        }
        return itemList;
    }
}
