package com.holzhausen.MedHelper.model.projections;

import java.sql.Time;

public class OccupiedVisitsProjectionImpl implements OccupiedVisitsProjection {

    private Time time;

    private int czasTrwania;

    @Override
    public Time getTime() {
        return time;
    }

    @Override
    public int getCzasTrwania() {
        return czasTrwania;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setCzasTrwania(int czasTrwania) {
        this.czasTrwania = czasTrwania;
    }
}
