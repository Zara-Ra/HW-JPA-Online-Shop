package ir.maktab.model.repository.Impl;

import ir.maktab.model.entity.items.Electronics;
import ir.maktab.model.entity.items.Item;
import ir.maktab.model.enums.ItemType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElectronicsRepo extends AbstractItemRepo<Electronics> {
    private ElectronicsRepo() {
    }

    private static final ElectronicsRepo instance = new ElectronicsRepo();

    public static ElectronicsRepo getInstance() {
        return instance;
    }

    @Override
    public List<Electronics> availableItems() throws SQLException {
        String sql = "SELECT name,count,price,description,brand,type FROM item_electronics WHERE count > 0";
        PreparedStatement preparedStatement = dbhelper.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Electronics> items = new ArrayList<>();
        while (resultSet.next()) {
            Electronics item = new Electronics();
            item.setName(resultSet.getString(1));
            item.setCount(resultSet.getInt(2));
            item.setPrice(resultSet.getDouble(3));
            item.setDescription(resultSet.getString(4));
            item.setBrand(resultSet.getString(5));
            item.setType(ItemType.valueOf(resultSet.getString(6)));
            items.add(item);
        }
        dbhelper.closeConnection();
        return items;
    }

    public Map<Item, Integer> findShoppingCardItems(int shoppingCardID) throws SQLException {
        Map<Item, Integer> resultShoppingItemMap = new HashMap<>();
        String sql = "SELECT S.item_count,I.name,I.price,I.description,I.brand,I.type FROM \"shopping_items\" S JOIN \"item_electronics\" I ON S.item_name = I.\"name\" WHERE S.shopping_card_id = ? ";
        PreparedStatement preparedStatement = dbhelper.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, shoppingCardID);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Integer count = resultSet.getInt(1);
            String name = resultSet.getString(2);
            double price = resultSet.getDouble(3);
            String description = resultSet.getString(4);
            String brand = resultSet.getString(5);
            ItemType type = ItemType.valueOf(resultSet.getString(6));
            Item item = new Electronics(type, name, price, description, 0, brand);
            resultShoppingItemMap.put(item, count);
        }
        dbhelper.closeConnection();
        return resultShoppingItemMap;
    }
}
