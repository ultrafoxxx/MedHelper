package com.holzhausen.MedHelper.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.holzhausen.MedHelper.util.EntityResolver;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SpecjalnoscLekarz")
public class SpecjalnoscLekarz implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @OneToOne(optional=false, targetEntity=Lekarz.class, fetch= FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})
    @JoinColumns(value={ @JoinColumn(name="lekarzId", referencedColumnName="user_id", nullable=false) },
            foreignKey=@ForeignKey(name="specjalnosc_lekarzFK"))
    private Lekarz lekarz;

    @JsonBackReference
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id",
            resolver = EntityResolver.class, scope = Specjalnosc.class)
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(targetEntity = Specjalnosc.class, fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.LOCK})
    @JoinColumns(value = {@JoinColumn(name = "specjalnoscId", referencedColumnName = "id", nullable = false)},
            foreignKey = @ForeignKey(name = "lekarz_specjalnoscFK"))
    private Specjalnosc specjalnosc;

    public Lekarz getLekarz() {
        return lekarz;
    }

    @PrePersist
    private void setIdPrePersist(){
        id = lekarz.getUserId();
    }

    public void setLekarz(Lekarz lekarz) {
        this.lekarz = lekarz;
    }

    public Specjalnosc getSpecjalnosc() {
        return specjalnosc;
    }

    public void setSpecjalnosc(Specjalnosc specjalnosc) {
        this.specjalnosc = specjalnosc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
