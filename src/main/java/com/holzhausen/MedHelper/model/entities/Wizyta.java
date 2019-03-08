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
    @ManyToOne(targetEntity = Lekarz.class, fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.LOCK})
    @JoinColumns(value = {@JoinColumn(name = "lekarzId", referencedColumnName = "user_id", nullable = false)},
            foreignKey = @ForeignKey(name = "wizyta_lekarzFK"))
    private Lekarz lekarz;

    @JsonManagedReference
    @OneToMany(mappedBy = "wizyta", targetEntity = Recepta.class)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<Recepta> recepty;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotka() {
        return notka;
    }

    public void setNotka(String notka) {
        this.notka = notka;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Pacjent getPacjent() {
        return pacjent;
    }

    public void setPacjent(Pacjent pacjent) {
        this.pacjent = pacjent;
    }

    public Lekarz getLekarz() {
        return lekarz;
    }

    public void setLekarz(Lekarz lekarz) {
        this.lekarz = lekarz;
    }

    public List<Recepta> getRecepty() {
        return recepty;
    }

    public void setRecepty(List<Recepta> recepty) {
        this.recepty = recepty;
    }
}
