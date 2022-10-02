package ir.maktab.service;

import ir.maktab.model.entity.items.Electronics;
import ir.maktab.model.entity.items.Item;
import ir.maktab.model.entity.items.TV;
import ir.maktab.model.repository.ElectronicsRepo;
import ir.maktab.util.exceptions.DataBaseException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemService {
    private ItemService(){}
    private static final ItemService instance = new ItemService();
    public static ItemService getInstance(){
        return instance;
    }
    ElectronicsRepo electronicsRepo = ElectronicsRepo.getInstance();

    public List<Item> availableItems(){
        Electronics electronicsItem = new TV();
        List<Item> itemList = new ArrayList<>();
        try {
             itemList.addAll(electronicsRepo.availableItems(electronicsItem));
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        }
        return itemList;
    }
}
