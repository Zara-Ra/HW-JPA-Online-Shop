package ir.maktab.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private String username;
    private String password;
    private ShoppingCart shoppingCart;//TODO is it necessary?

    @Override
    public String toString() {
        return "Username: " + username + " Shopping Card: "+ shoppingCart;
    }
}
