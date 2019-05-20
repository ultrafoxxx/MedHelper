package com.holzhausen.MedHelper.model.projections;

public class RoomProjectionImpl implements RoomProjection {

    private int id;
    private int nrSali;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getNrSali() {
        return nrSali;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNrSali(int nrSali) {
        this.nrSali = nrSali;
    }
}
