package com.holzhausen.MedHelper.model.entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Proxy(lazy = false)
@Table(name = "Zwolnienie")
public class Zwolnienie {

    @Id
    @Column(name = "unikalnyKod", nullable = false)
    private long unikalnyKod;

    @Column(name = "DataPocz", nullable = false)
    private Date dataPocz;

    @Column(name = "DataKon", nullable = false)
    private Date dataKon;

    @Column(name = "PrzyczynaZwolnienia", nullable = false, length = 1000)
    private String przyczynaZwolnienia;

    @OneToOne(optional=false, targetEntity=Wizyta.class, fetch= FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})
    @JoinColumns(value={ @JoinColumn(name="WizytaId", referencedColumnName="id", nullable=false) },
            foreignKey=@ForeignKey(name="wizyta_zwolnienieFK"))
    private Wizyta wizyta;

    public long getUnikalnyKod() {
        return unikalnyKod;
    }

    public void setUnikalnyKod(long unikalnyKod) {
        this.unikalnyKod = unikalnyKod;
    }

    public Date getDataPocz() {
        return dataPocz;
    }

    public void setDataPocz(Date dataPocz) {
        this.dataPocz = dataPocz;
    }

    public Date getDataKon() {
        return dataKon;
    }

    public void setDataKon(Date dataKon) {
        this.dataKon = dataKon;
    }

    public String getPrzyczynaZwolnienia() {
        return przyczynaZwolnienia;
    }

    public void setPrzyczynaZwolnienia(String przyczynaZwolnienia) {
        this.przyczynaZwolnienia = przyczynaZwolnienia;
    }

    public Wizyta getWizyta() {
        return wizyta;
    }

    public void setWizyta(Wizyta wizyta) {
        this.wizyta = wizyta;
    }
}
