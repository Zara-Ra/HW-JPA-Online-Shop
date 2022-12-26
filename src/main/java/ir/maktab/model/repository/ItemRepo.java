package ir.maktab.model.repository;

import ir.maktab.model.entity.items.Item;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ItemRepo<T extends Item> {

    List<T> availableItems() throws SQLException;

    void editCount(T item, int num) throws SQLException;

    Map<Item, Integer> findShoppingCardItems(int shoppingCardID) throws SQLException;

}
