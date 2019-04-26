package com.holzhausen.MedHelper.model.projections;

import java.sql.Time;

public class OccupiedVisitsProjectionImpl implements OccupiedVisitsProjection {

    private Time time;

    private int durationTime;

    @Override
    public Time getTime() {
        return time;
    }

    @Override
    public int getDurationTime() {
        return durationTime;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }
}
