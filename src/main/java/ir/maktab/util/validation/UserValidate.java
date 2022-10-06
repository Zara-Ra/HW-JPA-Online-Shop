package ir.maktab.util.validation;

import ir.maktab.util.exceptions.ValidationException;

public class UserValidate {
    public static void Validate(String name) throws ValidationException {
        if( !name.equals("") && name.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$"))
            return;
        throw new ValidationException("Username and Password should be at least 8 characters");
    }
}
