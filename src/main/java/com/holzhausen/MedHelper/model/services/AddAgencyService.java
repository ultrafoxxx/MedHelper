package com.holzhausen.MedHelper.model.services;

import com.holzhausen.MedHelper.model.entities.Placowka;
import com.holzhausen.MedHelper.model.projections.AgencyProjection;
import com.holzhausen.MedHelper.model.projections.WizytaProjection;
import com.holzhausen.MedHelper.model.projections.WizytaProjectionImpl;
import com.holzhausen.MedHelper.model.repositories.PlacowkaRepository;
import org.springframework.stereotype.Service;

@Service
public class AddAgencyService {

    private PlacowkaRepository repository;

    public AddAgencyService(PlacowkaRepository repository){
        this.repository = repository;
    }

    public void add (Placowka placowka){

        repository.save(placowka);
    }

}
