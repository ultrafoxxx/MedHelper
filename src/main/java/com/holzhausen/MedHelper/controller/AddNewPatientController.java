package com.holzhausen.MedHelper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AddNewPatientController {

    @GetMapping(value = "/addNewPatient")
    public ModelAndView index(){
        ModelAndView view = new ModelAndView();
        view.setViewName("addNewPatient");
        return view;
    }

}
