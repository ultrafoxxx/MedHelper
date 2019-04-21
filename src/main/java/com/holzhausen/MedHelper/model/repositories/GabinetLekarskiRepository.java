package com.holzhausen.MedHelper.model.repositories;

import com.holzhausen.MedHelper.model.entities.GabinetLekarski;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GabinetLekarskiRepository extends JpaRepository<GabinetLekarski, Integer> {

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM gabinet_lekarski " +
                                        "WHERE placowka_id=:placeId")
    List<GabinetLekarski> getGabinetsByPlaceId(@Param(value = "placeId") int placeId);

}
