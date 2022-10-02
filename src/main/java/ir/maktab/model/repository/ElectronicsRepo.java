package ir.maktab.model.repository;

import ir.maktab.model.entity.items.Electronics;

public class ElectronicsRepo extends AbstractItemRepo<Electronics> {
    private ElectronicsRepo(){}//TODO Singelton is this wrong?
    private static final ElectronicsRepo instance = new ElectronicsRepo();
    public static ElectronicsRepo getInstance(){
        return instance;
    }
}
