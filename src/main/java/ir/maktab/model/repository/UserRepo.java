package ir.maktab.model.repository;

import ir.maktab.model.entity.User;
import ir.maktab.util.DBhelper;
import ir.maktab.util.exceptions.UserNotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepo implements PersonRepo{

    private final DBhelper dbhelper = DBhelper.getInstance();
    @Override
    public boolean signIn(User user) throws SQLException {
        String sql = "SELECT * FROM user_table WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = dbhelper.getConnection().prepareStatement(sql);
        preparedStatement.setString(1,user.getUsername());
        preparedStatement.setString(2,user.getPassword());
        ResultSet resultSet = preparedStatement.executeQuery();
        if(!resultSet.next())
            throw new UserNotFoundException("Invalid Username of Password");
        dbhelper.closeConnection();
        return true;
    }

    @Override
    public User signUp(User user) {

    }

    @Override
    public boolean signOut(User user) {
        return false;
    }
}
