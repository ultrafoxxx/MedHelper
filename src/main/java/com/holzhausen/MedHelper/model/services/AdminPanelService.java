package com.holzhausen.MedHelper.model.services;

import com.holzhausen.MedHelper.model.entities.Lekarz;
import com.holzhausen.MedHelper.model.projections.LekarzProjection;
import com.holzhausen.MedHelper.model.projections.LekarzProjectionImpl;
import com.holzhausen.MedHelper.model.repositories.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class AdminPanelService {

    public static final String[] INPUT_TABLES = {"Lek", "User", "Placowka"};

    public static final int NUM_PAGES = 10;

    private UserRepository userRepository;

    public AdminPanelService(UserRepository userRepository) {
        this.userRepository = userRepository;
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



}
