package com.holzhausen.MedHelper.model.repositories;

import com.holzhausen.MedHelper.model.entities.Placowka;
import com.holzhausen.MedHelper.model.projections.PlaceProjection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface PlacowkaRepository extends JpaRepository<Placowka, Integer> {

    List<Placowka> findDistinctByMiastoContainingIgnoreCase(String cityName);

    List<Placowka> findAllByMiastoAndAdresContainingIgnoreCase(String cityName, String address);

    @Query(nativeQuery = true, value = "SELECT id AS id, CONCAT(miasto, ', ul. ', adres) AS fullName " +
                                        "FROM placowka " +
                                        "WHERE MATCH(miasto, adres) AGAINST (:data IN BOOLEAN MODE)")
    List<PlaceProjection> findAllPlacowkaGivenQuery(@Param("data") String data, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM placowka " +
                                        "WHERE id IN (SELECT GL.placowka_id " +
                                                        "FROM gabinet_lekarski GL" +
                                                        " WHERE GL.id=:gabinetId)")
    Placowka getPlacowkaWhereISGivenRoom(@Param("gabinetId") int gabinetId);

    Placowka save(Placowka placowka);

}
