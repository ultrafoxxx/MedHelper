package com.holzhausen.MedHelper.model.services;

import com.holzhausen.MedHelper.model.entities.GabinetLekarski;
import com.holzhausen.MedHelper.model.entities.Lekarz;
import com.holzhausen.MedHelper.model.entities.Placowka;
import com.holzhausen.MedHelper.model.projections.*;
import com.holzhausen.MedHelper.model.repositories.GabinetLekarskiRepository;
import com.holzhausen.MedHelper.model.repositories.PlacowkaRepository;
import com.holzhausen.MedHelper.model.repositories.UserRepository;
import com.holzhausen.MedHelper.model.repositories.WizytaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminPanelService {

    public static final String[] INPUT_TABLES = {"Lek", "User", "Placowka"};

    public static final int NUM_PAGES = 10;

    private static final int NUM_HINTS = 6;

    private UserRepository userRepository;
    private PlacowkaRepository placowkaRepository;
    private GabinetLekarskiRepository gabinetLekarskiRepository;
    private WizytaRepository wizytaRepository;

    @Autowired
    public AdminPanelService(UserRepository userRepository, PlacowkaRepository placowkaRepository,
                             GabinetLekarskiRepository gabinetLekarskiRepository, WizytaRepository wizytaRepository) {
        this.userRepository = userRepository;
        this.placowkaRepository = placowkaRepository;
        this.gabinetLekarskiRepository = gabinetLekarskiRepository;
        this.wizytaRepository = wizytaRepository;
    }

    public List<LekarzProjectionImpl> getDoctors(String data, int page){

        List<LekarzProjectionImpl> doctors = new ArrayList<>();

        List<LekarzProjection> sourceData;

        Pageable pageable = PageRequest.of(page, NUM_PAGES);

        if(data.isEmpty()){
            sourceData = userRepository.getDoctors(pageable);
        }
        else {
            data+="*";
            sourceData = userRepository.queryDoctors(data, pageable);
        }

        for(LekarzProjection sourceDoctor : sourceData){

            LekarzProjectionImpl doctor = new LekarzProjectionImpl();

            doctor.setName(sourceDoctor.getName());
            doctor.setPesel(sourceDoctor.getPesel());
            doctor.setSpecjalnosc(sourceDoctor.getSpecjalnosc());
            doctor.setUserId(sourceDoctor.getUserId());
            doctors.add(doctor);

        }

        return doctors;

    }

    public int getNumberOfDoctors(String data){

        if(data.isEmpty()){
            return userRepository.getNumberOfDoctors();
        }
        else {
            return userRepository.getNumberOfDoctors(data);
        }

    }

    public List<PlaceProjectionImpl> hintPlaces(String data){

        Pageable pageable = PageRequest.of(0, NUM_HINTS);

        data+="*";

        List<PlaceProjection> places = placowkaRepository.findAllPlacowkaGivenQuery(data, pageable);

        List<PlaceProjectionImpl> result = new ArrayList<>();

        for(PlaceProjection place : places){

            PlaceProjectionImpl projection = new PlaceProjectionImpl();
            projection.setId(place.getId());
            projection.setFullName(place.getFullName());
            result.add(projection);

        }

        return result;
    }

    public List<GabinetLekarski> getRooms(int placeId){
        return gabinetLekarskiRepository.getGabinetsByPlaceId(placeId);
    }

    public List<OccupiedVisitsProjectionImpl> getOccupiedVisits(int doctorId, Date date, int gabinetId){

        List<OccupiedVisitsProjection> occupiedVisits = wizytaRepository.getOccupiedVisits(doctorId, date, gabinetId);

        List<OccupiedVisitsProjectionImpl> result = new ArrayList<>();

        for(OccupiedVisitsProjection occupiedVisit: occupiedVisits){
            OccupiedVisitsProjectionImpl data = new OccupiedVisitsProjectionImpl();
            data.setTime(occupiedVisit.getTime());
            data.setCzasTrwania(occupiedVisit.getCzasTrwania());
            result.add(data);
        }

        return result;

    }



}
