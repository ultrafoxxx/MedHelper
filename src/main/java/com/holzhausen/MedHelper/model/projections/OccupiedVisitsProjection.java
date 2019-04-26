package com.holzhausen.MedHelper.model.projections;

import java.sql.Time;

public interface OccupiedVisitsProjection {

    Time getTime();

    int getDurationTime();

}
