package com.holzhausen.MedHelper.model.repositories;

import java.sql.Date;
import java.sql.Time;

public class WizytaProjectionImpl {

    private int id;
    private String imie;
    private String nazwisko;
    private int nrSali;
    private String miasto;
    private String adres;
    private Date data;
    private Time time;
    private boolean actual;
    private String color;

    public WizytaProjectionImpl() {

    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public int getNrSali() {
        return nrSali;
    }

    public String getMiasto() {
        return miasto;
    }

    public String getAdres() {
        return adres;
    }

    public Date getData() {
        return data;
    }

    public Time getTime() {
        return time;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setNrSali(int nrSali) {
        this.nrSali = nrSali;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public boolean isActual() {
        return actual;
    }

    public void setActual(boolean actual) {
        this.actual = actual;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
