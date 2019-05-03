package com.holzhausen.MedHelper.model.projections;

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

    String getHello();

    Time getTime();


}
