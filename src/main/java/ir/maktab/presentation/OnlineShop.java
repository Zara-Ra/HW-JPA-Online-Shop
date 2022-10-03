package ir.maktab.presentation;

import ir.maktab.model.entity.User;
import ir.maktab.model.enums.ItemType;
import ir.maktab.service.ItemService;
import ir.maktab.service.UserService;

import java.util.Scanner;

import static java.lang.System.exit;

public class OnlineShop {
    private final UserService userService = UserService.getInstance();
    private final ItemService itemService = ItemService.getInstance();
    private Scanner scanner = new Scanner(System.in);
    private User user;

    public void welcome() {
        System.out.println("Press 1  --> SignIn");
        System.out.println("Press 2  --> SignUp");
        System.out.println("Press 3  --> SignOut");
        System.out.println("Press any Key to Exit");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                if (signIn())
                    secondMenu();
                else
                    welcome();
                break;
            case 2:
                if (signUp())
                    secondMenu();
                else
                    welcome();
                break;
            case 3:
                signOut();
                welcome();
                break;
            case 4:
                exit(0);
                break;
        }
    }

    private void secondMenu() {
        System.out.println("Press 1 --> See All Items");
        System.out.println("Press 2 --> Add Item to Shopping Card");
        System.out.println("Press 3 --> Delete Item from Shopping Card");
        System.out.println("Press 4 --> View Shopping Card");
        System.out.println("Press 5 --> Confirm Purchase");
        System.out.println("Press 6 --> Back");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                printAvailableItems();
                secondMenu();
                break;
            case 2:
                addItemToShoppingCard();
                secondMenu();
                break;
            case 3:
                //deleteItemFromShoppingCard();
                secondMenu();
                break;
            case 4:
                //printShoppingCard();
                secondMenu();
                break;
            case 5:
                //confirmPurchase();
                secondMenu();
                break;
            case 6:
                welcome();
                break;
        }
        //printAvailableItems();
        //searchItem();
    }

    private void addItemToShoppingCard() {
        System.out.println("Press 1 --> Electronics");
        System.out.println("Press 2 --> Readables");
        System.out.println("Press 3 --> Shoes");
        System.out.println("Press 4 --> Back");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                secondMenu();
                break;
        }
    }
    private void printItemsByType(ItemType type){
        System.out.println(itemService.availableItems(type));
    }
    private void printAvailableItems() {
        System.out.println(itemService.availableItems());//todo can implement it in a way that only one type of products can be seen
    }

    private void signOut() {
        userService.signOut(user);
    }

    private boolean signIn() {
        boolean signInResult = false;
        user = new User("Hoda", "Rahimi", null);
        //Validate username & password
        try {
            if (userService.signIn(user)) {
                System.out.println("Signed In Successfully");
                signInResult = true;
            } else {
                System.out.println("Unable to Sign In, try again later");
            }
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        } finally {
            return signInResult;
        }
    }

    public boolean signUp() {
        boolean signUpResult = false;
        user = new User("Hoda", "Rahimi", null);
        //Validate username & password
        try {
            if (userService.signUp(user)) {
                System.out.println("Signed Up Successfully");
                signUpResult = true;
            } else {
                System.out.println("Unable to Sign Up, try again later");
            }
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        } finally {
            return signUpResult;
        }
    }
}
