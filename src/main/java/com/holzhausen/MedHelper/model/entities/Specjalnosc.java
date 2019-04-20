package com.holzhausen.MedHelper.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Specjalnosc")
public class Specjalnosc {

    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "specjalnosc", targetEntity = SpecjalnoscLekarz.class)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<SpecjalnoscLekarz> lekarze;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SpecjalnoscLekarz> getLekarze() {
        return lekarze;
    }

    public void setLekarze(List<SpecjalnoscLekarz> lekarze) {
        this.lekarze = lekarze;
    }
}
