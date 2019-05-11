package com.holzhausen.MedHelper.model.projections;


public class AgencyProjectionImpl implements AgencyProjection {

    private int id;

    private String city;

    private String address;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getCity() {
        return city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setId(int id) {
        this.id = id;
    }
}


