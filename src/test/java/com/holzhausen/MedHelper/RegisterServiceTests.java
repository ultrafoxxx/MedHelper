package com.holzhausen.MedHelper;

import com.holzhausen.MedHelper.model.entities.Pacjent;
import com.holzhausen.MedHelper.model.entities.Placowka;
import com.holzhausen.MedHelper.model.entities.Recepcjonista;
import com.holzhausen.MedHelper.model.entities.User;
import com.holzhausen.MedHelper.model.repositories.PlacowkaRepository;
import com.holzhausen.MedHelper.model.repositories.UserRepository;
import com.holzhausen.MedHelper.model.services.FindOnMapService;
import com.holzhausen.MedHelper.model.services.RegisterService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

public class RegisterServiceTests {

    @Autowired
    RegisterService registerService;

    @MockBean
    UserRepository registerRepository;

//    @TestConfiguration
//    static class EmployeeServiceImplTestContextConfiguration {
//
//        @Autowired UserRepository registerRepository;
//        @Bean
//        public RegisterService aService() {
//            return new RegisterService(registerRepository);
//        }
//    }

    @Before
    public void setUp(){
        User user = new Recepcjonista();
        user.setCzyKontoPotwierdzone(true);
        user.setEmail("email@gmail.com");
        user.setImie("Anna");
        user.setNazwisko("Nowak");
        user.setNrDowodu("Du5435");
        user.setNrTelefonu("543456789");
        user.setPassword("Password");
        user.setPesel("97878776667");

        User user2 = new Pacjent();
        user2.setCzyKontoPotwierdzone(true);
        user2.setEmail("email2@gmail.com");
        user2.setImie("Kamil");
        user2.setNazwisko("Buk");
        user2.setNrDowodu("Du4444");
        user2.setNrTelefonu("111111111");
        user2.setPassword("P@ssword");
        user2.setPesel("11111111111");

        Mockito.when(registerRepository.save(user)).thenReturn(user);
        Mockito.when(registerRepository.save(user2)).thenReturn(user2);
    }

    @Test
    public void registerTest(){
        User user = new Recepcjonista();
        user.setCzyKontoPotwierdzone(true);
        user.setEmail("email@gmail.com");
        user.setImie("Anna");
        user.setNazwisko("Nowak");
        user.setNrDowodu("Du5435");
        user.setNrTelefonu("543456789");
        user.setPassword("Password");
        user.setPesel("97878776667");

        Assert.assertNotEquals(user.getPassword(), registerService.register(user));
    }
}
