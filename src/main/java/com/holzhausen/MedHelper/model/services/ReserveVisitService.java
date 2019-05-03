package com.holzhausen.MedHelper.model.services;

import com.holzhausen.MedHelper.model.entities.Pacjent;
import com.holzhausen.MedHelper.model.entities.Specjalnosc;
import com.holzhausen.MedHelper.model.entities.Wizyta;
import com.holzhausen.MedHelper.model.projections.*;
import com.holzhausen.MedHelper.model.repositories.SpecjalnoscRepository;
import com.holzhausen.MedHelper.model.repositories.UserRepository;
import com.holzhausen.MedHelper.model.repositories.WizytaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReserveVisitService {

    private SpecjalnoscRepository specjalnoscRepository;
    private WizytaRepository wizytaRepository;
    private UserRepository repository;

    @Autowired
    public ReserveVisitService(SpecjalnoscRepository specjalnoscRepository, WizytaRepository wizytaRepository, UserRepository repository) {
        this.specjalnoscRepository = specjalnoscRepository;
        this.wizytaRepository = wizytaRepository;
        this.repository = repository;
    }

    public List<Specjalnosc> getSpecialties(){
        return specjalnoscRepository.findAll();
    }

    public List<ReserveVisitItemProjectionImpl> getDoctorsWithVisists(Date date, String placeName,String specialty){
        List<ReserveVisitItemProjection> queryResult;
        if(specialty.equals("default")){
            queryResult = wizytaRepository.getDoctorsWithVisits(date, placeName+"*");
        }
        else {
            queryResult = wizytaRepository.getDoctorsWithVisitsGivenSpecialty(date, placeName+"*",
                    specialty);
        }
        List<ReserveVisitItemProjectionImpl> result = new ArrayList<>();
        for(ReserveVisitItemProjection myResult : queryResult){

            ReserveVisitItemProjectionImpl newItem = new ReserveVisitItemProjectionImpl();
            newItem.setAddress(myResult.getAddress());
            newItem.setCity(myResult.getCity());
            newItem.setDoctorId(myResult.getDoctorId());
            newItem.setFullName(myResult.getFullName());
            newItem.setPlaceId(myResult.getPlaceId());
            newItem.setSpecialty(myResult.getSpecialty());
            result.add(newItem);
        }
        return result;
    }

    public List<TimeVisitsProjectionImpl> getDoctorVisits(Date date, int placeId, int doctorId){
        List<TimeVisistsProjection> queryResult = wizytaRepository.getDoctorsVisits(date, placeId, doctorId);
        List<TimeVisitsProjectionImpl> result = new ArrayList<>();
        for(TimeVisistsProjection myResult : queryResult){
            TimeVisitsProjectionImpl newItem = new TimeVisitsProjectionImpl();
            newItem.setId(myResult.getId());
            newItem.setTime(myResult.getTime());
            result.add(newItem);
        }
        return result;
    }

    public WizytaProjectionImpl getVisitGivenId(int visitId){
        WizytaProjection queryResult = wizytaRepository.getVisitGivenId(visitId);
        WizytaProjectionImpl result = new WizytaProjectionImpl();
        result.setAdres(queryResult.getAdres());
        result.setData(queryResult.getData());
        result.setImie(queryResult.getImie());
        result.setMiasto(queryResult.getMiasto());
        result.setNazwisko(queryResult.getNazwisko());
        result.setTime(queryResult.getTime());
        result.setNrSali(queryResult.getNrSali());
        result.setHello(queryResult.getHello());
        return result;
    }

    public void reserveVisit(int visitId){

        Wizyta reservation = wizytaRepository.findById(visitId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        reservation.setPacjent((Pacjent)repository.findByEmail(user.getUsername()));
        wizytaRepository.save(reservation);
    }

}
