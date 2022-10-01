package ir.maktab.model.repository;

import ir.maktab.model.entity.User;
import ir.maktab.util.DBhelper;
import ir.maktab.util.exceptions.UserNotFoundException;
import ir.maktab.util.exceptions.UserNotSignedUpException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepo implements PersonRepo{

    private UserRepo(){}
    private static final UserRepo instance = new UserRepo();
    public static UserRepo getInstance(){
        return instance;
    }
    private final DBhelper dbhelper = DBhelper.getInstance();
    @Override
    public void signIn(User user) throws SQLException {
        String sql = "SELECT * FROM user_table WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = dbhelper.getConnection().prepareStatement(sql);
        preparedStatement.setString(1,user.getUsername());
        preparedStatement.setString(2,user.getPassword());
        ResultSet resultSet = preparedStatement.executeQuery();
        if(!resultSet.next())
            throw new UserNotFoundException("Invalid Username of Password");
        dbhelper.closeConnection();
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
}
