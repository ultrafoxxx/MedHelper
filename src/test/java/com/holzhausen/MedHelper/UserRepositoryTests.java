package com.holzhausen.MedHelper;

import com.holzhausen.MedHelper.model.entities.Pacjent;
import com.holzhausen.MedHelper.model.entities.User;
import com.holzhausen.MedHelper.model.repositories.PlacowkaRepository;
import com.holzhausen.MedHelper.model.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    TestEntityManager manager;

    @Autowired
    UserRepository repository;

    @Test
    public void whenGivenFirstNameReturnPatient(){

        Pacjent patient1 = new Pacjent();
        patient1.setImie("Jan");
        patient1.setNazwisko("Sobieski");
        patient1.setEmail("agda@gmail.com");
        patient1.setPesel("57654123697");
        patient1.setPassword("adfds454D");
        patient1.setNrDowodu("fa5s7d");
        patient1.setNrTelefonu("589732015");
        manager.persist(patient1);
        manager.flush();

        Pacjent patient2 = new Pacjent();
        patient2.setImie("Piotr");
        patient2.setNazwisko("Holzhausen");
        patient2.setEmail("ph.cool97@gmail.com");
        patient2.setPesel("57654123693");
        patient2.setPassword("adfds414D");
        patient2.setNrDowodu("fa5s4d");
        patient2.setNrTelefonu("589712015");
        manager.persist(patient2);
        manager.flush();

        Pacjent patient3 = new Pacjent();
        patient3.setImie("Janina");
        patient3.setNazwisko("Holska");
        patient3.setEmail("jHolska@gmail.com");
        patient3.setPesel("57614123697");
        patient3.setPassword("adfd0454D");
        patient3.setNrDowodu("fa5n7d");
        patient3.setNrTelefonu("589732055");
        manager.persist(patient3);
        manager.flush();

        List<User> expectedOutcome = new ArrayList<>();
        expectedOutcome.add(patient1);
        expectedOutcome.add(patient3);

        List<User> result = repository.findPatientsStartingWith("Ja", "");

        assertEquals(expectedOutcome, result);

    }

    @Test
    public void whenGivenLastNameReturnPatient(){
        Pacjent patient1 = new Pacjent();
        patient1.setImie("Jan");
        patient1.setNazwisko("Sobieski");
        patient1.setEmail("agda@gmail.com");
        patient1.setPesel("57654123697");
        patient1.setPassword("adfds454D");
        patient1.setNrDowodu("fa5s7d");
        patient1.setNrTelefonu("589732015");
        manager.persist(patient1);
        manager.flush();

        Pacjent patient2 = new Pacjent();
        patient2.setImie("Piotr");
        patient2.setNazwisko("Holzhausen");
        patient2.setEmail("ph.cool97@gmail.com");
        patient2.setPesel("57654123693");
        patient2.setPassword("adfds414D");
        patient2.setNrDowodu("fa5s4d");
        patient2.setNrTelefonu("589712015");
        manager.persist(patient2);
        manager.flush();

        Pacjent patient3 = new Pacjent();
        patient3.setImie("Janina");
        patient3.setNazwisko("Holska");
        patient3.setEmail("jHolska@gmail.com");
        patient3.setPesel("57614123697");
        patient3.setPassword("adfd0454D");
        patient3.setNrDowodu("fa5n7d");
        patient3.setNrTelefonu("589732055");
        manager.persist(patient3);
        manager.flush();

        List<User> expectedOutcome = new ArrayList<>();
        expectedOutcome.add(patient2);
        expectedOutcome.add(patient3);

        List<User> result = repository.findPatientsStartingWith("", "Hol");

        assertEquals(expectedOutcome, result);
    }

    @Test
    public void whenGivenFullNameReturnPatient(){
        Pacjent patient1 = new Pacjent();
        patient1.setImie("Jan");
        patient1.setNazwisko("Sobieski");
        patient1.setEmail("agda@gmail.com");
        patient1.setPesel("57654123697");
        patient1.setPassword("adfds454D");
        patient1.setNrDowodu("fa5s7d");
        patient1.setNrTelefonu("589732015");
        manager.persist(patient1);
        manager.flush();

        Pacjent patient2 = new Pacjent();
        patient2.setImie("Piotr");
        patient2.setNazwisko("Holzhausen");
        patient2.setEmail("ph.cool97@gmail.com");
        patient2.setPesel("57654123693");
        patient2.setPassword("adfds414D");
        patient2.setNrDowodu("fa5s4d");
        patient2.setNrTelefonu("589712015");
        manager.persist(patient2);
        manager.flush();

        Pacjent patient3 = new Pacjent();
        patient3.setImie("Janina");
        patient3.setNazwisko("Holska");
        patient3.setEmail("jHolska@gmail.com");
        patient3.setPesel("57614123697");
        patient3.setPassword("adfd0454D");
        patient3.setNrDowodu("fa5n7d");
        patient3.setNrTelefonu("589732055");
        manager.persist(patient3);
        manager.flush();

        List<User> expectedOutcome = new ArrayList<>();
        expectedOutcome.add(patient2);

        List<User> result = repository.findPatientsStartingWith("Piotr", "Hol");

        assertEquals(expectedOutcome, result);
    }

}
