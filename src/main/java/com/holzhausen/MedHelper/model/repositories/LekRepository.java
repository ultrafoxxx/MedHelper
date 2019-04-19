package com.holzhausen.MedHelper.model.repositories;

import com.holzhausen.MedHelper.model.entities.Lek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LekRepository extends JpaRepository<Lek, Integer> {

    Lek save(Lek lek);

}
