package com.holzhausen.MedHelper.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.holzhausen.MedHelper.util.EntityResolver;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Proxy(lazy = false)
@Table(name = "ReceptaLek")
public class ReceptaLek {

    @Id
    @GeneratedValue(generator = "ReceptaLekIdGenerator")
    @Column(name = "id", nullable = false)
    private int id;

    @JsonBackReference
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "unikalnyKod",
            resolver = EntityResolver.class, scope = Recepta.class)
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(targetEntity = Recepta.class, fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.LOCK})
    @JoinColumns(value = {@JoinColumn(name = "UnikalnyKod", referencedColumnName = "UnikalnyKod", nullable = false)},
            foreignKey = @ForeignKey(name = "receptalek_receptaFK"))
    private Recepta recepta;

    @JsonBackReference
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id",
            resolver = EntityResolver.class, scope = Lek.class)
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(targetEntity = Lek.class, fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.LOCK})
    @JoinColumns(value = {@JoinColumn(name = "lekId", referencedColumnName = "id", nullable = false)},
            foreignKey = @ForeignKey(name = "receptalek_lekFK"))
    private Lek lek;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Recepta getRecepta() {
        return recepta;
    }

    public void setRecepta(Recepta recepta) {
        this.recepta = recepta;
    }

    public Lek getLek() {
        return lek;
    }

    public void setLek(Lek lek) {
        this.lek = lek;
    }
}
