package ir.maktab.model.repository.impl;

import ir.maktab.model.entity.items.Item;
import ir.maktab.model.entity.items.Readable;
import ir.maktab.model.enums.AgeRange;
import ir.maktab.model.enums.CoverType;
import ir.maktab.model.enums.ItemType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadableRepo extends AbstractItemRepo<Readable> {
    private static final ReadableRepo instance = new ReadableRepo();

    private ReadableRepo() {
    }

    public static ReadableRepo getInstance() {
        return instance;
    }

    @Override
    public List<Readable> availableItems() throws SQLException {
        String sql = "SELECT name,count,price,description,cover,age_range,num_page,type FROM item_readable WHERE count > 0";
        PreparedStatement preparedStatement = dbhelper.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Readable> items = new ArrayList<>();
        while (resultSet.next()) {
            Readable item = new Readable();
            item.setName(resultSet.getString(1));
            item.setCount(resultSet.getInt(2));
            item.setPrice(resultSet.getDouble(3));
            item.setDescription(resultSet.getString(4));
            item.setCover(CoverType.valueOf(resultSet.getString(5)));
            item.setAgeRange(AgeRange.valueOf(resultSet.getString(6)));
            item.setNumOfPage(resultSet.getInt(7));
            item.setType(ItemType.valueOf(resultSet.getString(8)));
            items.add(item);
        }
        dbhelper.closeConnection();
        return items;
    }

    @Override
    public Map<Item, Integer> findShoppingCardItems(int shoppingCardID) throws SQLException {
        Map<Item, Integer> resultShoppingItemMap = new HashMap<>();
        String sql = "SELECT S.item_count,I.name,I.price,I.description,I.cover,I.age_range,I.num_page,I.type FROM \"shopping_items\" S JOIN \"item_readable\" I ON S.item_name = I.\"name\" WHERE S.shopping_card_id = ? ";
        PreparedStatement preparedStatement = dbhelper.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, shoppingCardID);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Integer count = resultSet.getInt(1);
            String name = resultSet.getString(2);
            double price = resultSet.getDouble(3);
            String description = resultSet.getString(4);
            CoverType cover = CoverType.valueOf(resultSet.getString(5));
            AgeRange ageRange = AgeRange.valueOf(resultSet.getString(6));
            int numPage = resultSet.getInt(7);

            ItemType type = ItemType.valueOf(resultSet.getString(8));
            Item item = new Readable(type, name, price, description, 0, cover, ageRange, numPage);
            resultShoppingItemMap.put(item, count);
        }
        dbhelper.closeConnection();
        return resultShoppingItemMap;
    }
}
