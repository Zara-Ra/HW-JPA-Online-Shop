package ir.maktab.presentation;

import ir.maktab.model.entity.ShoppingCard;
import ir.maktab.model.entity.User;
import ir.maktab.model.entity.items.Item;
import ir.maktab.model.enums.ConfirmStatus;
import ir.maktab.model.enums.ProductCategory;
import ir.maktab.service.ItemService;
import ir.maktab.service.ShoppingCardService;
import ir.maktab.service.UserService;
import ir.maktab.util.exceptions.ItemUnavailableException;
import ir.maktab.util.exceptions.ShoppingCardFullExcepiton;

import java.util.*;

import static java.lang.System.exit;

public class OnlineShop {
    private final UserService userService = UserService.getInstance();
    private final ItemService itemService = ItemService.getInstance();
    private final ShoppingCardService shoppingCardService = ShoppingCardService.getInstance();
    private Scanner scanner = new Scanner(System.in);
    private User user;
    //private List<Item> shopItemList = new ArrayList<>();
    private Map<ProductCategory,List<Item>> shopItems = new HashMap<>();
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
                if(user != null){
                    System.out.println("Please Sign Out First");
                    welcome();
                    break;
                }
                if (signIn())
                    secondMenu();
                else{
                    System.out.println("Wrong username password");
                    welcome();
                }
                break;
            case 2:
                if(user != null){
                    System.out.println("Please Sign Out First");
                    welcome();
                    break;
                }
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
                secondMenu();
                break;
            case 4:
                printShoppingCard();
                secondMenu();
                break;
            case 5:
                confirmPurchase(ConfirmStatus.CONFIRMED);
                secondMenu();
                break;
            case 6:
                welcome();
                break;
        }
    }

    private void confirmPurchase(ConfirmStatus confirmStatus) {
        user.getShoppingCard().setConfirmStatus(confirmStatus);
        shoppingCardService.confirmShopping(user.getShoppingCard());
        if (confirmStatus.equals(ConfirmStatus.CONFIRMED)) {
            itemService.updateShopItemsCount(user.getShoppingCard(),shopItems);
        }
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
                Integer deletedNumOfItems = 1;
                switch (choice) {
                    case 1:
                        deletedNumOfItems = entry.getValue();
                        itemService.increaseShopItemsCount(shopItems,entry.getKey(),deletedNumOfItems);
                        shoppingCardService.removeItem(it);
                        break;
                    case 2:
                        int validNumItem = entry.getValue() - 1;
                        if (validNumItem == 0) {
                            itemService.increaseShopItemsCount(shopItems, entry.getKey(),deletedNumOfItems);
                            shoppingCardService.removeItem(it);
                        }
                        else if (validNumItem == 1) {
                            itemService.increaseShopItemsCount(shopItems, entry.getKey(),deletedNumOfItems);
                            shoppingCardService.editNumOfItem(shoppingItemMap, entry.getKey(), 1);
                        }
                        else {
                            System.out.println("How many Items do you need? ( 1 to " + validNumItem + " )");
                            int numOfItems = Integer.parseInt(scanner.nextLine());
                            while (numOfItems <= 0 || numOfItems > validNumItem) {
                                System.out.println("Enter a value between 1 and " + validNumItem);
                                numOfItems = Integer.parseInt(scanner.nextLine());
                            }
                            deletedNumOfItems = entry.getValue() - numOfItems;
                            itemService.increaseShopItemsCount(shopItems, entry.getKey(),deletedNumOfItems);
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
        int itemNum = 0;
        try {
            itemNum = Integer.parseInt(scanner.nextLine());
            if (itemNum < 0 || itemNum >= itemList.size()) {
                throw new NumberFormatException();
            }
            if(itemList.get(itemNum).getCount() == 0){
                throw new ItemUnavailableException("No More of this Item is Available");
            }
            shoppingCardService.addItem(user, itemList.get(itemNum));
        } catch (NumberFormatException e) {
            System.err.println("Invalid Number Entered");
        } catch (ItemUnavailableException e){
            System.err.println(e.getMessage());
        }
         catch (ShoppingCardFullExcepiton e) {
            System.err.println(e.getMessage());
        }
    }

    private List<Item> itemsByCategory(ProductCategory productCategory) {
        if(!shopItems.containsKey(productCategory)) {
             shopItems.put(productCategory ,itemService.itemsByCategory(productCategory));
        }
        return shopItems.get(productCategory);
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
        System.out.println("---------------------------------------------");
        System.out.println("Press 1 --> Confirm Purchase Before Sign Out");
        System.out.println("Press 2 --> Empty Shopping Card and Sign Out");
        System.out.println("Press 3 --> Save Shopping Card For Later Purchase and Sign Out");
        System.out.println("Press 4 --> Back");
        System.out.println("---------------------------------------------");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice){
            case 1:
                signOutHelper(ConfirmStatus.CONFIRMED);
                break;
            case 2:
                signOutHelper(ConfirmStatus.NOTCONFIRMED);
                break;
            case 3:
                signOutHelper(ConfirmStatus.PENDING);
                break;
            case 4:
                secondMenu();
                break;
        }
    }

    private void signOutHelper(ConfirmStatus confirmed) {
        confirmPurchase(confirmed);
        user = userService.signOut(user);
        shopItems.clear();
    }

    private boolean signIn() {
        boolean signInResult = false;
        //Validate username & password
        System.out.println("Username: ");
        String username = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();
        user = new User(username,password);
        try {
            if (userService.signIn(user)) {
                // todo user.getShoppingCard().setUser(user);
                ShoppingCard shoppingCard = userService.findShoppingCard(user);
                //user.getShoppingCard().setConfirmStatus(ConfirmStatus.PENDING);
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
        //TODO Validate username & password
        System.out.println("Username: ");
        String username = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();
        user = new User(username,password, new ShoppingCard());
        try {
            if (userService.signUp(user)) {
                System.out.println("Signed Up Successfully");
                user.getShoppingCard().setUser(user);
                user.getShoppingCard().setConfirmStatus(ConfirmStatus.PENDING);
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
