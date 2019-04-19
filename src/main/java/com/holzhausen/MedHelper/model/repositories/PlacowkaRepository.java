package com.holzhausen.MedHelper.model.repositories;

import com.holzhausen.MedHelper.model.entities.Placowka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface PlacowkaRepository extends JpaRepository<Placowka, Integer> {

    List<Placowka> findDistinctByMiastoContainingIgnoreCase(String cityName);

    List<Placowka> findAllByMiastoAndAdresContainingIgnoreCase(String cityName, String address);

    Placowka save(Placowka placowka);

}
