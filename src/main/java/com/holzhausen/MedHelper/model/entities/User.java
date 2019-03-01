package com.holzhausen.MedHelper.model.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id", nullable = false)
    private int id;

    @NotEmpty(message = "Pole nie może być puste")
    @Column(name = "login", unique = true, nullable = false, length = 30)
    private String login;

    @NotEmpty(message = "Pole nie może być puste")
    @Column(name = "password", nullable = false)
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
