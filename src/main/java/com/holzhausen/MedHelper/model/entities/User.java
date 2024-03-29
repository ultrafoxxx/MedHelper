package com.holzhausen.MedHelper.model.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.holzhausen.MedHelper.validators.StringToNumberConstraint;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Proxy(lazy = false)
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "rola", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("User")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "role")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Lekarz.class, name = "Lekarz"),
        @JsonSubTypes.Type(value = Pacjent.class, name = "Pacjent")
})
public abstract class User implements Serializable {

    @Transient
    public static final String[] roles = {"Pacjent", "Recepcjonista", "Lekarz", "Administrator"};

    @Id
    @GeneratedValue(generator = "UserIdGenerator")
    @Column(name = "user_id", nullable = false)
    private int userId;

    @NotEmpty(message = "Pole nie może być puste")
    @Size(max = 60, message = "Email może posiadać tylko 60 znaków")
    @Column(name = "email", unique = true, nullable = false, length = 60)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nazwisko", nullable = false)
    @NotEmpty(message = "Pole nie może być puste")
    private String nazwisko;

    @Column(name = "imie", nullable = false)
    @NotEmpty(message = "Pole nie może być puste")
    private String imie;

    @Column(name = "pesel", nullable = false, unique = true)
    @NotEmpty(message = "Pole nie może być puste")
    @Size(min = 11, max = 11, message = "Pole musi posiadać dokładnie 11 znaków")
    private String pesel;

    @Column(name = "nrTelefonu", nullable = false, length = 12)
    @NotEmpty(message = "Pole nie może być puste")
    @Size(min = 9, max = 9, message = "Pole musi posiadać dokładnie 9 znaków")
    private String nrTelefonu;

    @Column(name = "nrDowodu", unique = true)
    private String nrDowodu;

    @Column(name = "CzyKontoPotwierdzone", nullable = false)
    private boolean czyKontoPotwierdzone;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getNrTelefonu() {
        return nrTelefonu;
    }

    public void setNrTelefonu(String nrTelefonu) {
        this.nrTelefonu = nrTelefonu;
    }

    public String getNrDowodu() {
        return nrDowodu;
    }

    public void setNrDowodu(String nrDowodu) {
        this.nrDowodu = nrDowodu;
    }

    public boolean isCzyKontoPotwierdzone() {
        return czyKontoPotwierdzone;
    }

    public void setCzyKontoPotwierdzone(boolean czyKontoPotwierdzone) {
        this.czyKontoPotwierdzone = czyKontoPotwierdzone;
    }
}
