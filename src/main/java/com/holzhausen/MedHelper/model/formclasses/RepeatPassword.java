package com.holzhausen.MedHelper.model.formclasses;

import com.holzhausen.MedHelper.validators.PasswordConstraint;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RepeatPassword {

    private int userId;

    @Size(min = 10, max = 50, message = "Hasło powinno mieć przynajmniej 10 znaków")
    @PasswordConstraint
    @NotEmpty(message = "Pole nie może być puste")
    private String password;

    @NotEmpty(message = "Pole nie może być puste")
    private String repeatedPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
