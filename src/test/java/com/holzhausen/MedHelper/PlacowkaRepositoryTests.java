package com.holzhausen.MedHelper;

import com.holzhausen.MedHelper.model.entities.Placowka;
import com.holzhausen.MedHelper.model.repositories.PlacowkaRepository;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PlacowkaRepositoryTests {

    @Autowired
    TestEntityManager manager;

    @Autowired
    PlacowkaRepository repository;

    @Test
    public void whenGivenAPartOfCityNameReturnPlaces(){

        Placowka placowka1 = new Placowka();
        placowka1.setMiasto("Wrocław");
        placowka1.setAdres("Kanarkowa 20");
        placowka1.setDlugoscGeo(54.22);
        placowka1.setSzerGeo(56.15);
        placowka1.setTelefon("648849848");
        manager.persist(placowka1);
        manager.flush();

        Placowka placowka2 = new Placowka();
        placowka2.setMiasto("Warszawa");
        placowka2.setAdres("Marszałkowska 20");
        placowka2.setDlugoscGeo(51.22);
        placowka2.setSzerGeo(46.15);
        placowka2.setTelefon("6458449848");
        manager.persist(placowka2);
        manager.flush();

        Placowka placowka3 = new Placowka();
        placowka3.setMiasto("Wrocław");
        placowka3.setAdres("Ptysiowa 20");
        placowka3.setDlugoscGeo(54.12);
        placowka3.setSzerGeo(56.19);
        placowka3.setTelefon("648758588");
        manager.persist(placowka3);
        manager.flush();

        List<Placowka> expectedOutcome = new ArrayList<>();

        expectedOutcome.add(placowka1);
        expectedOutcome.add(placowka3);

        List<Placowka> outcome = repository.findDistinctByMiastoContainingIgnoreCase("Wrocław");

        assertEquals(outcome, expectedOutcome);


    }

    @Test
    public void whenGivenAPartOfCityNameReturnPlacesSecond(){

        Placowka placowka1 = new Placowka();
        placowka1.setMiasto("Wrocław");
        placowka1.setAdres("Kanarkowa 20");
        placowka1.setDlugoscGeo(54.22);
        placowka1.setSzerGeo(56.15);
        placowka1.setTelefon("648849848");
        manager.persist(placowka1);
        manager.flush();

        Placowka placowka2 = new Placowka();
        placowka2.setMiasto("Warszawa");
        placowka2.setAdres("Marszałkowska 20");
        placowka2.setDlugoscGeo(51.22);
        placowka2.setSzerGeo(46.15);
        placowka2.setTelefon("6458449848");
        manager.persist(placowka2);
        manager.flush();

        Placowka placowka3 = new Placowka();
        placowka3.setMiasto("Wrocław");
        placowka3.setAdres("Ptysiowa 20");
        placowka3.setDlugoscGeo(54.12);
        placowka3.setSzerGeo(56.19);
        placowka3.setTelefon("648758588");
        manager.persist(placowka3);
        manager.flush();

        List<Placowka> expectedOutcome = new ArrayList<>();

        expectedOutcome.add(placowka1);
        expectedOutcome.add(placowka2);
        expectedOutcome.add(placowka3);

        List<Placowka> outcome = repository.findDistinctByMiastoContainingIgnoreCase("W");

        assertEquals(outcome, expectedOutcome);


    }

    @Test
    public void whenGivenAPartOfCityNameReturnPlacesThird(){

        Placowka placowka1 = new Placowka();
        placowka1.setMiasto("Wrocław");
        placowka1.setAdres("Kanarkowa 20");
        placowka1.setDlugoscGeo(54.22);
        placowka1.setSzerGeo(56.15);
        placowka1.setTelefon("648849848");
        manager.persist(placowka1);
        manager.flush();

        Placowka placowka2 = new Placowka();
        placowka2.setMiasto("Warszawa");
        placowka2.setAdres("Marszałkowska 20");
        placowka2.setDlugoscGeo(51.22);
        placowka2.setSzerGeo(46.15);
        placowka2.setTelefon("6458449848");
        manager.persist(placowka2);
        manager.flush();

        Placowka placowka3 = new Placowka();
        placowka3.setMiasto("Wrocław");
        placowka3.setAdres("Ptysiowa 20");
        placowka3.setDlugoscGeo(54.12);
        placowka3.setSzerGeo(56.19);
        placowka3.setTelefon("648758588");
        manager.persist(placowka3);
        manager.flush();

        List<Placowka> expectedOutcome = new ArrayList<>();

        expectedOutcome.add(placowka2);


        List<Placowka> outcome = repository.findDistinctByMiastoContainingIgnoreCase("szawa");

        assertEquals(outcome, expectedOutcome);


    }

    @Test
    public void whenGivenAPartOfCityNameReturnPlacesFourth() {

        Placowka placowka1 = new Placowka();
        placowka1.setMiasto("Wrocław");
        placowka1.setAdres("Kanarkowa 20");
        placowka1.setDlugoscGeo(54.22);
        placowka1.setSzerGeo(56.15);
        placowka1.setTelefon("648849848");
        manager.persist(placowka1);
        manager.flush();

        Placowka placowka2 = new Placowka();
        placowka2.setMiasto("Warszawa");
        placowka2.setAdres("Marszałkowska 20");
        placowka2.setDlugoscGeo(51.22);
        placowka2.setSzerGeo(46.15);
        placowka2.setTelefon("6458449848");
        manager.persist(placowka2);
        manager.flush();

        Placowka placowka3 = new Placowka();
        placowka3.setMiasto("Wrocław");
        placowka3.setAdres("Ptysiowa 20");
        placowka3.setDlugoscGeo(54.12);
        placowka3.setSzerGeo(56.19);
        placowka3.setTelefon("648758588");
        manager.persist(placowka3);
        manager.flush();

        List<Placowka> expectedOutcome = new ArrayList<>();

        expectedOutcome.add(placowka1);
        expectedOutcome.add(placowka3);

        List<Placowka> outcome = repository.findDistinctByMiastoContainingIgnoreCase("wrocław");


        assertEquals(expectedOutcome, outcome);
    }

    @Test
    public void whenGivenAPartOfCityNameReturnPlacesFith() {

        Placowka placowka1 = new Placowka();
        placowka1.setMiasto("Wrocław");
        placowka1.setAdres("Kanarkowa 20");
        placowka1.setDlugoscGeo(54.22);
        placowka1.setSzerGeo(56.15);
        placowka1.setTelefon("648849848");
        manager.persist(placowka1);
        manager.flush();

        Placowka placowka2 = new Placowka();
        placowka2.setMiasto("Warszawa");
        placowka2.setAdres("Marszałkowska 20");
        placowka2.setDlugoscGeo(51.22);
        placowka2.setSzerGeo(46.15);
        placowka2.setTelefon("6458449848");
        manager.persist(placowka2);
        manager.flush();

        Placowka placowka3 = new Placowka();
        placowka3.setMiasto("Wrocław");
        placowka3.setAdres("Ptysiowa 20");
        placowka3.setDlugoscGeo(54.12);
        placowka3.setSzerGeo(56.19);
        placowka3.setTelefon("648758588");
        manager.persist(placowka3);
        manager.flush();

        List<Placowka> expectedOutcome = new ArrayList<>();

        expectedOutcome.add(placowka1);
        expectedOutcome.add(placowka2);
        expectedOutcome.add(placowka3);



        List<Placowka> outcome = repository.findDistinctByMiastoContainingIgnoreCase("");


        assertEquals(expectedOutcome, outcome);
    }

    @Test
    public void whenGivenAPartOfAdressAndCityNameReturnPlaces() {

        Placowka placowka1 = new Placowka();
        placowka1.setMiasto("Wrocław");
        placowka1.setAdres("Kanarkowa 20");
        placowka1.setDlugoscGeo(54.22);
        placowka1.setSzerGeo(56.15);
        placowka1.setTelefon("648849848");
        manager.persist(placowka1);
        manager.flush();

        Placowka placowka2 = new Placowka();
        placowka2.setMiasto("Warszawa");
        placowka2.setAdres("Marszałkowska 20");
        placowka2.setDlugoscGeo(51.22);
        placowka2.setSzerGeo(46.15);
        placowka2.setTelefon("6458449848");
        manager.persist(placowka2);
        manager.flush();

        Placowka placowka3 = new Placowka();
        placowka3.setMiasto("Wrocław");
        placowka3.setAdres("Ptysiowa 20");
        placowka3.setDlugoscGeo(54.12);
        placowka3.setSzerGeo(56.19);
        placowka3.setTelefon("648758588");
        manager.persist(placowka3);
        manager.flush();

        List<Placowka> expectedOutcome = new ArrayList<>();

        expectedOutcome.add(placowka3);



        List<Placowka> outcome = repository
                .findAllByMiastoAndAdresContainingIgnoreCase("Wrocław", "Ptysiowa 20");


        assertEquals(expectedOutcome, outcome);
    }

    @Test
    public void whenGivenAPartOfAdressAndCityNameReturnPlacesSecond() {

        Placowka placowka1 = new Placowka();
        placowka1.setMiasto("Wrocław");
        placowka1.setAdres("Kanarkowa 20");
        placowka1.setDlugoscGeo(54.22);
        placowka1.setSzerGeo(56.15);
        placowka1.setTelefon("648849848");
        manager.persist(placowka1);
        manager.flush();

        Placowka placowka2 = new Placowka();
        placowka2.setMiasto("Warszawa");
        placowka2.setAdres("Marszałkowska 20");
        placowka2.setDlugoscGeo(51.22);
        placowka2.setSzerGeo(46.15);
        placowka2.setTelefon("6458449848");
        manager.persist(placowka2);
        manager.flush();

        Placowka placowka3 = new Placowka();
        placowka3.setMiasto("Wrocław");
        placowka3.setAdres("Ptysiowa 20");
        placowka3.setDlugoscGeo(54.12);
        placowka3.setSzerGeo(56.19);
        placowka3.setTelefon("648758588");
        manager.persist(placowka3);
        manager.flush();

        List<Placowka> expectedOutcome = new ArrayList<>();

        expectedOutcome.add(placowka2);



        List<Placowka> outcome = repository
                .findAllByMiastoAndAdresContainingIgnoreCase("Warszawa", "marszał");


        assertEquals(expectedOutcome, outcome);
    }

    @Test
    public void whenGivenAPartOfAdressAndCityNameReturnPlacesThird() {

        Placowka placowka1 = new Placowka();
        placowka1.setMiasto("Wrocław");
        placowka1.setAdres("Kanarkowa 20");
        placowka1.setDlugoscGeo(54.22);
        placowka1.setSzerGeo(56.15);
        placowka1.setTelefon("648849848");
        manager.persist(placowka1);
        manager.flush();

        Placowka placowka2 = new Placowka();
        placowka2.setMiasto("Warszawa");
        placowka2.setAdres("Marszałkowska 20");
        placowka2.setDlugoscGeo(51.22);
        placowka2.setSzerGeo(46.15);
        placowka2.setTelefon("6458449848");
        manager.persist(placowka2);
        manager.flush();

        Placowka placowka3 = new Placowka();
        placowka3.setMiasto("Wrocław");
        placowka3.setAdres("Ptysiowa 20");
        placowka3.setDlugoscGeo(54.12);
        placowka3.setSzerGeo(56.19);
        placowka3.setTelefon("648758588");
        manager.persist(placowka3);
        manager.flush();

        List<Placowka> expectedOutcome = new ArrayList<>();




        List<Placowka> outcome = repository
                .findAllByMiastoAndAdresContainingIgnoreCase("Wroc", "Ptysiowa 20");


        assertEquals(expectedOutcome, outcome);
    }




}
