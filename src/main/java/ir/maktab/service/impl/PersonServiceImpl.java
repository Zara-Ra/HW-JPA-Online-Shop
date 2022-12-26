package ir.maktab.service.impl;

import ir.maktab.model.entity.User;
import ir.maktab.model.repository.impl.PersonRepoImpl;
import ir.maktab.service.PersonService;
import ir.maktab.util.exceptions.UserNotFoundException;
import ir.maktab.util.exceptions.UserNotSignedUpException;

import java.sql.SQLException;

public class PersonServiceImpl implements PersonService {
    private static final PersonServiceImpl instance = new PersonServiceImpl();
    private final PersonRepoImpl personRepoImpl = PersonRepoImpl.getInstance();

    private PersonServiceImpl() {
    }

    public static PersonServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean signIn(User user) {
        checkUser(user);
        try {
            personRepoImpl.signIn(user);
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
            personRepoImpl.signUp(user);
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
