package ir.maktab.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String username;
    private String password;
    private ShoppingCart shoppingCart;//TODO is it necessary?
}
