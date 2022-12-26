package ir.maktab.model.repository.impl;

import ir.maktab.model.entity.ShoppingCard;
import ir.maktab.model.entity.items.Item;
import ir.maktab.model.enums.ConfirmStatus;
import ir.maktab.model.repository.ShoppingCardRepo;
import ir.maktab.util.DBhelper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ShoppingCardRepoImpl implements ShoppingCardRepo {

    private static final ShoppingCardRepoImpl instance = new ShoppingCardRepoImpl();
    private final DBhelper dBhelper = DBhelper.getInstance();

    private ShoppingCardRepoImpl() {
    }

    public static ShoppingCardRepoImpl getInstance() {
        return instance;
    }

    @Override
    public void insertShoppingCard(ShoppingCard shoppingCard) throws SQLException {
        Date today = new Date(System.currentTimeMillis());
        shoppingCard.setDate(today);
        String sql = "INSERT INTO shopping_card( username , confirm_status, date ) VALUES(?,?,?)";
        PreparedStatement preparedStatement = dBhelper.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, shoppingCard.getUser().getUsername());
        preparedStatement.setString(2, shoppingCard.getConfirmStatus().toString());
        preparedStatement.setDate(3, today);
        preparedStatement.executeUpdate();
        dBhelper.closeConnection();
    }

    @Override
    public void deleteShoppingCard(int id) throws SQLException {
        String sql = "DELETE FROM shopping_card WHERE id = ? AND confirm_status = 'PENDING'";
        PreparedStatement preparedStatement = dBhelper.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        dBhelper.closeConnection();
    }

    @Override
    public void addShoppingCardItem(int id, Map.Entry<Item, Integer> entry) throws SQLException {
        String sql = "INSERT INTO shopping_items ( shopping_card_id , item_name , item_count , item_type ) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = dBhelper.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, entry.getKey().getName());
        preparedStatement.setInt(3, entry.getValue());
        preparedStatement.setString(4, entry.getKey().getType().toString());
        preparedStatement.executeUpdate();
        dBhelper.closeConnection();
    }

    @Override
    public int findIDByUsername(String username, ConfirmStatus confirmStatus) throws SQLException {
        int resultID = 0;
        String sql = "SELECT id FROM shopping_card WHERE username = ? AND confirm_status = ?";
        PreparedStatement preparedStatement = dBhelper.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, confirmStatus.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next())
            resultID = resultSet.getInt(1);
        dBhelper.closeConnection();
        return resultID;
    }

    @Override
    public void updateTotalPrice(int id, double totalPrice) throws SQLException {
        String sql = "UPDATE shopping_card SET total_price = ? WHERE id = ?";
        PreparedStatement preparedStatement = dBhelper.getConnection().prepareStatement(sql);
        preparedStatement.setDouble(1, totalPrice);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
        dBhelper.closeConnection();
    }
}
