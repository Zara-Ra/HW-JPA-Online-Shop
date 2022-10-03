package ir.maktab.service;

import ir.maktab.model.entity.items.Item;
import ir.maktab.model.repository.ShoppingCardRepo;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCardService {
    private ShoppingCardService(){}
    private static final ShoppingCardService instance = new ShoppingCardService();
    public static ShoppingCardService getInstance(){
        return instance;
    }
    private ShoppingCardRepo shoppingCardRepo = ShoppingCardRepo.getInstance();

    public boolean addItem(Item item){

        return true;
    }
    public boolean deleteItem(){return true;}
    public List<Item> allItems(){
        return new ArrayList<>();
    }
    public boolean confirmShopping(){return true;}

}
