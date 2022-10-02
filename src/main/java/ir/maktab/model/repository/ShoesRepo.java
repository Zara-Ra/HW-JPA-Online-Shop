package ir.maktab.model.repository;

import ir.maktab.model.entity.items.Readable;
import ir.maktab.model.entity.items.Shoes;

public class ShoesRepo extends AbstractItemRepo<Shoes> {
    private ShoesRepo(){}
    private static final ShoesRepo instance = new ShoesRepo();
    public static ShoesRepo getInstance(){
        return instance;
    }
}
