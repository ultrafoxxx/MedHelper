package com.holzhausen.MedHelper.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/map")
public class FindOnMapController {

    @GetMapping(value = "")
    public ModelAndView getMap(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("findOnMap");
        return modelAndView;

    }

}
