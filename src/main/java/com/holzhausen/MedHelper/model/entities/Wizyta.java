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
@Table(name = "Wizyta")
public class Wizyta {

    @Id
    @GeneratedValue(generator = "WizytaIdGenerator")
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "notka", length = 2000)
    private String notka;

    @Column(name = "data", nullable = false)
    private Date data;

    @JsonBackReference
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId",
                        resolver = EntityResolver.class, scope = Pacjent.class)
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(targetEntity = Pacjent.class, fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.LOCK})
    @JoinColumns(value = {@JoinColumn(name = "pacjentId", referencedColumnName = "user_id", nullable = false)},
    foreignKey = @ForeignKey(name = "wizyta_pacjentFK"))
    private Pacjent pacjent;

    @JsonBackReference
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId",
            resolver = EntityResolver.class, scope = Lekarz.class)
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(targetEntity = Pacjent.class, fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.LOCK})
    @JoinColumns(value = {@JoinColumn(name = "lekarzId", referencedColumnName = "user_id", nullable = false)},
            foreignKey = @ForeignKey(name = "wizyta_lekarzFK"))
    private Lekarz lekarz;

}
