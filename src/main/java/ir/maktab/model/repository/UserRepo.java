package ir.maktab.model.repository;

import ir.maktab.model.entity.User;
import ir.maktab.util.DBhelper;
import ir.maktab.util.exceptions.UserNotFoundException;
import ir.maktab.util.exceptions.UserNotSignedUpException;

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
    public User signUp(User user) throws SQLException {
        String sql = "INSERT INTO user_table VALUES (?,?)";
        PreparedStatement preparedStatement = dbhelper.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        if(preparedStatement.executeUpdate() == 0)
            throw new UserNotSignedUpException("Unable to Sign up");
        dbhelper.closeConnection();
        return user;
    }

    @Override
    public boolean signOut(User user) {
        return false;
    }
}
