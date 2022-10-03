package ir.maktab.model.repository;

import ir.maktab.model.entity.items.Electronics;
import ir.maktab.model.entity.items.Readable;
import ir.maktab.model.enums.AgeRange;
import ir.maktab.model.enums.CoverType;
import ir.maktab.model.enums.ItemType;
import ir.maktab.util.DBhelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadableRepo extends AbstractItemRepo<Readable> {
    private ReadableRepo(){}
    private static final ReadableRepo instance = new ReadableRepo();
    public static ReadableRepo getInstance(){
        return instance;
    }
    private DBhelper dBhelper = DBhelper.getInstance();
    @Override
    public List<Readable> availableItems() throws SQLException {
        String sql = "SELECT name,count,price,description,cover,age_range,num_page,type FROM item_readable WHERE count > 0";//todo
        PreparedStatement preparedStatement = dBhelper.getConnection().prepareStatement(sql);
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
        return items;
    }
}
