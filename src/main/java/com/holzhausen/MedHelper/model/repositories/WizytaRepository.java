package com.holzhausen.MedHelper.model.repositories;

import com.holzhausen.MedHelper.model.entities.Wizyta;
import com.holzhausen.MedHelper.model.projections.OccupiedVisitsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WizytaRepository extends JpaRepository<Wizyta, Integer> {

    void deleteById(int id);

    @Query(nativeQuery = true, value = "SELECT time, czas_trwania " +
                                        "FROM wizyta " +
                                        "WHERE data")
    List<OccupiedVisitsProjection> getOccupiedVisits(@Param("doctorId") int doctorId, @Param("date")Date date,
                                                     @Param("gabinetId") int gabinetId);

}
