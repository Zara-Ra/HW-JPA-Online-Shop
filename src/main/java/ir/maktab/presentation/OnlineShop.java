package ir.maktab.presentation;

import ir.maktab.model.entity.User;
import ir.maktab.model.entity.items.TV;
import ir.maktab.service.UserService;

public class OnlineShop {
    private final UserService userService = UserService.getInstance();
    public static void main(String[] args) {
        OnlineShop onlineShop = new OnlineShop();
        onlineShop.welcome();
    }
    public void welcome(){
        User user = new User("Zahra","Password",null);
        System.out.println(userService.signUp(user));
    }
}
