package com.holzhausen.MedHelper.controller;

import com.holzhausen.MedHelper.model.entities.Pacjent;
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
@RequestMapping(value = "/register")
public class RegisterController {

    private RegisterService service;

    @Autowired
    public RegisterController(RegisterService service) {
        this.service = service;
    }

    /**
     * Aby można było wypełnić formularz, do widoku należy przesłać pusty obiekt, który zawiera zmienne odpowiadające
     * polom formularza.
     * @return
     */
    @GetMapping(value = "")
    public ModelAndView registerSite(){
        ModelAndView modelAndView = new ModelAndView();
        Pacjent pacjent = new Pacjent();
        modelAndView.addObject("pacjent", pacjent);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    /**
     * W przypadku wysyłania formularzy, które walidujemy, w kontrolerze na wejściu trzeba pobrać wypełnione dane
     * z adnotacją @Valid, a także obiekt BindingResult, tak jak widać.
     * W kontrolerze można przeprowadzić dodatkową walidację, niż tą zdefiniowaną w obiekcie Pacjent (pierwszy if)
     * a następnie jeżeli wystąpiły problemy z walidacją to drugi if, jeżeli nie to else.
     * @param pacjent
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "")
    public ModelAndView register(@Valid Pacjent pacjent, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(service.doesUserExists(pacjent.getEmail())){
            bindingResult.rejectValue("email", "error.pacjent", "Już istnieje pacjent o podanym emailu");
        }
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("register");
        }
        else {
            service.register(pacjent);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("pacjent", new Pacjent());
            modelAndView.setViewName("register");
        }
        return modelAndView;
    }


}
