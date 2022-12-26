package ir.maktab.model.repository.impl;

import ir.maktab.model.entity.items.Item;
import ir.maktab.model.repository.ItemRepo;
import ir.maktab.util.DBhelper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractItemRepo<T extends Item> implements ItemRepo<T> {
    protected final DBhelper dbhelper = DBhelper.getInstance();

    @Override
    public void editCount(T item, int num) throws SQLException {
        String tableName = item.getType().toProductCategory().tableName();
        //item.getClass().getSimpleName
        String sql = "UPDATE " + tableName + " SET count = ? WHERE name = ?";
        PreparedStatement preparedStatement = dbhelper.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, num);
        preparedStatement.setString(2, item.getName());
        preparedStatement.executeUpdate();
        dbhelper.closeConnection();
    }
}
