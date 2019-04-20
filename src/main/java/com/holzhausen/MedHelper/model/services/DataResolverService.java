package com.holzhausen.MedHelper.model.services;

import com.holzhausen.MedHelper.model.entities.*;
import com.holzhausen.MedHelper.model.enums.Forma;
import com.holzhausen.MedHelper.model.repositories.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Scanner;

@Service
public class DataResolverService {

    private UserRepository userRepository;

    private PlacowkaRepository placowkaRepository;

    private LekRepository lekRepository;

    private BCryptPasswordEncoder encoder;

    private SpecjalnoscRepository specjalnoscRepository;

    private SpecjalnoscLekarzRepository specjalnoscLekarzRepository;

    private static final String[] placowkaColumns = {"miasto", "adres", "dlugoscGeo", "szerGeo", "telefon"};

    private static final String[] userColumns = {"rola", "email", "password", "nazwisko", "imie", "pesel", "nrTelefonu",
            "nrDowodu", "specjalnosc"};

    private static final String[] lekColums = {"nazwa", "dawka", "forma"};

    public DataResolverService(UserRepository userRepository, PlacowkaRepository placowkaRepository,
                               LekRepository lekRepository, BCryptPasswordEncoder encoder,
                               SpecjalnoscRepository specjalnoscRepository,
                               SpecjalnoscLekarzRepository specjalnoscLekarzRepository) {
        this.userRepository = userRepository;
        this.placowkaRepository = placowkaRepository;
        this.lekRepository = lekRepository;
        this.encoder = encoder;
        this.specjalnoscRepository = specjalnoscRepository;
        this.specjalnoscLekarzRepository = specjalnoscLekarzRepository;
    }

    @Async
    public void resolveCSVData(MultipartFile file, String tableName) {

        File dataFile = new File(file.getOriginalFilename());
        Scanner scanner;
        JpaRepository repository;
        String[] columnTable;
        if (tableName.equals("Placowka")){
            columnTable = placowkaColumns;
        }
        else if(tableName.equals("User")){
            columnTable = userColumns;
        }
        else if(tableName.equals("Lek")){
            columnTable = lekColums;
        }
        else {
            throw new InvalidParameterException("Value of tableName property is invalid.");
        }
        try {

            scanner = new Scanner(file.getInputStream());
            String[] columnNames = scanner.nextLine().split(";");
            HashMap<String, Integer> tableMapping = new HashMap<>();

            for(int i=0;i<columnNames.length;i++){
                boolean equals = false;
                for(int j=0;j<columnTable.length;j++){
                    if(columnNames[i].equals(columnTable[j])){
                        equals = true;
                        tableMapping.put(columnNames[i], i);
                    }
                }
                if(!equals){
                    throw new InvalidParameterException("Your file is incorrect");
                }
            }

            while (scanner.hasNextLine()){
                String[] parameters = scanner.nextLine().split(";");
                if (tableName.equals("Placowka")){
                    Placowka placowka = new Placowka();
                    placowka.setMiasto(parameters[tableMapping.get(placowkaColumns[0])]);
                    placowka.setAdres(parameters[tableMapping.get(placowkaColumns[1])]);
                    placowka.setDlugoscGeo(Double.parseDouble(parameters[tableMapping.get(placowkaColumns[2])]));
                    placowka.setSzerGeo(Double.parseDouble(parameters[tableMapping.get(placowkaColumns[3])]));
                    placowka.setTelefon(parameters[tableMapping.get(placowkaColumns[4])]);
                    placowkaRepository.save(placowka);
                }
                else if(tableName.equals("User")){
                    User user;
                    if(parameters[tableMapping.get(userColumns[0])].equals(User.roles[0])){
                        user = new Pacjent();
                    }
                    else if(parameters[tableMapping.get(userColumns[0])].equals(User.roles[1])){
                        user = new Recepcjonista();
                    }
                    else if(parameters[tableMapping.get(userColumns[0])].equals(User.roles[2])){
                        user = new Lekarz();
                    }
                    else if(parameters[tableMapping.get(userColumns[0])].equals(User.roles[3])){
                        user = new Administrator();
                    }
                    else {
                        throw new InvalidParameterException("There is problem with data you provided");
                    }
                    user.setEmail(parameters[tableMapping.get(userColumns[1])]);
                    user.setPassword(encoder.encode(parameters[tableMapping.get(userColumns[2])]));
                    user.setNazwisko(parameters[tableMapping.get(userColumns[3])]);
                    user.setImie(parameters[tableMapping.get(userColumns[4])]);
                    user.setPesel(parameters[tableMapping.get(userColumns[5])]);
                    user.setNrTelefonu(parameters[tableMapping.get(userColumns[6])]);
                    user.setNrDowodu(parameters[tableMapping.get(userColumns[7])]);

                    User savedUser = userRepository.save(user);
                    if(user instanceof Lekarz){
                        SpecjalnoscLekarz specjalnoscLekarz = new SpecjalnoscLekarz();
                        specjalnoscLekarz.setLekarz((Lekarz)savedUser);
                        Specjalnosc specjalnosc = specjalnoscRepository
                                .getByName(parameters[tableMapping.get(userColumns[8])]);
                        if(specjalnosc != null){
                            specjalnoscLekarz.setSpecjalnosc(specjalnosc);
                            specjalnoscLekarzRepository.save(specjalnoscLekarz);
                        }
                        else {
                            throw new InvalidParameterException("There is problem with data you provided");
                        }
                    }
                }
                else {
                    Lek lek = new Lek();
                    lek.setNazwa(parameters[tableMapping.get(lekColums[0])]);
                    lek.setDawka(Float.parseFloat(parameters[tableMapping.get(lekColums[1])]));
                    lek.setForma(Forma.valueOf(parameters[tableMapping.get(lekColums[2])]));
                    lekRepository.save(lek);
                }

            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
            throw new InvalidParameterException("There is problem with data you provided");
        }
    }
}
