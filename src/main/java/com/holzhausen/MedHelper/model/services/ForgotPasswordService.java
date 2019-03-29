package com.holzhausen.MedHelper.model.services;

import com.holzhausen.MedHelper.model.entities.User;
import com.holzhausen.MedHelper.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordService {

    private UserRepository repository;

    @Autowired
    public ForgotPasswordService(UserRepository repository) {
        this.repository = repository;
    }

    public boolean doesUserExists(String email){

        User user = repository.findByEmail(email);
        if(user == null){
            return false;
        }
        else {
            return true;
        }

    }
}
