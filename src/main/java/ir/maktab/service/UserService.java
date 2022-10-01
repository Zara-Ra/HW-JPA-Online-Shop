package ir.maktab.service;

import ir.maktab.model.entity.User;
import ir.maktab.model.repository.UserRepo;
import ir.maktab.util.exceptions.DataBaseException;
import ir.maktab.util.exceptions.UserNotFoundException;
import ir.maktab.util.exceptions.UserNotSignedUpException;

import java.sql.SQLException;

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
        try {
            userRepo.signIn(user);
            return true;
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean signUp(User user) {
        if(user == null)
            throw new NullPointerException("The User Can not be null");
        if(user.getUsername() == null)
            throw new IllegalArgumentException("Username Can not be empty");
        if(user.getPassword() == null)
            throw new IllegalArgumentException("Password Can not be empty");
        try {
            userRepo.signUp(user);
            return true;
        } catch (SQLException e) {
            throw new DataBaseException("This Username Already Exists");
        } catch (UserNotSignedUpException e) {
            return false;
        }
    }

    @Override
    public boolean signOut(User user) {
        user = null;
        return true;
    }
}
