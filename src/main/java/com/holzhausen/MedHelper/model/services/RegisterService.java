package com.holzhausen.MedHelper.model.services;

import com.holzhausen.MedHelper.model.entities.User;
import com.holzhausen.MedHelper.model.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class RegisterService {

    private UserRepository repository;

    private BCryptPasswordEncoder encoder;

    private EmailService service;

    private TemplateEngine engine;


    public RegisterService(UserRepository repository, BCryptPasswordEncoder encoder,
                           EmailService service, TemplateEngine engine) {
        this.repository = repository;
        this.encoder = encoder;
        this.service = service;
        this.engine = engine;
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

    public void register(User user){

        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedPassword = RandomStringUtils.random(length, useLetters, useNumbers);
        user.setPassword(encoder.encode(generatedPassword));

//        BasicTextEncryptor encryptor = new BasicTextEncryptor();
//        encryptor.setPasswordCharArray((user.getImie()+user.getNazwisko()).toCharArray());
//        user.setNrDowodu(encryptor.encrypt(user.getNrDowodu()));
//        user.setPesel(encryptor.encrypt(user.getPesel()));
        User newUser = repository.save(user);

        Context context = new Context();
        context.setVariable("password", generatedPassword);
        context.setVariable("userId", newUser.getUserId());
        String message = engine.process("confirmTemplate", context);

        service.sendEmail("ph.cool97@gmail.com", "MedHelper-Rejestracja", message);

    }

    public boolean confirmAccount(int id){

        User user = repository.findByUserId(id);

        if(!user.isCzyKontoPotwierdzone()){
            user.setCzyKontoPotwierdzone(true);
            repository.save(user);
            return true;
        }
        return false;
    }

    public void changePassword(int id, String newPassword){

        User user = repository.findByUserId(id);
        System.out.println(id);
        user.setPassword(encoder.encode(newPassword));
        repository.save(user);

    }

}
