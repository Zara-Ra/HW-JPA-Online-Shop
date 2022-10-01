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
