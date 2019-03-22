package com.holzhausen.MedHelper.model.services;

import com.holzhausen.MedHelper.model.entities.Placowka;
import com.holzhausen.MedHelper.model.repositories.PlacowkaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindOnMapService {

    private PlacowkaRepository repository;

    @Autowired
    public FindOnMapService(PlacowkaRepository repository) {
        this.repository = repository;
    }

    public List<Placowka> getPlacesWithCityContaining(String place){

        return repository.findDistinctByMiastoContaining(place);

    }

    public List<Placowka> getPlacesWithCityAndAddressCotaining(String city, String address){

        return repository.findAllByMiastoAndAdresContaining(city, address);

    }
}
