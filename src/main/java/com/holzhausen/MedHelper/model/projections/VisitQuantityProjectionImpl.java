package com.holzhausen.MedHelper.model.projections;

import java.sql.Date;

public class VisitQuantityProjectionImpl implements VisitQuantityProjection {

    private Date visitDate;
    private int quantity;

    @Override
    public Date getVisitDate() {
        return visitDate;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    public void setDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
