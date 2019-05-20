package com.holzhausen.MedHelper.model.repositories;

import com.holzhausen.MedHelper.model.entities.GabinetLekarski;
import com.holzhausen.MedHelper.model.projections.RoomProjection;
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

    @Query(nativeQuery = true, value = "SELECT GLL.id AS id, GLL.nr_sali AS nrSali " +
            "FROM gabinet_lekarski GLL " +
            "WHERE GLL.placowka_id=(SELECT DISTINCT P.id " +
            "FROM wizyta W JOIN gabinet_lekarski GL " +
            "ON W.gabinet_lekarski_id=GL.id JOIN placowka P " +
            "ON GL.placowka_id=P.id " +
            "WHERE W.id=:visitId)")
    List<RoomProjection> queryRoomsByVisit(@Param("visitId") int visitId);

}
