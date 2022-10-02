package ir.maktab.util;

import java.util.ArrayList;
import java.util.List;

public class GenericList<T>{
    List<T> list = new ArrayList<>();
    public GenericList(){}
    public void add(T item){
        list.add(item);
    }
}
