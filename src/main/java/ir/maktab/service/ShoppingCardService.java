package ir.maktab.service;

import ir.maktab.model.entity.User;
import ir.maktab.model.entity.items.Item;
import ir.maktab.model.repository.ShoppingCardRepo;
import ir.maktab.util.exceptions.ShoppingCardFullExcepiton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ShoppingCardService {
    private ShoppingCardService() {
    }

    private static final ShoppingCardService instance = new ShoppingCardService();

    public static ShoppingCardService getInstance() {
        return instance;
    }

    private ShoppingCardRepo shoppingCardRepo = ShoppingCardRepo.getInstance();

    public boolean addItem(User user, Item item) {
        Map<Item, Integer> shoppingItemsMap = user.getShoppingCard().getShoppingItemsMap();
        if (shoppingItemsMap.size() == 5) {
            throw new ShoppingCardFullExcepiton("There are already 5 Distinct Items in Shopping Card");
        }
        Integer numOfItem = 1;
        if(shoppingItemsMap.containsKey(item)){
            numOfItem = shoppingItemsMap.get(item);
            numOfItem++;
        }
        shoppingItemsMap.put(item, numOfItem);
        return true;
    }

    public void removeItem(Iterator iterator){
        iterator.remove();
    }

    public List<Item> allItems() {
        return new ArrayList<>();
    }

    public boolean confirmShopping() {//here i will enter the shoppingcard info in to the database, should also be called from signout
        return true;
    }

    public void editNumOfItem(Map<Item, Integer> shoppingItemMap, Item item, int num) {
        shoppingItemMap.replace(item,num);
    }
}
