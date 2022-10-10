package ir.maktab.service;

import ir.maktab.model.entity.ShoppingCard;
import ir.maktab.model.entity.User;
import ir.maktab.model.entity.items.Item;
import ir.maktab.model.enums.ConfirmStatus;
import ir.maktab.model.repository.ShoppingCardRepo;
import ir.maktab.util.exceptions.ShoppingCardException;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

public class ShoppingCardService {
    private ShoppingCardService() {
    }

    private static final ShoppingCardService instance = new ShoppingCardService();

    public static ShoppingCardService getInstance() {
        return instance;
    }

    private final ShoppingCardRepo shoppingCardRepo = ShoppingCardRepo.getInstance();

    public void addItem(User user, Item item) throws ShoppingCardException {
        Map<Item, Integer> shoppingItemsMap = user.getShoppingCard().getShoppingItemsMap();
        Integer numOfItem = 1;
        if (shoppingItemsMap.containsKey(item)) {
            numOfItem = shoppingItemsMap.get(item);
            numOfItem++;
        } else if (shoppingItemsMap.size() == 5) {
            throw new ShoppingCardException("There Are Already 5 Distinct Items in the Shopping Card");
        }
        shoppingItemsMap.put(item, numOfItem);
        item.countMinus();
    }

    public void removeItem(Iterator iterator) {
        iterator.remove();
    }

    public void confirmShopping(ShoppingCard shoppingCard) {
        double totalPrice = 0;
        try {
            int id = shoppingCardRepo.findID(shoppingCard.getUser().getUsername(), ConfirmStatus.PENDING);
            if (id != 0) {
                shoppingCardRepo.deleteShoppingCard(id);
            }
            shoppingCardRepo.insertShoppingCard(shoppingCard);
            id = shoppingCardRepo.findID(shoppingCard.getUser().getUsername(), shoppingCard.getConfirmStatus());
            for (Map.Entry<Item, Integer> entry : shoppingCard.getShoppingItemsMap().entrySet()) {
                shoppingCardRepo.addShoppingCardItem(id, entry);
                totalPrice += entry.getKey().getPrice() * entry.getValue();
            }
            shoppingCardRepo.updateTotalPrice(id, totalPrice);
        } catch (SQLException e) {
            System.err.println("DataBase Error,Confirming Shopping");
        }
    }

    public void editNumOfItem(Map<Item, Integer> shoppingItemMap, Item item, int num) {
        shoppingItemMap.replace(item, num);
    }
}
