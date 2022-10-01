package ir.maktab.model.repository;

import ir.maktab.model.entity.items.Item;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCardRepo {
    public boolean addItem(){return true;}
    public boolean deleteItem(){return true;}
    public List<Item> allItems(){
        return new ArrayList<>();
    }
    public boolean confirmShopping(){return true;}
}
