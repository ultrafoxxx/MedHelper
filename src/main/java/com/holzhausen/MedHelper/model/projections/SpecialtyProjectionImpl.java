package com.holzhausen.MedHelper.model.projections;

public class SpecialtyProjectionImpl implements SpecialtyProjection{

    private String specialtyName;
    private int specialtyCount;

    @Override
    public String getSpecialtyName() {
        return specialtyName;
    }

    @Override
    public int getSpecialtyCount() {
        return specialtyCount;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public void setSpecialtyCount(int specialtyCount) {
        this.specialtyCount = specialtyCount;
    }
}
