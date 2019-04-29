package com.holzhausen.MedHelper.controller;

import com.holzhausen.MedHelper.model.entities.Specjalnosc;
import com.holzhausen.MedHelper.model.services.ReserveVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/reserveVisit")
public class ReserveVisitController {

    private ReserveVisitService service;

    @Autowired
    public ReserveVisitController(ReserveVisitService service) {
        this.service = service;
    }

    @GetMapping(value = "")
    public ModelAndView reserveVisitPanel(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("visitreservation/reservationPatient");
        List<Specjalnosc> specialties = service.getSpecialties();
        modelAndView.addObject("specialties", specialties);
        return modelAndView;
    }

}
