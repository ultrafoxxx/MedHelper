package com.holzhausen.MedHelper.model.services;

import com.holzhausen.MedHelper.model.entities.Placowka;
import com.holzhausen.MedHelper.model.projections.*;
import com.holzhausen.MedHelper.model.repositories.PlacowkaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddAgencyService {

    private PlacowkaRepository repository;

    public AddAgencyService(PlacowkaRepository repository){
        this.repository = repository;
    }

    public void add (Placowka placowka){

        repository.save(placowka);
    }

    public List<AgencyProjectionImpl> getAgencies(){
        List<AgencyProjectionImpl> agencies = new ArrayList<>();
        List<AgencyProjection> sourceData = repository.findAgencies();

        for(AgencyProjection sourceAgency : sourceData){

            AgencyProjectionImpl agency = new AgencyProjectionImpl();

            agency.setId(sourceAgency.getId());
            agency.setCity(sourceAgency.getCity());
            agency.setAddress(sourceAgency.getAddress());
            agencies.add(agency);
        }

        return agencies;
    }

    public void removeAgency(int cityId){
        repository.deleteById(cityId);
    }

}
