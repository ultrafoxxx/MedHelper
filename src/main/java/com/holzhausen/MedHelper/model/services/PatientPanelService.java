package com.holzhausen.MedHelper.model.services;

import com.holzhausen.MedHelper.model.entities.User;
import com.holzhausen.MedHelper.model.entities.Wizyta;
import com.holzhausen.MedHelper.model.projections.WizytaProjectionImpl;
import com.holzhausen.MedHelper.model.repositories.UserRepository;
import com.holzhausen.MedHelper.model.repositories.WizytaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientPanelService {

    private UserRepository repository;
    private WizytaRepository visitRepository;
    private ReceptionistService receptionistService;

    @Autowired
    public PatientPanelService(UserRepository repository, WizytaRepository visitRepository,
                               ReceptionistService receptionistService) {
        this.repository = repository;
        this.visitRepository = visitRepository;
        this.receptionistService = receptionistService;
    }

    public User getPatient() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return repository.findByEmail(auth.getName());
    }

    public User findUserById(int userId){
        return repository.findByUserId(userId);
    }

    public User updateUserInfo(User user){
        return repository.save(user);
    }

    public List<WizytaProjectionImpl> getVisits(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth.getPrincipal();

        return receptionistService.getVisists(repository.findByEmail(user.getUsername()).getUserId());

    }

    public void resignVisit(int visitId){

        Wizyta visit = visitRepository.findById(visitId);
        visit.setPacjent(null);
        visitRepository.save(visit);

    }
}
