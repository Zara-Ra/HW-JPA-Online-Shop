package ir.maktab.service.impl;

import ir.maktab.model.entity.ShoppingCard;
import ir.maktab.model.entity.User;
import ir.maktab.model.entity.items.Item;
import ir.maktab.model.enums.ConfirmStatus;
import ir.maktab.model.repository.impl.ElectronicsRepo;
import ir.maktab.model.repository.impl.ReadableRepo;
import ir.maktab.model.repository.impl.ShoesRepo;
import ir.maktab.model.repository.impl.ShoppingCardRepoImpl;
import ir.maktab.service.ShoppingCardService;
import ir.maktab.util.exceptions.ShoppingCardException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ShoppingCardServiceImpl implements ShoppingCardService {
    private static final ShoppingCardServiceImpl instance = new ShoppingCardServiceImpl();
    private final ShoppingCardRepoImpl shoppingCardRepoImpl = ShoppingCardRepoImpl.getInstance();
    ElectronicsRepo electronicsRepo = ElectronicsRepo.getInstance();
    ReadableRepo readableRepo = ReadableRepo.getInstance();
    ShoesRepo shoesRepo = ShoesRepo.getInstance();
    private ShoppingCardServiceImpl() {
    }

    public static ShoppingCardServiceImpl getInstance() {
        return instance;
    }

    @Override
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

    @Override
    public void removeItem(Iterator iterator) {
        iterator.remove();
    }

    @Override
    public void confirmShopping(ShoppingCard shoppingCard) {
        double totalPrice = 0;
        try {
            int id = shoppingCardRepoImpl.findIDByUsername(shoppingCard.getUser().getUsername(), ConfirmStatus.PENDING);
            if (id != 0) {
                shoppingCardRepoImpl.deleteShoppingCard(id);
                //is it rational to compare the previous card with the current card and edit the database?
                //or simply delete the card so all the referenced data is deleted and add the new card all over again?
                //sorry for the comment, I barely connect to telegram!
                //ShoppingCard prevShoppingCard = findShoppingCard(shoppingCard.getUser());
            }
            shoppingCardRepoImpl.insertShoppingCard(shoppingCard);
            id = shoppingCardRepoImpl.findIDByUsername(shoppingCard.getUser().getUsername(), shoppingCard.getConfirmStatus());
            for (Map.Entry<Item, Integer> entry : shoppingCard.getShoppingItemsMap().entrySet()) {
                shoppingCardRepoImpl.addShoppingCardItem(id, entry);
                totalPrice += entry.getKey().getPrice() * entry.getValue();
            }
            if (shoppingCard.getConfirmStatus() != ConfirmStatus.DELETE)
                shoppingCardRepoImpl.updateTotalPrice(id, totalPrice);
        } catch (SQLException e) {
            System.err.println("DataBase Error,Confirming Shopping");
        }
    }

    @Override
    public ShoppingCard findShoppingCard(User user) {
        ShoppingCard result = new ShoppingCard(user, ConfirmStatus.PENDING);
        int shoppingCardID = 0;
        try {
            shoppingCardID = shoppingCardRepoImpl.findIDByUsername(user.getUsername(), ConfirmStatus.PENDING);
        } catch (SQLException e) {
            System.err.println("DataBase Error, Unable to find Shopping Card");
        }
        if (shoppingCardID == 0)
            return result;
        Map<Item, Integer> shoppingItemsMap = new HashMap<>();
        try {
            shoppingItemsMap.putAll(electronicsRepo.findShoppingCardItems(shoppingCardID));
            shoppingItemsMap.putAll(shoesRepo.findShoppingCardItems(shoppingCardID));
            shoppingItemsMap.putAll(readableRepo.findShoppingCardItems(shoppingCardID));
        } catch (SQLException e) {
            System.err.println("DataBase Error, Unable to find Shopping Card");
        }

        result.setShoppingItemsMap(shoppingItemsMap);
        return result;
    }

    @Override
    public void editNumOfItem(Map<Item, Integer> shoppingItemMap, Item item, int num) {
        shoppingItemMap.replace(item, num);
    }
}
