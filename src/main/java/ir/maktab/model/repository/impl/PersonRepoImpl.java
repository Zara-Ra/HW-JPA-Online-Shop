package ir.maktab.model.repository.impl;

import ir.maktab.model.entity.User;
import ir.maktab.model.repository.PersonRepo;
import ir.maktab.util.DBhelper;
import ir.maktab.util.exceptions.UserNotFoundException;
import ir.maktab.util.exceptions.UserNotSignedUpException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRepoImpl implements PersonRepo {

    private static final PersonRepoImpl instance = new PersonRepoImpl();
    private final DBhelper dbhelper = DBhelper.getInstance();

    private PersonRepoImpl() {
    }

    public static PersonRepoImpl getInstance() {
        return instance;
    }

    @Override
    public void signIn(User user) throws SQLException, UserNotFoundException {
        String sql = "SELECT * FROM user_table WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = dbhelper.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next())
            throw new UserNotFoundException("Invalid Username or Password");
        dbhelper.closeConnection();
    }

    @Override
    public User signUp(User user) throws SQLException, UserNotSignedUpException {
        String sql = "INSERT INTO user_table VALUES (?,?)";
        PreparedStatement preparedStatement = dbhelper.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        if (preparedStatement.executeUpdate() == 0)
            throw new UserNotSignedUpException("Unable to Sign up");
        dbhelper.closeConnection();
        return user;
    }
}
