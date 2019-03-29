package com.holzhausen.MedHelper.controller;

import com.holzhausen.MedHelper.model.entities.Pacjent;
import com.holzhausen.MedHelper.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/logowanie")
public class LogowanieController {

    @Autowired
    JavaMailSender javaMailSender;

    @GetMapping(value = "")
    public ModelAndView logowanie(){
        ModelAndView view = new ModelAndView();
        view.setViewName("MedHelperLogowanie");
        return view;
    }

    @GetMapping(value = "/forgotPassword")
    public ModelAndView forgotPassword(){
        ModelAndView view = new ModelAndView();
        view.setViewName("forgotPassword");
        return view;
    }

    @PostMapping(value = "/resetPassword")
    public String resetPassword(@RequestParam("email") String email){

        System.out.println(email);
    return null;

    }



}
