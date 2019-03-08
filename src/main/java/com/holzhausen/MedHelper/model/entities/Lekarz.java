package com.holzhausen.MedHelper.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

}
