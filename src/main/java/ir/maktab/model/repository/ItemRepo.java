package ir.maktab.model.repository;

import ir.maktab.model.entity.items.Item;

import java.util.List;

public interface ItemRepo <T extends Item>{
    List<T> allAvailableItems();    // TODO Can it be a default method?
    boolean decreseCount(int num);
    T findItemByName(String name);
    T findItemByID(int ID);

}
