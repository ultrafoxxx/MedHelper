package com.holzhausen.MedHelper.model.projections;

import java.sql.Time;

public class TimeVisitsProjectionImpl implements TimeVisistsProjection {

    private int id;
    private Time time;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Time getTime() {
        return time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
