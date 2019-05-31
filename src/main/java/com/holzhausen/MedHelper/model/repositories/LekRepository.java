package com.holzhausen.MedHelper.model.repositories;

import com.holzhausen.MedHelper.model.entities.Lek;
import com.holzhausen.MedHelper.model.projections.DrugProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LekRepository extends JpaRepository<Lek, Integer> {

    Lek save(Lek lek);

    @Query(nativeQuery = true, value = "SELECT L.nazwa AS drugName, COUNT(*) AS drugCount " +
                                        "FROM lek L JOIN recepta_lek RL " +
                                        "ON L.id = RL.lek_id " +
                                        "GROUP BY l.nazwa " +
                                        "HAVING COUNT(*)>0 " +
                                        "ORDER BY drugCount DESC " +
                                        "LIMIT 5")
    List<DrugProjection> getDrugStatistics();

}
