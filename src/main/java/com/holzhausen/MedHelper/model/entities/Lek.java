package com.holzhausen.MedHelper.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.holzhausen.MedHelper.model.enums.Forma;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

@Entity
@Proxy(lazy = false)
@Table(name = "Lek")
public class Lek {

    @Id
    @GeneratedValue(generator = "LekIdGenerator")
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "nazwa", nullable = false)
    private String nazwa;

    @Column(name = "dawka", nullable = false)
    private float dawka;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma", nullable = false)
    private Forma forma;

    @Column(name = "IleOpakowan", nullable = false)
    private int ileOpakowan;

    @JsonManagedReference
    @OneToMany(mappedBy = "lek", targetEntity = ReceptaLek.class)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<ReceptaLek> receptaLek;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public float getDawka() {
        return dawka;
    }

    public void setDawka(float dawka) {
        this.dawka = dawka;
    }

    public Forma getForma() {
        return forma;
    }

    public void setForma(Forma forma) {
        this.forma = forma;
    }

    public int getIleOpakowan() {
        return ileOpakowan;
    }

    public void setIleOpakowan(int ileOpakowan) {
        this.ileOpakowan = ileOpakowan;
    }

    public List<ReceptaLek> getReceptaLek() {
        return receptaLek;
    }

    public void setReceptaLek(List<ReceptaLek> receptaLek) {
        this.receptaLek = receptaLek;
    }
}
