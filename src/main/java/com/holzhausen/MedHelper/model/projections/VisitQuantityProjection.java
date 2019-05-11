package com.holzhausen.MedHelper.model.projections;

import java.sql.Date;

public interface VisitQuantityProjection {

    Date getVisitDate();

    int getQuantity();

}
