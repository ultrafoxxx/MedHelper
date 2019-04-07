package com.holzhausen.MedHelper.model.services;

import com.holzhausen.MedHelper.model.entities.User;
import com.holzhausen.MedHelper.model.formclasses.Credential;
import com.holzhausen.MedHelper.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReceptionistService {

    private UserRepository repository;

    @Autowired
    public ReceptionistService(UserRepository repository) {
        this.repository = repository;
    }

    public List<Credential> findUserByName(String fullName){

        String[] name = fullName.split(" ");
        List<User> foundUsers = new ArrayList<>();
        if(name.length>0){
            String firstArgument = name[0];
            String lastArgument;
            if(name.length>1){
                lastArgument = name[1];
            }
            else {
                lastArgument = "";
            }
            List<User> firstOption = repository.findPatientsStartingWith(firstArgument, lastArgument);
            List<User> secondOption = repository.findPatientsStartingWith(lastArgument, firstArgument);
            foundUsers.addAll(firstOption);
            foundUsers.addAll(secondOption);
        }
        List<Credential> credentials = new ArrayList<>();
        for(int i=0;i<foundUsers.size();i++){
            credentials.add(new Credential(foundUsers.get(i).getImie(), foundUsers.get(i).getNazwisko(),
                    foundUsers.get(i).getPesel()));
        }
        return credentials;
    }

    public User getUserBySearchInfo(String searchInfo){

        String[] info = searchInfo.split(" ");
        if(info.length!=3){
            return null;
        }
        else {
            User user = repository.findByPesel(info[2]);
            return user;
        }
    }

    public User findUserById(int userId){
        return repository.findByUserId(userId);
    }

    public User updateUserInfo(User user){
        return repository.save(user);
    }
}
