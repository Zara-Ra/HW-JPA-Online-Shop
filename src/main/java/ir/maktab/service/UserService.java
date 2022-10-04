package ir.maktab.service;

import ir.maktab.model.entity.ShoppingCard;
import ir.maktab.model.entity.User;
import ir.maktab.model.entity.items.Item;
import ir.maktab.model.enums.ConfirmStatus;
import ir.maktab.model.repository.*;
import ir.maktab.util.exceptions.DataBaseException;
import ir.maktab.util.exceptions.ShoppingCardNotFound;
import ir.maktab.util.exceptions.UserNotFoundException;
import ir.maktab.util.exceptions.UserNotSignedUpException;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

public class UserService implements PersonService {
    private UserService() {
    }

    private static final UserService instance = new UserService();

    public static UserService getInstance() {
        return instance;
    }

    private final UserRepo userRepo = UserRepo.getInstance();
    private final ShoppingCardRepo shoppingCardRepo = ShoppingCardRepo.getInstance();
    ElectronicsRepo electronicsRepo = ElectronicsRepo.getInstance();
    ReadableRepo readableRepo = ReadableRepo.getInstance();
    ShoesRepo shoesRepo = ShoesRepo.getInstance();


    @Override
    public boolean signIn(User user) {
        checkUser(user);
        try {
            userRepo.signIn(user);
            return true;
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());//TODO what kind of exception could i handel here?
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
            throw new DataBaseException("This Username Already Exists");
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

    public ShoppingCard findShoppingCard(User user) {
        ShoppingCard result = new ShoppingCard(user,ConfirmStatus.PENDING);
        int shoppingCardID = 0;
        try {
            shoppingCardID = shoppingCardRepo.findID(user);
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } catch (ShoppingCardNotFound e) {
            return result;
        }
        Map<Item, Integer> shoppingItemsMap = null;
        try {
            shoppingItemsMap.putAll(Collections.unmodifiableMap(electronicsRepo.findShoppingCardItems(shoppingCardID)));
            shoppingItemsMap.putAll(Collections.unmodifiableMap(shoesRepo.findShoppingCardItems(shoppingCardID)));
            shoppingItemsMap.putAll(Collections.unmodifiableMap(readableRepo.findShoppingCardItems(shoppingCardID)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        result.setShoppingItemsMap(shoppingItemsMap);
        return result;
    }
}
