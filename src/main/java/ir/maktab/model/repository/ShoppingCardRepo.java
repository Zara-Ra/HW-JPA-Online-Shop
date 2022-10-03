package ir.maktab.model.repository;

import ir.maktab.model.entity.items.Item;
import ir.maktab.service.ShoppingCardService;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCardRepo {

    private ShoppingCardRepo(){}
    private static final ShoppingCardRepo instance = new ShoppingCardRepo();
    public static ShoppingCardRepo getInstance(){
        return instance;
    }
    public boolean addItem(){return true;}
    public boolean deleteItem(){return true;}
    public List<Item> allItems(){
        return new ArrayList<>();
    }
    public boolean confirmShopping(){return true;}
}
