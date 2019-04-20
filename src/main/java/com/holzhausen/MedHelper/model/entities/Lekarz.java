package com.holzhausen.MedHelper.model.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.holzhausen.MedHelper.util.EntityResolver;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("Lekarz")
@Proxy(lazy = false)
public class Lekarz extends User implements Serializable {

    @JsonManagedReference
    @OneToMany(mappedBy = "lekarz", targetEntity = Wizyta.class)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<Wizyta> wizyty;


    @OneToOne(mappedBy="lekarz", targetEntity= SpecjalnoscLekarz.class, fetch= FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})
    private SpecjalnoscLekarz specjalnosc;

    public List<Wizyta> getWizyty() {
        return wizyty;
    }

    public void setWizyty(List<Wizyta> wizyty) {
        this.wizyty = wizyty;
    }

    public SpecjalnoscLekarz getSpecjalnosc() {
        return specjalnosc;
    }

    public void setSpecjalnosc(SpecjalnoscLekarz specjalnosc) {
        this.specjalnosc = specjalnosc;
    }
}
