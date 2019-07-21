package com.holzhausen.MedHelper.model.repositories;

import com.holzhausen.MedHelper.model.entities.Zwolnienie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZwolnienieRepository extends JpaRepository<Zwolnienie, Long> {

    Zwolnienie save(Zwolnienie zwolnienie);

}
