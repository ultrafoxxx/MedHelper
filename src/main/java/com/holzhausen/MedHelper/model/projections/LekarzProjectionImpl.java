package com.holzhausen.MedHelper.model.projections;

public class LekarzProjectionImpl implements LekarzProjection {

    private String name;

    private String pesel;

    private String specjalnosc;

    private int userId;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPesel() {
        return pesel;
    }

    @Override
    public String getSpecjalnosc() {
        return specjalnosc;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public void setSpecjalnosc(String specjalnosc) {
        this.specjalnosc = specjalnosc;
    }
}
