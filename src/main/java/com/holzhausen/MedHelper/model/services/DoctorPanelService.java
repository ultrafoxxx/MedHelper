package com.holzhausen.MedHelper.model.services;

import com.holzhausen.MedHelper.model.entities.*;

import com.holzhausen.MedHelper.model.formclasses.Visit;
import com.holzhausen.MedHelper.model.projections.PatientProjection;
import com.holzhausen.MedHelper.model.projections.PatientProjectionImpl;
import com.holzhausen.MedHelper.model.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class DoctorPanelService {

    private UserRepository userRepository;
    private WizytaRepository wizytaRepository;
    private ZwolnienieRepository zwolnienieRepository;
    private LekRepository lekRepository;
    private ReceptaRepository receptaRepository;
    private ReceptaLekRepository receptaLekRepository;

    public DoctorPanelService(UserRepository userRepository, WizytaRepository wizytaRepository,
                              ZwolnienieRepository zwolnienieRepository, LekRepository lekRepository,
                              ReceptaRepository receptaRepository, ReceptaLekRepository receptaLekRepository) {
        this.userRepository = userRepository;
        this.wizytaRepository = wizytaRepository;
        this.zwolnienieRepository = zwolnienieRepository;
        this.lekRepository = lekRepository;
        this.receptaRepository = receptaRepository;
        this.receptaLekRepository = receptaLekRepository;
    }

    public List<PatientProjectionImpl> getPatientsForVisits(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User loggedUser = (User)auth.getPrincipal();
        Lekarz doctor = (Lekarz)userRepository.findByEmail(loggedUser.getUsername());

        List<PatientProjection> queryResult = userRepository.getPatientsForVisits(doctor.getUserId());
        List<PatientProjectionImpl> result = new ArrayList<>();

        for(PatientProjection patient : queryResult){
            PatientProjectionImpl patientProjection = new PatientProjectionImpl();
            patientProjection.setId(patient.getId());
            patientProjection.setName(patient.getName());
            patientProjection.setPesel(patient.getPesel());
            patientProjection.setVisitTime(patient.getVisitTime());
            result.add(patientProjection);
        }

        return result;

    }

    public PatientProjectionImpl getPatientForVisit(int visitId){

        PatientProjection queryResult = userRepository.getPatientForVisit(visitId);
        PatientProjectionImpl result = new PatientProjectionImpl();

        result.setName(queryResult.getName());
        result.setPesel(queryResult.getPesel());

        return result;

    }

    public void endVisit(Visit visit, boolean hasEnded){

        Wizyta wizyta = wizytaRepository.findById(visit.getVisitId());
        wizyta.setNotka(visit.getNote());
        wizyta.setCzySieOdbyla(hasEnded);
        wizyta = wizytaRepository.save(wizyta);

        if(!visit.getWhenTo().isEmpty()){

            Zwolnienie zwolnienie = new Zwolnienie();
            zwolnienie.setWizyta(wizyta);
            zwolnienie.setUnikalnyKod(Long.valueOf(String.valueOf(wizyta.getId())+String.valueOf(wizyta.getLekarz().getUserId())
                    +String.valueOf(wizyta.getPacjent().getUserId())));
            zwolnienie.setDataPocz(Date.valueOf(visit.getWhenFrom()));
            zwolnienie.setDataKon(Date.valueOf(visit.getWhenTo()));
            zwolnienie.setPrzyczynaZwolnienia(visit.getReleaseNote());
            zwolnienieRepository.save(zwolnienie);
        }

    }

    public List<Lek> getDrugData(String data){

        return lekRepository.getDrugsByData(data+"*");

    }

    public void submitPrescription(List<Integer> drugs, int visitId){

        Recepta recepta = new Recepta();
        String code;
        Wizyta wizyta = wizytaRepository.findById(visitId);
        code = String.valueOf(visitId) + String.valueOf(wizyta.getLekarz().getUserId()) + String.valueOf(wizyta.getPacjent().getUserId());

        recepta.setUnikalnyKod(code);
        recepta.setWizyta(wizyta);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        recepta.setCzasRealizacji(new Date(calendar.getTime().getTime()));

        recepta = receptaRepository.save(recepta);

        for (Integer drugId : drugs){

            ReceptaLek receptaLek = new ReceptaLek();
            receptaLek.setRecepta(recepta);
            receptaLek.setLek(lekRepository.findById(drugId).get());
            receptaLekRepository.save(receptaLek);

        }

    }

    public Wizyta getVisitById(int visitId){
       return wizytaRepository.findById(visitId);
    }

    public List<Wizyta> getVistsByPatient(int visitId){

        Wizyta wizyta = wizytaRepository.findById(visitId);
        return wizytaRepository.getPreviousVisit(wizyta.getPacjent().getUserId());

    }
}
