package com.holzhausen.MedHelper.model.projections;

public class DrugProjectionImpl implements DrugProjection {

    private String drugName;
    private int drugCount;

    @Override
    public String getDrugName() {
        return drugName;
    }

    @Override
    public int getDrugCount() {
        return drugCount;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public void setDrugCount(int drugCount) {
        this.drugCount = drugCount;
    }
}
