package ir.maktab.model.repository;

import ir.maktab.model.entity.User;

public class UserRepo implements PersonRepo{

    @Override
    public boolean signIn() {
        return false;
    }

    @Override
    public User signUp() {
        return null;
    }

    @Override
    public boolean signOut() {
        return false;
    }
}
