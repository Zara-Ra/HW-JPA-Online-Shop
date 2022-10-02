package ir.maktab.model.repository;

import ir.maktab.model.entity.items.Electronics;
import ir.maktab.model.entity.items.Readable;

public class ReadableRepo extends AbstractItemRepo<Readable> {
    private ReadableRepo(){}
    private static final ReadableRepo instance = new ReadableRepo();
    public static ReadableRepo getInstance(){
        return instance;
    }
}
