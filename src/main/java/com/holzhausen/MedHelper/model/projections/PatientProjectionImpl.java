package com.holzhausen.MedHelper.model.projections;

public class PatientProjectionImpl implements PatientProjection {

    private int id;
    private String name;
    private String pesel;
    private String licenseNumber;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPesel() {
        return pesel;
    }

    @Override
    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}
