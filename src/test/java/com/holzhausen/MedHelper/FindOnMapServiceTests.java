package com.holzhausen.MedHelper;

import com.holzhausen.MedHelper.model.entities.Placowka;
<<<<<<< HEAD
=======

>>>>>>> a4c4274ee987e270fcea6de7b22c9352a76833e3
import com.holzhausen.MedHelper.model.repositories.PlacowkaRepository;
import com.holzhausen.MedHelper.model.services.FindOnMapService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class FindOnMapServiceTests {

    @Autowired
    FindOnMapService findOnMapService;

    @MockBean
    PlacowkaRepository findOnMapRepository;

<<<<<<< HEAD
//    @TestConfiguration
//    static class EmployeeServiceImplTestContextConfiguration {
//
//        @Autowired PlacowkaRepository findOnMapRepository;
//        @Bean
//        public FindOnMapService employeeService() {
//            return new FindOnMapService(findOnMapRepository);
//        }
//    }
=======
    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Autowired PlacowkaRepository findOnMapRepository;
        @Bean
        public FindOnMapService employeeService() {
            return new FindOnMapService(findOnMapRepository);
        }
    }
>>>>>>> a4c4274ee987e270fcea6de7b22c9352a76833e3

    @Before
    public void setUp(){

        List<Placowka> placowki = new ArrayList<>();

        Placowka placowka1 = new Placowka();
        placowka1.setMiasto("Wrocław");
        placowka1.setAdres("Kanarkowa 20");
        placowka1.setDlugoscGeo(54.22);
        placowka1.setSzerGeo(56.15);
        placowka1.setTelefon("648849848");
        placowki.add(placowka1);

        Placowka placowka2 = new Placowka();
        placowka2.setMiasto("Warszawa");
        placowka2.setAdres("Marszałkowska 20");
        placowka2.setDlugoscGeo(51.22);
        placowka2.setSzerGeo(46.15);
        placowka2.setTelefon("6458449848");
        placowki.add(placowka2);

        Placowka placowka3 = new Placowka();
        placowka3.setMiasto("Wrocław");
        placowka3.setAdres("Ptysiowa 20");
        placowka3.setDlugoscGeo(54.12);
        placowka3.setSzerGeo(56.19);
        placowka3.setTelefon("648758588");
        placowki.add(placowka3);

        List<Placowka> placowkiWro= new ArrayList<>();
        placowkiWro.add(placowka1);
        placowkiWro.add(placowka3);

<<<<<<< HEAD
        List<Placowka> placowkiWaw= new ArrayList<>();
        placowkiWaw.add(placowka2);

=======
        List<Placowka> placowkiWroP=new ArrayList<>();
        placowkiWroP.add(placowka3);
>>>>>>> a4c4274ee987e270fcea6de7b22c9352a76833e3


        Mockito.when(findOnMapRepository.findDistinctByMiastoContainingIgnoreCase("wr")).thenReturn(placowkiWro);

<<<<<<< HEAD
        Mockito.when(findOnMapRepository.findDistinctByMiastoContainingIgnoreCase("wa")).thenReturn(placowkiWaw);

        Mockito.when(findOnMapRepository.findDistinctByMiastoContainingIgnoreCase("w")).thenReturn(placowki);
    }

    @Test
    public void pobieranieMiast(){
=======

        Mockito.when(findOnMapRepository.findAllByMiastoAndAdresContainingIgnoreCase("Wrocław", "p")).thenReturn(placowkiWroP);

    }

    @Test
    public void pobieranieMiastWro(){
>>>>>>> a4c4274ee987e270fcea6de7b22c9352a76833e3
        List<String> otrzymane=findOnMapService.getPlacesWithCityContaining("wr");
        List<String> przewidywane = new ArrayList<>();
        przewidywane.add("Wrocław");

        Assert.assertEquals(przewidywane,otrzymane);
    }

<<<<<<< HEAD
=======
    @Test
    public void pobieranieMiastWroP(){
        List<String> otrzymane=findOnMapService.getPlacesWithCityAndAddressCotaining("Wrocław", "p");
        List<String> przewidywane = new ArrayList<>();
        przewidywane.add("Ptysiowa 20");

        Assert.assertEquals(przewidywane,otrzymane);
    }


>>>>>>> a4c4274ee987e270fcea6de7b22c9352a76833e3
}
