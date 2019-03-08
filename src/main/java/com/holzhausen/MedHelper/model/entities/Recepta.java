package com.holzhausen.MedHelper.model.entities;

import com.fasterxml.jackson.annotation.*;
import com.holzhausen.MedHelper.util.EntityResolver;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Proxy(lazy = false)
@Table(name = "Recepta")
public class Recepta {

    @Id
    @Column(name = "UnikalnyKod", nullable = false)
    private String unikalnyKod;

    @Column(name = "CzasRealizacji", nullable = false)
    private Date czasRealizacji;

    @JsonBackReference
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id",
            resolver = EntityResolver.class, scope = Wizyta.class)
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(targetEntity = Wizyta.class, fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.LOCK})
    @JoinColumns(value = {@JoinColumn(name = "wizytaId", referencedColumnName = "id", nullable = false)},
            foreignKey = @ForeignKey(name = "recepta_wizytaFK"))
    private Wizyta wizyta;

    @JsonManagedReference
    @OneToMany(mappedBy = "recepta", targetEntity = ReceptaLek.class)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<ReceptaLek> receptaLek;

    public String getUnikalnyKod() {
        return unikalnyKod;
    }

    public void setUnikalnyKod(String unikalnyKod) {
        this.unikalnyKod = unikalnyKod;
    }

    public Date getCzasRealizacji() {
        return czasRealizacji;
    }

    public void setCzasRealizacji(Date czasRealizacji) {
        this.czasRealizacji = czasRealizacji;
    }

    public Wizyta getWizyta() {
        return wizyta;
    }

    public void setWizyta(Wizyta wizyta) {
        this.wizyta = wizyta;
    }

    public List<ReceptaLek> getReceptaLek() {
        return receptaLek;
    }

    public void setReceptaLek(List<ReceptaLek> receptaLek) {
        this.receptaLek = receptaLek;
    }
}
