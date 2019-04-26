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

    Wizyta save(Wizyta wizyta);

    void deleteById(int id);

    @Query(nativeQuery = true, value = "SELECT time AS time, czas_trwania AS durationTime " +
                                        "FROM wizyta " +
                                        "WHERE data=:date AND (lekarz_id=:doctorId OR gabinet_lekarski_id=:gabinetId) " +
                                        "ORDER BY time")
    List<OccupiedVisitsProjection> getOccupiedVisits(@Param("doctorId") int doctorId, @Param("date")Date date,
                                                     @Param("gabinetId") int gabinetId);

}
