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
import ir.maktab.util.exceptions.ShoppingCardException;
import ir.maktab.util.exceptions.ValidationException;
import ir.maktab.util.validation.UserValidate;

import java.util.*;

import static java.lang.System.exit;

public class OnlineShop {
    private final UserService userService = UserService.getInstance();
    private final ItemService itemService = ItemService.getInstance();
    private final ShoppingCardService shoppingCardService = ShoppingCardService.getInstance();
    private Scanner scanner = new Scanner(System.in);
    private User user;
    private Map<ProductCategory, List<Item>> shopItems = new HashMap<>();

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
                if (user != null) {
                    System.out.println("Please Sign Out First");
                    welcome();
                    break;
                }
                if (signIn())
                    secondMenu();
                else {
                    System.out.println("Wrong username or password");
                    welcome();
                }
                break;
            case 2:
                if (user != null) {
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
        System.out.println("Press 5 --> Back");
        System.out.println("---------------------------------------------");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            choice = 6;
        }

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
                try {
                    printShoppingCard();
                    confirmationMenu();
                } catch (ShoppingCardException e) {
                    System.err.println(e.getMessage());
                }
                secondMenu();
                break;
            case 5:
                welcome();
                break;
            case 6:
                secondMenu();
                break;
        }
    }

    private void printShoppingCard() throws ShoppingCardException {
        double totalPrice = 0;
        Map<Item, Integer> shoppingItemMap = user.getShoppingCard().getShoppingItemsMap();
        if (shoppingItemMap.size() == 0) {
            throw new ShoppingCardException("Shopping Card is Empty");
        } else {
            for (Map.Entry<Item, Integer> entry : shoppingItemMap.entrySet()) {
                double thisItemTotalPrice = entry.getValue() * entry.getKey().getPrice();
                totalPrice += thisItemTotalPrice;
                System.out.println("Number of Item: " + entry.getValue() +" Total: "+thisItemTotalPrice+ entry.getKey());
            }
            System.out.println("---------------------------------------------");
            System.out.println("Total Price of Shopping Card: " + totalPrice);
        }
    }

    private void deleteItemFromShoppingCard() {
        Map<Item, Integer> shoppingItemMap = user.getShoppingCard().getShoppingItemsMap();
        if (shoppingItemMap.size() == 0) {
            System.out.println("Shopping Card is Empty");
        } else {
            for (Iterator<Map.Entry<Item, Integer>> it = shoppingItemMap.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<Item, Integer> entry = it.next();
                System.out.println("---------------------------------------------");
                System.out.println("Number of Item: " + entry.getValue() + entry.getKey());
                System.out.println("Press 1 --> Delete Item");
                System.out.println("Press 2 --> Decrease the Number of Item");
                System.out.println("Press 3 --> Delete Next Item");
                System.out.println("Press 4 --> Back");
                System.out.println("---------------------------------------------");
                int choice;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    if (choice <= 0 || choice > 4)
                        throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    choice = 4;
                    System.out.println("Invalid Number, Back to Previous Menu...");
                }

                Integer deletedNumOfItems = 1;
                switch (choice) {
                    case 1:
                        deletedNumOfItems = entry.getValue();
                        itemService.increaseShopItemsCount(shopItems, entry.getKey(), deletedNumOfItems);
                        shoppingCardService.removeItem(it);
                        break;
                    case 2:
                        int validNumItem = entry.getValue() - 1;
                        if (validNumItem == 0) {
                            itemService.increaseShopItemsCount(shopItems, entry.getKey(), deletedNumOfItems);
                            shoppingCardService.removeItem(it);
                        } else if (validNumItem == 1) {
                            itemService.increaseShopItemsCount(shopItems, entry.getKey(), deletedNumOfItems);
                            shoppingCardService.editNumOfItem(shoppingItemMap, entry.getKey(), 1);
                        } else {
                            System.out.println("How many Items do you need? ( 1 to " + validNumItem + " )");
                            try {
                                int numOfItems = Integer.parseInt(scanner.nextLine());
                                while (numOfItems <= 0 || numOfItems > validNumItem) {
                                    System.out.println("Enter a value between 1 and " + validNumItem);
                                    numOfItems = Integer.parseInt(scanner.nextLine());
                                }
                                deletedNumOfItems = entry.getValue() - numOfItems;
                                itemService.increaseShopItemsCount(shopItems, entry.getKey(), deletedNumOfItems);
                                shoppingCardService.editNumOfItem(shoppingItemMap, entry.getKey(), numOfItems);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid Number, Back to Previous Menu...");
                                return;
                            }
                        }
                        break;
                    case 3:
                        break;
                    default:
                        return;
                }

            }
        }
    }

    private void shop() {
        System.out.println("---------------------------------------------");
        System.out.println("Press 1 --> Shop Electronics");
        System.out.println("Press 2 --> Shop Readable");
        System.out.println("Press 3 --> Shop Shoes");
        System.out.println("Press 4 --> Back");
        System.out.println("---------------------------------------------");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            choice = 4;
            System.out.println("Invalid Number, Back to Previous Menu...");
        }
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
            default:
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
            if (itemNum <= 0 || itemNum > itemList.size()) {
                throw new NumberFormatException();
            }
            if (itemList.get(itemNum-1).getCount() == 0) {
                throw new ItemUnavailableException("No More of this Item is Available");
            }
            shoppingCardService.addItem(user, itemList.get(itemNum-1));
        } catch (NumberFormatException e) {
            System.err.println("Invalid Number Entered");
        } catch (ItemUnavailableException | ShoppingCardException e) {
            System.err.println(e.getMessage());
        }
    }

    private List<Item> itemsByCategory(ProductCategory productCategory) {
        if (!shopItems.containsKey(productCategory)) {
            shopItems.put(productCategory, itemService.itemsByCategory(productCategory));
        }
        return shopItems.get(productCategory);
    }

    private void printItems(List<Item> itemList) {
        for (int j = 1; j <= itemList.size(); j++) {
            System.out.println("No."+j + " " + itemList.get(j-1));
        }
    }

    private void printAllItems() {
        for (ProductCategory pc : ProductCategory.values()) {
            System.out.println("--- " + pc + " -----------------------------------------------------------------------------------------------------------------------------");
            printItems(itemsByCategory(pc));
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    private void confirmationMenu() {
        System.out.println("---------------------------------------------");
        System.out.println("Press 1 --> Confirm Purchase");
        System.out.println("Press 2 --> Empty Shopping Card");
        System.out.println("Press 3 --> Save Shopping Card For Later Purchase");
        System.out.println("Press 4 --> Continue Shopping");
        System.out.println("---------------------------------------------");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            choice = 4;
            System.out.println("Invalid Number, Back to Previous Menu...");
        }
        switch (choice) {
            case 1:
                confirmPurchase(ConfirmStatus.CONFIRMED);
                break;
            case 2:
                confirmPurchase(ConfirmStatus.DELETE);
                break;
            case 3:
                confirmPurchase(ConfirmStatus.PENDING);
                break;
            case 4:
                secondMenu();
                break;
        }
    }

    private void confirmPurchase(ConfirmStatus confirmStatus) {
        user.getShoppingCard().setConfirmStatus(confirmStatus);
        try {
            printShoppingCard();
        } catch (ShoppingCardException e) {
            System.err.println(e.getMessage());
            return;
        }
        switch (confirmStatus) {
            case CONFIRMED:
                shoppingCardService.confirmShopping(user.getShoppingCard());
                itemService.updateShopItemsCount(user.getShoppingCard(), shopItems);
                user.getShoppingCard().setConfirmStatus(ConfirmStatus.PENDING);
                user.getShoppingCard().getShoppingItemsMap().clear();
                shopItems.clear();
                System.out.println("Shopping Card Confirmed");
                break;
            case PENDING:
                shoppingCardService.confirmShopping(user.getShoppingCard());
                System.out.println("Shopping Card Saved");
                break;
            case DELETE:
                user.getShoppingCard().getShoppingItemsMap().clear();
                shoppingCardService.confirmShopping(user.getShoppingCard());
                shopItems.clear();
                System.out.println("Shopping Card Deleted");
                break;
        }
    }

    private boolean signIn() {
        boolean signInResult = false;
        System.out.println("Username: ");
        try {
            String username = scanner.nextLine();
            UserValidate.Validate(username);
            System.out.println("Password: ");
            String password = scanner.nextLine();
            UserValidate.Validate(password);
            user = new User(username, password);
            if (userService.signIn(user)) {
                ShoppingCard shoppingCard = userService.findShoppingCard(user);
                user.setShoppingCard(shoppingCard);
                System.out.println("Signed In Successfully");
                signInResult = true;

            } else {
                System.out.println("Unable to Sign In, try again later");
            }
        } catch (ValidationException e) {
            System.err.println(e.getMessage());
        }
        return signInResult;

    }

    public boolean signUp() {
        boolean signUpResult = false;
        System.out.println("Username: ");
        try {
            String username = scanner.nextLine();
            UserValidate.Validate(username);
            System.out.println("Password: ");
            String password = scanner.nextLine();
            UserValidate.Validate(password);
            user = new User(username, password, new ShoppingCard());
            if (userService.signUp(user)) {
                System.out.println("Signed Up Successfully");
                user.getShoppingCard().setUser(user);
                user.getShoppingCard().setConfirmStatus(ConfirmStatus.PENDING);
                signUpResult = true;
            } else {
                user = null;
                System.out.println("Unable to Sign Up, try again later");
            }
        } catch (ValidationException e) {
            user = null;
            System.err.println(e.getMessage());
        }
        return signUpResult;
    }

    private void signOut() {
        ConfirmStatus confirmStatus = user.getShoppingCard().getConfirmStatus();
        if (confirmStatus.equals(ConfirmStatus.PENDING))
            confirmPurchase(ConfirmStatus.PENDING);
        user = userService.signOut(user);
    }
}
