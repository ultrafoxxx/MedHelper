package com.holzhausen.MedHelper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogowanieController {

    @GetMapping(value = "/logowanie")
    public ModelAndView logowanie(){
        ModelAndView view = new ModelAndView();
        view.setViewName("MedHelperLogowanie");
        return view;
    }
}
