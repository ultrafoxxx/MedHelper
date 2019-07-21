package com.holzhausen.MedHelper.model.formclasses;

import java.sql.Date;

public class Visit {

    private int visitId;
    private String note;
    private String releaseNote;
    private String whenFrom;
    private String whenTo;
    private boolean hasEnded;

    public boolean isHasEnded() {
        return hasEnded;
    }

    public void setHasEnded(boolean hasEnded) {
        this.hasEnded = hasEnded;
    }

    public int getVisitId() {
        return visitId;
    }

    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReleaseNote() {
        return releaseNote;
    }

    public void setReleaseNote(String releaseNote) {
        this.releaseNote = releaseNote;
    }

    public String getWhenFrom() {
        return whenFrom;
    }

    public void setWhenFrom(String whenFrom) {
        this.whenFrom = whenFrom;
    }

    public String getWhenTo() {
        return whenTo;
    }

    public void setWhenTo(String whenTo) {
        this.whenTo = whenTo;
    }
}
