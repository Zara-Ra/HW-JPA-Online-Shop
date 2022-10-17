package ir.maktab.model.repository;

import ir.maktab.model.entity.ShoppingCard;
import ir.maktab.model.entity.items.Item;
import ir.maktab.model.enums.ConfirmStatus;

import java.sql.SQLException;
import java.util.Map;

public interface ShoppingCardRepo {
    void insertShoppingCard(ShoppingCard shoppingCard) throws SQLException;

    void deleteShoppingCard(int id) throws SQLException;

    void addShoppingCardItem(int id, Map.Entry<Item, Integer> entry) throws SQLException;

    int findIDByUsername(String username, ConfirmStatus confirmStatus) throws SQLException;

    void updateTotalPrice(int id, double totalPrice) throws SQLException;
}
