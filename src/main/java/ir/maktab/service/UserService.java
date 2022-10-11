package ir.maktab.service;

import ir.maktab.model.entity.ShoppingCard;
import ir.maktab.model.entity.User;
import ir.maktab.model.entity.items.Item;
import ir.maktab.model.enums.ConfirmStatus;
import ir.maktab.model.repository.*;
import ir.maktab.util.exceptions.UserNotFoundException;
import ir.maktab.util.exceptions.UserNotSignedUpException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserService implements PersonService {
    private UserService() {
    }

    private static final UserService instance = new UserService();

    public static UserService getInstance() {
        return instance;
    }

    private final UserRepo userRepo = UserRepo.getInstance();

    @Override
    public boolean signIn(User user) {
        checkUser(user);
        try {
            userRepo.signIn(user);
            return true;
        } catch (SQLException e) {
            System.err.println("DataBase Error, Sign in");
            return false;
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean signUp(User user) {
        checkUser(user);
        try {
            userRepo.signUp(user);
            return true;
        } catch (SQLException e) {
            System.err.println("DataBase Error, Sign up");
            return false;
        } catch (UserNotSignedUpException e) {
            return false;
        }
    }

    private void checkUser(User user) {
        if (user == null)
            throw new NullPointerException("The User Can not be null");
        if (user.getUsername() == null)
            throw new IllegalArgumentException("Username Can not be empty");
        if (user.getPassword() == null)
            throw new IllegalArgumentException("Password Can not be empty");
    }

    @Override
    public User signOut(User user) {
        return null;
    }

}
