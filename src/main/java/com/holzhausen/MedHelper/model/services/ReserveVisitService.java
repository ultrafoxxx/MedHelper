package com.holzhausen.MedHelper.model.services;

import com.holzhausen.MedHelper.model.entities.Specjalnosc;
import com.holzhausen.MedHelper.model.repositories.SpecjalnoscRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReserveVisitService {

    private SpecjalnoscRepository specjalnoscRepository;

    @Autowired
    public ReserveVisitService(SpecjalnoscRepository specjalnoscRepository) {
        this.specjalnoscRepository = specjalnoscRepository;
    }

    public List<Specjalnosc> getSpecialties(){
        return specjalnoscRepository.findAll();
    }
}
