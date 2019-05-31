package com.holzhausen.MedHelper.model.repositories;

import com.holzhausen.MedHelper.model.entities.Specjalnosc;
import com.holzhausen.MedHelper.model.projections.SpecialtyProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecjalnoscRepository extends JpaRepository<Specjalnosc, Integer> {

    Specjalnosc save(Specjalnosc specjalnosc);

    Specjalnosc getByName(String name);

    List<Specjalnosc> findAll();

    @Query(nativeQuery = true, value = "SELECT S.name AS specialtyName, COUNT(*) AS specialtyCount " +
                                        "FROM specjalnosc S JOIN specjalnosc_lekarz SL " +
                                        "ON S.id = SL.specjalnosc_id JOIN user U " +
                                        "ON SL.lekarz_id = U.user_id JOIN wizyta W " +
                                        "ON U.user_id = W.lekarz_id " +
                                        "WHERE pacjent_id IS NOT NULL " +
                                        "GROUP BY S.name " +
                                        "HAVING COUNT(*)>0 " +
                                        "ORDER BY specialtyCount DESC " +
                                        "LIMIT 5")
    List<SpecialtyProjection> getSpecialtyStatistics();

}
