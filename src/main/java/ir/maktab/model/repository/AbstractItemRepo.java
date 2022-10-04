package ir.maktab.model.repository;

import ir.maktab.model.entity.items.Item;
import ir.maktab.util.DBhelper;
import ir.maktab.util.exceptions.ItemUnavailableException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractItemRepo<T extends Item> implements ItemRepo<T> {
    private final DBhelper dbhelper = DBhelper.getInstance();
    @Override
    public boolean editCount(T item, int num) throws SQLException {
        String tableName = item.getType().toPrdoductCategory().tableName();
        String sql = "UPDATE "+tableName+" SET count = ? WHERE name = ?";
        PreparedStatement preparedStatement = dbhelper.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, num);
        preparedStatement.setString(2, item.getName());
        return preparedStatement.executeUpdate() > 0;
    }










    @Override
    public T findItemByName(T item) throws SQLException, ItemUnavailableException {
        String sql = "SELECT count,price,description FROM item WHERE name = ?";
        PreparedStatement preparedStatement = dbhelper.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, item.getName());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            throw new ItemUnavailableException("This Item is Currently Unavailable");
        }
        item.setCount(resultSet.getInt(1));
        item.setPrice(resultSet.getDouble(2));
        item.setDescription(resultSet.getString(3));
        return item;
    }
}
