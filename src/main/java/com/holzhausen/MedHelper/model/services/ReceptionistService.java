package com.holzhausen.MedHelper.model.services;

import com.holzhausen.MedHelper.model.entities.User;
import com.holzhausen.MedHelper.model.formclasses.Credential;
import com.holzhausen.MedHelper.model.repositories.UserRepository;
import com.holzhausen.MedHelper.model.repositories.WizytaProjection;
import com.holzhausen.MedHelper.model.repositories.WizytaProjectionImpl;
import com.holzhausen.MedHelper.model.repositories.WizytaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReceptionistService {

    private UserRepository userRepository;
    private WizytaRepository wizytaRepository;

    @Autowired
    public ReceptionistService(UserRepository userRepository, WizytaRepository wizytaRepository) {
        this.userRepository = userRepository;
        this.wizytaRepository = wizytaRepository;
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
            List<User> firstOption = userRepository.findPatientsStartingWith(firstArgument, lastArgument);
            List<User> secondOption = userRepository.findPatientsStartingWith(lastArgument, firstArgument);
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
            User user = userRepository.findByPesel(info[2]);
            return user;
        }
    }

    public User findUserById(int userId){
        return userRepository.findByUserId(userId);
    }

    public User updateUserInfo(User user){
        return userRepository.save(user);
    }

    public List<WizytaProjectionImpl> getVisists(int userId){
        List<WizytaProjection> visits = userRepository.getVisitsForPatient(userId);

        List<WizytaProjectionImpl> translatedVisits= new ArrayList<>();
        Date date = new Date();
        for(WizytaProjection visit : visits){
            WizytaProjectionImpl newVisit = new WizytaProjectionImpl();
            newVisit.setId(visit.getId());
            newVisit.setAdres(visit.getAdres());
            newVisit.setData(visit.getData());
            newVisit.setImie(visit.getImie());
            newVisit.setMiasto(visit.getMiasto());
            newVisit.setNazwisko(visit.getNazwisko());
            newVisit.setNrSali(visit.getNrSali());
            newVisit.setTime(visit.getTime());
            if(newVisit.getTime().before(new Time(date.getTime())) && (newVisit.getData().before(date))){
                newVisit.setActual(false);
                newVisit.setColor("alert alert-danger");
            }
            else if(newVisit.getTime().before(new Time(date.getTime())) && newVisit.getData().equals(date)){
                newVisit.setActual(false);
                newVisit.setColor("alert alert-warning");
            }
            else {
                newVisit.setActual(true);
                newVisit.setColor("alert alert-success");
            }
            translatedVisits.add(newVisit);
        }
        return translatedVisits;
    }

    public void removeVisit(int visitId){
        wizytaRepository.deleteById(visitId);
    }
}
