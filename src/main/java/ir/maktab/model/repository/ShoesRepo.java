package ir.maktab.model.repository;

import ir.maktab.model.entity.items.Electronics;
import ir.maktab.model.entity.items.Readable;
import ir.maktab.model.entity.items.Shoes;
import ir.maktab.model.enums.Color;
import ir.maktab.model.enums.Gender;
import ir.maktab.model.enums.ItemType;
import ir.maktab.util.DBhelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShoesRepo extends AbstractItemRepo<Shoes> {
    private ShoesRepo(){}
    private static final ShoesRepo instance = new ShoesRepo();
    private DBhelper dBhelper = DBhelper.getInstance();
    public static ShoesRepo getInstance(){
        return instance;
    }

    @Override
    public List<Shoes> availableItems() throws SQLException {
        String sql = "SELECT name,count,price,description,color,gender,size,type FROM item_shoes WHERE count > 0";//todo
        PreparedStatement preparedStatement = dBhelper.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Shoes> items = new ArrayList<>();
        while (resultSet.next()) {
            Shoes item = new Shoes();
            item.setName(resultSet.getString(1));
            item.setCount(resultSet.getInt(2));
            item.setPrice(resultSet.getDouble(3));
            item.setDescription(resultSet.getString(4));
            item.setColor(Color.valueOf(resultSet.getString(5)));
            item.setGender(Gender.valueOf(resultSet.getString(6)));
            item.setSize(resultSet.getInt(7));
            item.setType(ItemType.valueOf(resultSet.getString(8)));
            items.add(item);
        }
        return items;
    }
}
