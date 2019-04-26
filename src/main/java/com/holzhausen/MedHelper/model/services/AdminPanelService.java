package com.holzhausen.MedHelper.model.services;

import com.holzhausen.MedHelper.model.entities.*;
import com.holzhausen.MedHelper.model.formclasses.VisitSearchDetail;
import com.holzhausen.MedHelper.model.projections.*;
import com.holzhausen.MedHelper.model.repositories.GabinetLekarskiRepository;
import com.holzhausen.MedHelper.model.repositories.PlacowkaRepository;
import com.holzhausen.MedHelper.model.repositories.UserRepository;
import com.holzhausen.MedHelper.model.repositories.WizytaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import javax.persistence.EntityManager;
import java.sql.Time;
import java.util.ArrayList;

import java.sql.Date;
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

    private EntityManager manager;

    @Autowired
    public AdminPanelService(UserRepository userRepository, PlacowkaRepository placowkaRepository,
                             GabinetLekarskiRepository gabinetLekarskiRepository, WizytaRepository wizytaRepository,
                             EntityManager manager) {
        this.userRepository = userRepository;
        this.placowkaRepository = placowkaRepository;
        this.gabinetLekarskiRepository = gabinetLekarskiRepository;
        this.wizytaRepository = wizytaRepository;
        this.manager = manager;
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

    public List<OccupiedVisitsProjectionImpl> getOccupiedVisits(VisitSearchDetail visitSearchDetail){

        List<OccupiedVisitsProjection> occupiedVisits = wizytaRepository
                .getOccupiedVisits(visitSearchDetail.getDoctorId(), visitSearchDetail.getDate(),
                        visitSearchDetail.getGabinetId());

        Placowka placowka = placowkaRepository.getPlacowkaWhereISGivenRoom(visitSearchDetail.getGabinetId());
        String[] openTime = placowka.getCzasOtwarcia().toString().split(":");
        String[] closeTime = placowka.getCzasZamkniecia().toString().split(":");

        String[] timeIter = openTime.clone();

        List<OccupiedVisitsProjectionImpl> availableVisits = new ArrayList<>();

        for(OccupiedVisitsProjection visits : occupiedVisits){
            String[] occupiedVisitTime = visits.getTime().toString().split(":");
            int difference = getMinutes(occupiedVisitTime) - getMinutes(timeIter);
            for(int i=visitSearchDetail.getVisitTime();i<=difference;i+=visitSearchDetail.getVisitTime()){
                OccupiedVisitsProjectionImpl newVisit = new OccupiedVisitsProjectionImpl();
                newVisit.setDurationTime(visitSearchDetail.getVisitTime());
                newVisit.setTime(getTranslatedTime(timeIter));
                timeIter = getTime(getMinutes(timeIter)+visitSearchDetail.getVisitTime());
                availableVisits.add(newVisit);
            }
            int durationTime = visits.getDurationTime();
            timeIter = getTime(getMinutes(visits.getTime().toString().split(":"))+durationTime);
        }

        int difference = getMinutes(closeTime) - getMinutes(timeIter);
        for(int i=visitSearchDetail.getVisitTime();i<=difference;i+=visitSearchDetail.getVisitTime()){
            OccupiedVisitsProjectionImpl newVisit = new OccupiedVisitsProjectionImpl();
            newVisit.setDurationTime(visitSearchDetail.getVisitTime());
            newVisit.setTime(getTranslatedTime(timeIter));
            timeIter = getTime(getMinutes(timeIter)+visitSearchDetail.getVisitTime());
            availableVisits.add(newVisit);
        }

        return availableVisits;
    }

    public void saveNewVisits(VisitSearchDetail visitSearchDetail){

        Date visitDate = visitSearchDetail.getDate();
        int durationTime = visitSearchDetail.getVisitTime();
        Lekarz doctor = manager.getReference(Lekarz.class, visitSearchDetail.getDoctorId());
        GabinetLekarski doctorsRoom = manager.getReference(GabinetLekarski.class, visitSearchDetail.getGabinetId());
        for(String visitTime : visitSearchDetail.getTime()){
            Wizyta wizyta = new Wizyta();
            wizyta.setData(visitDate);
            wizyta.setCzasTrwania(durationTime);
            wizyta.setLekarz(doctor);
            wizyta.setGabinetLekarski(doctorsRoom);
            wizyta.setTime(Time.valueOf(visitTime));
            wizytaRepository.save(wizyta);
        }
    }

    private Time getTranslatedTime(String[] time){

        StringBuilder builder = new StringBuilder();

        for(int i=0;i<time.length;i++){
            builder.append(time[i]);
            builder.append(":");
        }
        builder.deleteCharAt(builder.length()-1);
        return Time.valueOf(builder.toString());

    }

    private int getMinutes(String[] time){
        return Integer.parseInt(time[0])*60 + Integer.parseInt(time[1]);
    }

    private String[] getTime(int minutes){
        String[] time = new String[3];
        int hours = (int)Math.floor(minutes / 60);
        time[0] = String.valueOf(hours);
        if(hours<10){
            time[0] = "0" + time[0];
        }
        int mins = minutes % 60;
        time[1] = String.valueOf(mins);
        if(mins < 10){
            time[1] = "0" + time[1];
        }
        time[2] = "00";
        return time;
    }


}
