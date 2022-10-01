package ir.maktab.model.repository;

import ir.maktab.model.entity.User;

import java.sql.SQLException;

public interface PersonRepo {
    boolean signIn(User user) throws SQLException;
    User signUp(User user) throws SQLException;
}
