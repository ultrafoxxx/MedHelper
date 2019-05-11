package com.holzhausen.MedHelper.controller;

import com.holzhausen.MedHelper.model.entities.Pacjent;
import com.holzhausen.MedHelper.model.entities.Placowka;
import com.holzhausen.MedHelper.model.services.AddAgencyService;
import com.holzhausen.MedHelper.model.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/addAgency")
public class AddAgencyController {
    private AddAgencyService service;
    @Autowired
    public AddAgencyController(AddAgencyService service) {
        this.service = service;
    }
    /**
     * Aby można było wypełnić formularz, do widoku należy przesłać pusty obiekt, który zawiera zmienne odpowiadające
     * polom formularza.
     * @return
     */
    @GetMapping(value = "")
    public ModelAndView addAgencySite(){
        ModelAndView modelAndView = new ModelAndView();
        Placowka placowka = new Placowka();
        modelAndView.addObject("placowka", placowka);
        modelAndView.setViewName("adminpanel/addAgency");
        return modelAndView;
    }
    /**
     * W przypadku wysyłania formularzy, które walidujemy, w kontrolerze na wejściu trzeba pobrać wypełnione dane
     * z adnotacją @Valid, a także obiekt BindingResult, tak jak widać.
     * W kontrolerze można przeprowadzić dodatkową walidację, niż tą zdefiniowaną w obiekcie Pacjent (pierwszy if)
     * a następnie jeżeli wystąpiły problemy z walidacją to drugi if, jeżeli nie to else.
     * @param //pacjent
     * @param //bindingResult
     * @return
     */
    @PostMapping(value = "")
    public ModelAndView add(@Valid Placowka placowka, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("adminpanel/addAgency");
        }
        else {
            service.add(placowka);
            modelAndView.addObject("successMessage", "Agency has been added successfully");
            modelAndView.addObject("placowka", new Placowka());
            modelAndView.setViewName("adminpanel/addAgency");
        }
        return modelAndView;
    }


}
