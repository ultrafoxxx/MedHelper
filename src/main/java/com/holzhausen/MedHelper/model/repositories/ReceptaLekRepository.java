package com.holzhausen.MedHelper.model.repositories;

import com.holzhausen.MedHelper.model.entities.ReceptaLek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceptaLekRepository extends JpaRepository<ReceptaLek, Integer> {
}
