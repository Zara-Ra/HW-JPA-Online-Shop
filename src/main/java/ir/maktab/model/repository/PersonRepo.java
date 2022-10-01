package ir.maktab.model.repository;

import ir.maktab.model.entity.User;

public interface PersonRepo {
    boolean signIn();
    User signUp();
    boolean signOut();
}
