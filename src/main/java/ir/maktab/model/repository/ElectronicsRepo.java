package ir.maktab.model.repository;

import ir.maktab.model.entity.items.Electronics;
import ir.maktab.util.DBhelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ElectronicsRepo extends AbstractItemRepo<Electronics> {
    private ElectronicsRepo(){}//TODO Singelton is this wrong?
    private static final ElectronicsRepo instance = new ElectronicsRepo();
    public static ElectronicsRepo getInstance(){
        return instance;
    }

    private DBhelper dBhelper = DBhelper.getInstance();
    @Override
    public List<Electronics> availableItems() throws SQLException {
        String sql = "SELECT name,count,price,description,model FROM item_electronics WHERE count > 0";//todo
        PreparedStatement preparedStatement = dBhelper.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Electronics> items = new ArrayList<>();
        while (resultSet.next()) {
            Electronics item = new Electronics();
            item.setName(resultSet.getString(1));
            item.setCount(resultSet.getInt(2));
            item.setPrice(resultSet.getDouble(3));
            item.setDescription(resultSet.getString(4));
            item.setModel(resultSet.getString(5));
            //int inch = resultSet.getInt(6);
            //String color = resultSet.getString(7);
            items.add(item);
        }
        return items;
    }
}
