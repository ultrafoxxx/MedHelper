package com.holzhausen.MedHelper.controller;

import com.holzhausen.MedHelper.model.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LogowanieController {

    @GetMapping(value = "/logowanie")
    public ModelAndView logowanie(){
        ModelAndView view = new ModelAndView();
        view.setViewName("MedHelperLogowanie");
        return view;
    }



}
