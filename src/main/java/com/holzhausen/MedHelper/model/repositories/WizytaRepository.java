package com.holzhausen.MedHelper.model.repositories;

import com.holzhausen.MedHelper.model.entities.Wizyta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WizytaRepository extends JpaRepository<Wizyta, Integer> {

    void deleteById(int id);

}
