package com.holzhausen.MedHelper.model.repositories;

import com.holzhausen.MedHelper.model.entities.SpecjalnoscLekarz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecjalnoscLekarzRepository extends JpaRepository<SpecjalnoscLekarz, Integer> {

    SpecjalnoscLekarz save(SpecjalnoscLekarz specjalnoscLekarz);

}
