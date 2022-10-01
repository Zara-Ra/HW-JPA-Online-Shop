package ir.maktab.presentation;

import ir.maktab.model.entity.User;
import ir.maktab.service.UserService;
import ir.maktab.util.exceptions.DataBaseException;

public class OnlineShop {
    private final UserService userService = UserService.getInstance();
    private User user;
    public static void main(String[] args) {
        OnlineShop onlineShop = new OnlineShop();
        onlineShop.welcome();
    }

    public void welcome() {
        //signUp();
        signIn();
        signOut();
    }

    private void signOut() {
        user = new User("","",null);
        userService.signOut(user);
    }

    private boolean signIn() {
        boolean signInResult = false;
        user = new User("Hoda","Rahimi",null);
        //Validate username & password
        try {
            if(userService.signIn(user)) {
                System.out.println("Signed In Successfully");
                signInResult = true;
            }
            else {
                System.out.println("Unable to Sign In, try again later");
            }
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }finally {
            return signInResult;
        }
    }

    public boolean signUp() {
        boolean signUpResult = false;
        user = new User("Hoda","Rahimi",null);
        //Validate username & password
        try {
            if(userService.signUp(user)) {
                System.out.println("Signed Up Successfully");
                signUpResult = true;
            }
            else {
                System.out.println("Unable to Sign Up, try again later");
            }
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }finally {
            return signUpResult;
        }
    }
}
