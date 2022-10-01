package ir.maktab.service;

import ir.maktab.model.entity.User;

public interface PersonService {
    boolean signIn(User user);
    boolean signUp(User user);
    boolean signOut(User user);
}
