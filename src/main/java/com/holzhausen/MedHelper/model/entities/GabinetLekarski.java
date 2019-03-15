package com.holzhausen.MedHelper.model.entities;

import com.fasterxml.jackson.annotation.*;
import com.holzhausen.MedHelper.util.EntityResolver;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

@Entity
@Proxy(lazy = false)
@Table(name = "GabinetLekarski")
public class GabinetLekarski {

    @Id
    @GeneratedValue(generator = "GabinetLekarskiIdGenerator")
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "NrSali", nullable = false)
    private int nrSali;

    @JsonManagedReference
    @OneToMany(mappedBy = "gabinetLekarski", targetEntity = Wizyta.class)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<Wizyta> wizyty;

    @JsonBackReference
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id",
            resolver = EntityResolver.class, scope = Placowka.class)
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(targetEntity = Placowka.class, fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.LOCK})
    @JoinColumns(value = {@JoinColumn(name = "placowkaId", referencedColumnName = "id", nullable = false)},
            foreignKey = @ForeignKey(name = "gabinet_placowkaFK"))
    private Placowka placowka;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNrSali() {
        return nrSali;
    }

    public void setNrSali(int nrSali) {
        this.nrSali = nrSali;
    }

    public List<Wizyta> getWizyty() {
        return wizyty;
    }

    public void setWizyty(List<Wizyta> wizyty) {
        this.wizyty = wizyty;
    }

    public Placowka getPlacowka() {
        return placowka;
    }

    public void setPlacowka(Placowka placowka) {
        this.placowka = placowka;
    }
}
