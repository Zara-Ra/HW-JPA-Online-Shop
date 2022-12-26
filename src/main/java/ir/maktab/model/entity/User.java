package ir.maktab.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private ShoppingCard shoppingCard;//unnecessary can keep this relation just in shopping card

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, ShoppingCard shoppingCard) {
        this.username = username;
        this.password = password;
        this.shoppingCard = shoppingCard;
    }

    @Override
    public String toString() {
        return "Username: " + username + " Shopping Card: " + shoppingCard;
    }
}
