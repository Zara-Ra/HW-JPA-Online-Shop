package ir.maktab.model.repository;

import ir.maktab.model.entity.items.Electronics;
import ir.maktab.model.entity.items.Item;
import ir.maktab.model.entity.items.TV;
import ir.maktab.util.DBhelper;
import ir.maktab.util.GenericList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ElectronicsRepo<T extends Electronics> implements ItemRepo<T> {
    private ElectronicsRepo(){}//TODO Singelton is this wrong?
    private static final ElectronicsRepo instance = new ElectronicsRepo<>();
    public static ElectronicsRepo getInstance(){
        return instance;
    }

    private final DBhelper dbhelper = DBhelper.getInstance();
    @Override
    public List<T> availableItems(T item) throws SQLException {
        String sql = "SELECT name,count,price,description,model,inch,color FROM item_electronics WHERE count > 0";
        PreparedStatement preparedStatement = dbhelper.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<T> items = new ArrayList<>();
        while (resultSet.next()){
            item.setName(resultSet.getString(1));
            item.setCount(resultSet.getInt(2));
            item.setPrice(resultSet.getDouble(3));
            item.setDescription(resultSet.getString(4));
            item.setModel(resultSet.getString(5));
            int inch = resultSet.getInt(6);
            String color = resultSet.getString(7);
            items.add(item);
        }
        return items;
    }
    @Override
    public boolean editCount(T item, int num) throws SQLException {
        String sql = "UPDATE item_electronics SET count = ? WHERE name = ?";
        PreparedStatement preparedStatement = dbhelper.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1,num);
        preparedStatement.setString(2,item.getName());
        return preparedStatement.executeUpdate() > 0;
    }

    @Override
    public T findItemByName(String name) {
        return null;
    }

    @Override
    public T findItemByID(int ID) {
        return null;
    }
}
