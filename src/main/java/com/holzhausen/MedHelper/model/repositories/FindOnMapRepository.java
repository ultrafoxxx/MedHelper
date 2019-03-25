package com.holzhausen.MedHelper.model.repositories;

import com.holzhausen.MedHelper.model.entities.Placowka;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FindOnMapRepository extends Repository<Placowka, String> {

    @Query(nativeQuery = true, value = "SELECT adres " +
            "FROM PLACOWKA " +
            "WHERE miasto=:city ")
    List<String> getPlacowki(@Param(value = "city") String city);

}
