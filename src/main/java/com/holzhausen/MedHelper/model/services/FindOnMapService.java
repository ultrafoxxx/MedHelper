package com.holzhausen.MedHelper.model.services;

import com.holzhausen.MedHelper.model.entities.Placowka;
import com.holzhausen.MedHelper.model.repositories.PlacowkaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindOnMapService {

    private PlacowkaRepository repository;

    @Autowired
    public FindOnMapService(PlacowkaRepository repository) {
        this.repository = repository;
    }

    public List<String> getPlacesWithCityContaining(String place){

        List<Placowka> places = repository.findDistinctByMiastoContainingIgnoreCase(place);
        List<String> cities = new ArrayList<>();
        for(int i =0;i<places.size(); i++){
            cities.add(places.get(i).getMiasto());
        }
        cities = cities.stream().distinct().collect(Collectors.toList());

        return cities;

    }

    public List<String> getPlacesWithCityAndAddressCotaining(String city, String address){

        List<Placowka> places = repository.findAllByMiastoAndAdresContainingIgnoreCase(city, address);
        List<String> addresses = new ArrayList<>();
        for(int i =0;i<places.size(); i++){
            addresses.add(places.get(i).getAdres());
        }

        return addresses;

    }
}
