package com.holzhausen.MedHelper.model.projections;

public class ReserveVisitItemProjectionImpl implements ReserveVisitItemProjection {

    private String specialty;
    private int doctorId;
    private String fullName;
    private int placeId;
    private String city;
    private String address;

    @Override
    public String getSpecialty() {
        return specialty;
    }

    @Override
    public int getDoctorId() {
        return doctorId;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public int getPlaceId() {
        return placeId;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getAddress() {
        return address;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
