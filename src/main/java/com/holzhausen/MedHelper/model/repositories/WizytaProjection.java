package com.holzhausen.MedHelper.model.repositories;

import java.sql.Date;
import java.sql.Time;

public interface WizytaProjection {

    int getId();

    String getImie();

    String getNazwisko();

    int getNrSali();

    String getMiasto();

    String getAdres();

    Date getData();

    Time getTime() ;
}
