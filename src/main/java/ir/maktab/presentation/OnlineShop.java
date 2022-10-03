package ir.maktab.presentation;

import ir.maktab.model.entity.ShoppingCard;
import ir.maktab.model.entity.User;
import ir.maktab.model.entity.items.Item;
import ir.maktab.model.enums.ProductCategory;
import ir.maktab.service.ItemService;
import ir.maktab.service.ShoppingCardService;
import ir.maktab.service.UserService;
import ir.maktab.util.exceptions.ShoppingCardFullExcepiton;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.exit;

public class OnlineShop {
    private final UserService userService = UserService.getInstance();
    private final ItemService itemService = ItemService.getInstance();
    private final ShoppingCardService shoppingCardService = ShoppingCardService.getInstance();
    private Scanner scanner = new Scanner(System.in);
    private User user;

    public void welcome() {
        System.out.println("---------------------------------------------");
        System.out.println("Press 1  --> SignIn");
        System.out.println("Press 2  --> SignUp");
        System.out.println("Press 3  --> SignOut");
        System.out.println("Press any Key to Exit");
        System.out.println("---------------------------------------------");
        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            exit(0);
        }
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
            default:
                exit(0);
                break;
        }
    }

    private void secondMenu() {
        System.out.println("---------------------------------------------");
        System.out.println("Press 1 --> See All Items");
        System.out.println("Press 2 --> Add Item to Shopping Card");
        System.out.println("Press 3 --> Delete Item from Shopping Card");
        System.out.println("Press 4 --> View Shopping Card");
        System.out.println("Press 5 --> Confirm Purchase");
        System.out.println("Press 6 --> Back");
        System.out.println("---------------------------------------------");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                printAllItems();
                secondMenu();
                break;
            case 2:
                shop();
                secondMenu();
                break;
            case 3:
                deleteItemFromShoppingCard();
                //decreaseItemNumFromShoppingCard();
                secondMenu();
                break;
            case 4:
                printShoppingCard();
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

    private void printShoppingCard() {
        Map<Item, Integer> shoppingItemMap = user.getShoppingCard().getShoppingItemsMap();
        if (shoppingItemMap.size() == 0) {
            System.out.println("Shopping Card is Empty");
        } else {
            for (Map.Entry<Item, Integer> entry : shoppingItemMap.entrySet()) {
                System.out.println("Number of Item: " + entry.getValue() + entry.getKey());
            }
        }
    }

    private void deleteItemFromShoppingCard() {
        Map<Item, Integer> shoppingItemMap = user.getShoppingCard().getShoppingItemsMap();
        if (shoppingItemMap.size() == 0) {
            System.out.println("Shopping Card is Empty");
        } else {
            for (Iterator<Map.Entry<Item, Integer>> it = shoppingItemMap.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<Item, Integer> entry = it.next();
                System.out.println("Number of Item: " + entry.getValue() + entry.getKey());
                System.out.println("Press 1 --> Delete Item");
                System.out.println("Press 2 --> Decrease the Number of Item");
                System.out.println("Press 3 --> Next Item");
                System.out.println("Press 4 --> Back");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        shoppingCardService.removeItem(it);
                        break;
                    case 2:
                        int validNumItem = entry.getValue() - 1;
                        if (validNumItem == 0)
                            shoppingCardService.removeItem(it);
                        else if (validNumItem == 1)
                            shoppingCardService.editNumOfItem(shoppingItemMap, entry.getKey(), 1);
                        else {
                            System.out.println("How many Items do you need? ( 1 to " + validNumItem + " )");
                            int numOfItems = Integer.parseInt(scanner.nextLine());
                            while (numOfItems <= 0 || numOfItems > validNumItem) {
                                System.out.println("Enter a value between 1 and " + validNumItem);
                                numOfItems = Integer.parseInt(scanner.nextLine());
                            }
                            shoppingCardService.editNumOfItem(shoppingItemMap, entry.getKey(), numOfItems);
                        }
                        break;
                    case 3:
                        break;
                    case 4:
                        secondMenu();
                        break;
                }

            }
        }
    }

    private void shop() {
        System.out.println("---------------------------------------------");
        System.out.println("Press 1 --> Electronics");
        System.out.println("Press 2 --> Readables");
        System.out.println("Press 3 --> Shoes");
        System.out.println("Press 4 --> Back");
        System.out.println("---------------------------------------------");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                addItemToShoppingCard(ProductCategory.ELECTRONICS);
                shop();
                break;
            case 2:
                addItemToShoppingCard(ProductCategory.READABLE);
                shop();
                break;
            case 3:
                addItemToShoppingCard(ProductCategory.SHOES);
                shop();
                break;
            case 4:
                secondMenu();
                break;
        }
    }

    private void addItemToShoppingCard(ProductCategory productCategory) {
        List<Item> itemList = itemsByCategory(productCategory);
        printItems(itemList);
        System.out.println("Enter The Item Number to Add: ");
        int itemNum;
        try {
            itemNum = Integer.parseInt(scanner.nextLine());
            if(itemNum<0 || itemNum >= itemList.size()){
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid Number Entered");
            return;
        }
        try {
            shoppingCardService.addItem(user, itemList.get(itemNum));
        } catch (ShoppingCardFullExcepiton e) {
            System.err.println(e.getMessage());
        }
    }

    private List<Item> itemsByCategory(ProductCategory productCategory) {
        List<Item> itemList = itemService.itemsByCategory(productCategory);
        return itemList;
    }

    private void printItems(List<Item> itemList) {
        for (int j = 0; j < itemList.size(); j++) {
            System.out.println(j + " " + itemList.get(j));
        }
    }

    private void printAllItems() {
        for (ProductCategory pc : ProductCategory.values()) {
            System.out.println("--- " + pc + " -----------------------------------------------------------------------------------------------------------------------------");
            printItems(itemsByCategory(pc));
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    private void signOut() {
        //TODO should call confirmPurchase also to add the shoppingcard to the database for future access
        userService.signOut(user);
    }

    private boolean signIn() {//TODO handel ShoppingCard , read from table Shoppingcard
        boolean signInResult = false;
        user = new User("Hoda", "Rahimi", new ShoppingCard());
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
        user = new User("Hoda", "Rahimi", new ShoppingCard());
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
