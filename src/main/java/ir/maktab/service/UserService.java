package ir.maktab.service;

import ir.maktab.model.entity.User;
import ir.maktab.model.repository.UserRepo;
import ir.maktab.util.exceptions.UserNotFoundException;
import ir.maktab.util.exceptions.UserNotSignedUpException;

import java.sql.SQLException;

public class UserService implements PersonService {
    private UserService(){}
    private static final UserService instance = new UserService();
    public static UserService getInstance(){
        return instance;
    }
    private final UserRepo userRepo = UserRepo.getInstance();
    @Override
    public boolean signIn(User user) {
        try{
            userRepo.signIn(user);
            return true;
        } catch (SQLException e) {  //TODO what should i do with sqlexception
            throw new RuntimeException(e);
        }
        catch (UserNotFoundException unfe){
            return false;
        }
    }

    @Override
    public User signUp(User user) {
        try{
            userRepo.signUp(user);
            return user;
        } catch (SQLException | UserNotSignedUpException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean signOut(User user) {
        user = null;
        return true;
    }
}
