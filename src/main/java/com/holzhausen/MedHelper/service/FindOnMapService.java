package com.holzhausen.MedHelper.service;

import com.holzhausen.MedHelper.model.entities.Placowka;
import com.holzhausen.MedHelper.repository.FindOnMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FindOnMapService {

    private FindOnMapRepository findOnMapRepository;


    @Autowired
    public FindOnMapService(FindOnMapRepository findOnMapRepository) {
        this.findOnMapRepository = findOnMapRepository;
    }

//    public double getDlugoscGeo(String miasto){
//        Placowka placowka = findOnMapRepository.getPlacowka(miasto);
//        return placowka.getDlugoscGeo();
//    }
//
//    public double getSzerGeo(String miasto){
//        Placowka placowka = findOnMapRepository.getPlacowka(miasto);
//        return placowka.getSzerGeo();
//    }

    public List<String> getAdresy(String miasto){
        return findOnMapRepository.getPlacowki(miasto);
    }
}
