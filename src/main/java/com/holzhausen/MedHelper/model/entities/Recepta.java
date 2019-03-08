package com.holzhausen.MedHelper.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.holzhausen.MedHelper.util.EntityResolver;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.sql.Date;

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

}
