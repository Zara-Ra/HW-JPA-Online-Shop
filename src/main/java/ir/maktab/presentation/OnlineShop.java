package ir.maktab.presentation;

import ir.maktab.model.entity.User;
import ir.maktab.service.UserService;
import ir.maktab.util.exceptions.DataBaseException;

public class OnlineShop {
    private final UserService userService = UserService.getInstance();

    public static void main(String[] args) {
        OnlineShop onlineShop = new OnlineShop();
        onlineShop.welcome();
    }

    public void welcome() {
        signUp();
    }

    public void signUp() {
        User user = new User(null,null,null);
        try {
            if(userService.signUp(user))
                System.out.println("Signed Up Successfully");
            else
                System.out.println("Unable to Sign Up, try again later");
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }
}
