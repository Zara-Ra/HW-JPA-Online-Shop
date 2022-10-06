package ir.maktab.model.repository;

import ir.maktab.model.entity.items.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemRepo<T extends Item> {

    List<T> availableItems() throws SQLException;

    boolean editCount(T item, int num) throws SQLException;

}
