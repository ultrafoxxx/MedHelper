package com.holzhausen.MedHelper.controller;

import com.holzhausen.MedHelper.model.formclasses.RepeatPassword;
import com.holzhausen.MedHelper.model.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/activation")
public class ActivationController {

    private RegisterService service;

    @Autowired
    public ActivationController(RegisterService service) {
        this.service = service;
    }

    @GetMapping(value = "/confirmAccount")
    public ModelAndView confirmAccount(@RequestParam(name = "userId") int userId){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("confirmAccount");
        boolean isConfirmed = service.confirmAccount(userId);
        modelAndView.addObject("isConfirmed", isConfirmed);
        return modelAndView;

    }

    @GetMapping(value = "/changePassword")
    public ModelAndView changePassword(@RequestParam(name = "userId") int userId,
                                       @RequestParam(name = "confirm") boolean confirm){
        ModelAndView modelAndView = new ModelAndView();
        if(confirm){
            boolean isConfirmed = service.confirmAccount(userId);
            if(!isConfirmed){
                modelAndView.addObject("isConfirmed", false);
                modelAndView.setViewName("confirmAccount");
                return modelAndView;
            }
        }
        modelAndView.setViewName("newPassword");
        RepeatPassword repeatPassword = new RepeatPassword();
        repeatPassword.setUserId(userId);
        modelAndView.addObject("repeatPassword", repeatPassword);
        return modelAndView;

    }

    @PostMapping(value = "/changePassword")
    public ModelAndView submitNewPassword(@Valid RepeatPassword repeatPassword, BindingResult result){
        ModelAndView modelAndView = new ModelAndView();
        if(!repeatPassword.getPassword().equals(repeatPassword.getRepeatedPassword())){
            result.rejectValue("repeatedPassword", "error.repeatPassword", "Obie wartości nie zgadzają się");
        }
        if(result.hasErrors()){
            modelAndView.setViewName("newPassword");
        }
        else{
            service.changePassword(repeatPassword.getUserId(), repeatPassword.getPassword());
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }
}
