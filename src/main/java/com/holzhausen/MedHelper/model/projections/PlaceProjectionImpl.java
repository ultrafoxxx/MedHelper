package com.holzhausen.MedHelper.model.projections;

public class PlaceProjectionImpl implements PlaceProjection {

    private int id;
    private String fullName;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
