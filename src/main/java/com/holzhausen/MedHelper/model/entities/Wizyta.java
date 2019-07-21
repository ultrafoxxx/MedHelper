package com.holzhausen.MedHelper.model.entities;

import com.fasterxml.jackson.annotation.*;
import com.holzhausen.MedHelper.util.EntityResolver;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Proxy(lazy = false)
@Table(name = "Wizyta")
public class Wizyta {

    @Id
    @GeneratedValue(generator = "WizytaIdGenerator")
    @Column(name = "id", nullable = false)
    private int id;

    @Lob
    @Column(name = "notka")
    private String notka;

    @Column(name = "data", nullable = false)
    private Date data;

    @Column(name = "time")
    private Time time;

    @Column(name = "czasTrwania", nullable = false)
    private int czasTrwania;

    @Column(name = "czySieOdbyla")
    private boolean czySieOdbyla = false;

    @JsonBackReference
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId",
                        resolver = EntityResolver.class, scope = Pacjent.class)
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(targetEntity = Pacjent.class, fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.LOCK})
    @JoinColumns(value = {@JoinColumn(name = "pacjentId", referencedColumnName = "user_id")},
    foreignKey = @ForeignKey(name = "wizyta_pacjentFK"))
    private Pacjent pacjent;

    @JsonBackReference
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId",
            resolver = EntityResolver.class, scope = Lekarz.class)
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(targetEntity = Lekarz.class, fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.LOCK})
    @JoinColumns(value = {@JoinColumn(name = "lekarzId", referencedColumnName = "user_id", nullable = false)},
            foreignKey = @ForeignKey(name = "wizyta_lekarzFK"))
    private Lekarz lekarz;

    @JsonManagedReference
    @OneToMany(mappedBy = "wizyta", targetEntity = Recepta.class)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<Recepta> recepty;

    @JsonBackReference
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id",
            resolver = EntityResolver.class, scope = GabinetLekarski.class)
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(targetEntity = GabinetLekarski.class, fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.LOCK})
    @JoinColumns(value = {@JoinColumn(name = "GabinetLekarskiId", referencedColumnName = "id", nullable = false)},
            foreignKey = @ForeignKey(name = "wizyta_gabinetFK"))
    private GabinetLekarski gabinetLekarski;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "unikalnyKod",
            resolver = EntityResolver.class, scope = Zwolnienie.class)
    @JsonIdentityReference(alwaysAsId = true)
    @OneToOne(mappedBy="wizyta", targetEntity= Zwolnienie.class, fetch= FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})
    private Zwolnienie zwolnienie;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotka() {
        return notka;
    }

    public void setNotka(String notka) {
        this.notka = notka;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Pacjent getPacjent() {
        return pacjent;
    }

    public int getCzasTrwania() {
        return czasTrwania;
    }

    public void setCzasTrwania(int czasTrwania) {
        this.czasTrwania = czasTrwania;
    }

    public void setPacjent(Pacjent pacjent) {
        this.pacjent = pacjent;
    }

    public Lekarz getLekarz() {
        return lekarz;
    }

    public void setLekarz(Lekarz lekarz) {
        this.lekarz = lekarz;
    }

    public List<Recepta> getRecepty() {
        return recepty;
    }

    public void setRecepty(List<Recepta> recepty) {
        this.recepty = recepty;
    }

    public GabinetLekarski getGabinetLekarski() {
        return gabinetLekarski;
    }

    public void setGabinetLekarski(GabinetLekarski gabinetLekarski) {
        this.gabinetLekarski = gabinetLekarski;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Zwolnienie getZwolnienie() {
        return zwolnienie;
    }

    public void setZwolnienie(Zwolnienie zwolnienie) {
        this.zwolnienie = zwolnienie;
    }

    public boolean isCzySieOdbyla() {
        return czySieOdbyla;
    }

    public void setCzySieOdbyla(boolean czySieOdbyla) {
        this.czySieOdbyla = czySieOdbyla;
    }
}
