package com.holzhausen.MedHelper.controller;


import com.holzhausen.MedHelper.model.entities.Placowka;
import com.holzhausen.MedHelper.model.services.FindOnMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/map")
public class FindOnMapController {

    private FindOnMapService service;

    @Autowired
    public FindOnMapController(FindOnMapService service) {
        this.service = service;
    }

    @GetMapping(value = "")
    public ModelAndView getMap(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("findOnMap");
        return modelAndView;
    }

    @GetMapping(value = "/getCities")
    @ResponseBody
    public ResponseEntity<List<String>> getCitiesList(@RequestParam("city") String city){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("accept", "application/json");

        return new ResponseEntity<>(service.getPlacesWithCityContaining(city), httpHeaders, HttpStatus.OK);


    }

    @GetMapping(value = "/getAddresses")
    @ResponseBody
    public List<String> getAddressesList(@RequestParam("city") String city, @RequestParam("adress") String address){

        return  service.getPlacesWithCityAndAddressCotaining(city, address);

    }

}
