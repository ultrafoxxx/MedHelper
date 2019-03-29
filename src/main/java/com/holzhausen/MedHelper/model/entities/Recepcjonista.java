package com.holzhausen.MedHelper.model.entities;


import org.hibernate.annotations.Proxy;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("Recepcjonista")
@Proxy(lazy = false)
public class Recepcjonista extends User implements Serializable {
}
