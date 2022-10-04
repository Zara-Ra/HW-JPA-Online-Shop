package ir.maktab.model.repository;

import ir.maktab.model.entity.ShoppingCard;
import ir.maktab.model.entity.User;
import ir.maktab.model.entity.items.Item;
import ir.maktab.util.DBhelper;
import ir.maktab.util.exceptions.ShoppingCardNotFound;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCardRepo {

    private ShoppingCardRepo() {
    }

    private static final ShoppingCardRepo instance = new ShoppingCardRepo();

    public static ShoppingCardRepo getInstance() {
        return instance;
    }

    private DBhelper dBhelper = DBhelper.getInstance();

    public boolean addItem() {//unnecessary
        return true;
    }

    public boolean deleteItem() {//unnecessary
        return true;
    }

    public List<Item> allItems() {
        return new ArrayList<>();
    }

    public boolean confirmShopping(ShoppingCard shoppingCard) throws SQLException {
        Date today = new Date(System.currentTimeMillis());
        shoppingCard.setDate(today);
        String sql = "INSERT INTO shopping_card( username , confirm_status, date ) VALUES(?,?,?)";
        PreparedStatement preparedStatement = dBhelper.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, shoppingCard.getUser().getUsername());
        preparedStatement.setString(2, shoppingCard.getConfirmStatus().toString());
        preparedStatement.setDate(3, today);
        return preparedStatement.executeUpdate() > 0;
    }

    public int getID(ShoppingCard shoppingCard) throws SQLException {
        String sql = "SELECT id FROM shopping_card WHERE username = ? AND date = ?";
        PreparedStatement preparedStatement = dBhelper.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, shoppingCard.getUser().getUsername());
        preparedStatement.setDate(2, shoppingCard.getDate());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next())
            return 0;
        return resultSet.getInt(1);
    }

    public void addShoppingCardItem(int id, Map.Entry<Item, Integer> entry) throws SQLException {
        String sql = "INSERT INTO shopping_items ( shopping_card_id , item_name , item_count , item_type ) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = dBhelper.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, entry.getKey().getName());
        preparedStatement.setInt(3, entry.getValue());
        preparedStatement.setString(4, entry.getKey().getType().toString());
        preparedStatement.executeUpdate();

    }

    public int findID(User user) throws SQLException, ShoppingCardNotFound {
        String sql = "SELECT id FROM shopping_card WHERE username = ? AND confirm_status = 'PENDING'";
        PreparedStatement preparedStatement = dBhelper.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, user.getUsername());
        ResultSet resultSet = preparedStatement.executeQuery();
        if(!resultSet.next())
            throw new ShoppingCardNotFound("You dont have any Pending Shopping Cards");
        return resultSet.getInt(1);
    }
}
