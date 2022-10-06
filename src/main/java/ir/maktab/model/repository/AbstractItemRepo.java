package ir.maktab.model.repository;

import ir.maktab.model.entity.items.Item;
import ir.maktab.util.DBhelper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractItemRepo<T extends Item> implements ItemRepo<T> {
    private final DBhelper dbhelper = DBhelper.getInstance();

    @Override
    public boolean editCount(T item, int num) throws SQLException {
        String tableName = item.getType().toPrdoductCategory().tableName();
        String sql = "UPDATE " + tableName + " SET count = ? WHERE name = ?";
        PreparedStatement preparedStatement = dbhelper.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, num);
        preparedStatement.setString(2, item.getName());
        return preparedStatement.executeUpdate() > 0;
    }
}
