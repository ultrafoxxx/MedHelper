package com.holzhausen.MedHelper.model.repositories;

import com.holzhausen.MedHelper.model.entities.Placowka;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PlacowkaRepository extends Repository<Placowka, Integer> {

    List<Placowka> findDistinctByMiastoContaining(String cityName);

    List<Placowka> findAllByMiastoAndAdresContaining(String cityName, String address);

}
