package com.holzhausen.MedHelper.model.repositories;

import com.holzhausen.MedHelper.model.entities.Recepta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceptaRepository extends JpaRepository<Recepta, Long> {
}
