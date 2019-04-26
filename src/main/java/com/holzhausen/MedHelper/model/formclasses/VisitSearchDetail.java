package com.holzhausen.MedHelper.model.formclasses;

import java.sql.Date;
import java.util.List;

public class VisitSearchDetail {

    private Date date;
    private int gabinetId;
    private int doctorId;
    private int visitTime;
    private List<String> time;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getGabinetId() {
        return gabinetId;
    }

    public void setGabinetId(int gabinetId) {
        this.gabinetId = gabinetId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(int visitTime) {
        this.visitTime = visitTime;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

}
