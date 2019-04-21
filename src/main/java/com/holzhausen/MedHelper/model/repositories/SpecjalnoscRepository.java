package com.holzhausen.MedHelper.model.repositories;

import com.holzhausen.MedHelper.model.entities.Specjalnosc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecjalnoscRepository extends JpaRepository<Specjalnosc, Integer> {

    Specjalnosc save(Specjalnosc specjalnosc);

    Specjalnosc getByName(String name);

}