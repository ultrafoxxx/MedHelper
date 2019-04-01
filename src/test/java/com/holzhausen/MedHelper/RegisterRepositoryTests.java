package com.holzhausen.MedHelper;

import com.holzhausen.MedHelper.model.entities.Pacjent;
import com.holzhausen.MedHelper.model.entities.Recepcjonista;
import com.holzhausen.MedHelper.model.entities.User;
import com.holzhausen.MedHelper.model.repositories.PlacowkaRepository;
import com.holzhausen.MedHelper.model.repositories.UserRepository;
import com.holzhausen.MedHelper.model.services.FindOnMapService;
import com.holzhausen.MedHelper.model.services.RegisterService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RegisterRepositoryTests {

    @Autowired
    TestEntityManager manager;

    @Autowired
    UserRepository repository;


    @Test
    public void whenGivenEmail(){
        User user = new Recepcjonista();
        user.setCzyKontoPotwierdzone(true);
        user.setEmail("email@gmail.com");
        user.setImie("Anna");
        user.setNazwisko("Nowak");
        user.setNrDowodu("Du5435");
        user.setNrTelefonu("543456789");
        user.setPassword("Password");
        user.setPesel("97878776667");
        manager.persist(user);
        manager.flush();

        User user2 = new Pacjent();
        user2.setCzyKontoPotwierdzone(true);
        user2.setEmail("email2@gmail.com");
        user2.setImie("Kamil");
        user2.setNazwisko("Buk");
        user2.setNrDowodu("Du4444");
        user2.setNrTelefonu("111111111");
        user2.setPassword("P@ssword");
        user2.setPesel("11111111111");
        manager.persist(user2);
        manager.flush();

        User expectedOutcome = user;

        User outcome = repository.findByEmail("email@gmail.com");

        Assert.assertEquals(expectedOutcome,outcome);

    }

    @Test
    public void whenGivenId(){
        User user = new Recepcjonista();
        user.setCzyKontoPotwierdzone(true);
        user.setEmail("email@gmail.com");
        user.setImie("Anna");
        user.setNazwisko("Nowak");
        user.setNrDowodu("Du5435");
        user.setNrTelefonu("543456789");
        user.setPassword("Password");
        user.setPesel("97878776667");
        manager.persist(user);
        manager.flush();

        User user2 = new Pacjent();
        user2.setCzyKontoPotwierdzone(true);
        user2.setEmail("email2@gmail.com");
        user2.setImie("Kamil");
        user2.setNazwisko("Buk");
        user2.setNrDowodu("Du4444");
        user2.setNrTelefonu("111111111");
        user2.setPassword("P@ssword");
        user2.setPesel("11111111111");
        manager.persist(user2);
        manager.flush();

        User expectedOutcome = user2;

        User outcome = repository.findByUserId(2);

        Assert.assertEquals(expectedOutcome,outcome);
    }
}
