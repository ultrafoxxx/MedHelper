package com.holzhausen.MedHelper.model.services;

import com.holzhausen.MedHelper.model.entities.User;
import com.holzhausen.MedHelper.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PatientPanelService {

    private UserRepository repository;

    @Autowired
    public PatientPanelService(UserRepository repository) {
        this.repository = repository;
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
}
