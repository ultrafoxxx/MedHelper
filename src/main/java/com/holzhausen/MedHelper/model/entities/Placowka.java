package com.holzhausen.MedHelper.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

@Entity
@Proxy(lazy = false)
@Table(name = "Placowka")
public class Placowka {

    @Id
    @GeneratedValue(generator = "PlacowkaIdGenerator")
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "miasto", nullable = false)
    private String miasto;

    @Column(name = "adres", nullable = false)
    private String adres;

    @Column(name = "DlugoscGeo", nullable = false)
    private double dlugoscGeo;

    @Column(name = "SzerGeo", nullable = false)
    private double szerGeo;

    @Column(name = "telefon", nullable = false)
    private String telefon;

    @JsonManagedReference
    @OneToMany(mappedBy = "placowka", targetEntity = GabinetLekarski.class)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<GabinetLekarski> gabinetyLekarskie;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public double getDlugoscGeo() {
        return dlugoscGeo;
    }

    public void setDlugoscGeo(double dlugoscGeo) {
        this.dlugoscGeo = dlugoscGeo;
    }

    public double getSzerGeo() {
        return szerGeo;
    }

    public void setSzerGeo(double szerGeo) {
        this.szerGeo = szerGeo;
    }

    public List<GabinetLekarski> getGabinetyLekarskie() {
        return gabinetyLekarskie;
    }

    public void setGabinetyLekarskie(List<GabinetLekarski> gabinetyLekarskie) {
        this.gabinetyLekarskie = gabinetyLekarskie;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
