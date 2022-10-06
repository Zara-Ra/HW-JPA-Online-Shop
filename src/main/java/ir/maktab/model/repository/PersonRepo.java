package ir.maktab.model.repository;

import ir.maktab.model.entity.User;
import ir.maktab.util.exceptions.UserNotFoundException;
import ir.maktab.util.exceptions.UserNotSignedUpException;

import java.sql.SQLException;

public interface PersonRepo {
    void signIn(User user) throws SQLException, UserNotFoundException;

    User signUp(User user) throws SQLException, UserNotSignedUpException;
}
