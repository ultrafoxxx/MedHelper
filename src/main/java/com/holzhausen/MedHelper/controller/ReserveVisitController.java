package com.holzhausen.MedHelper.controller;

import com.holzhausen.MedHelper.model.entities.Specjalnosc;
import com.holzhausen.MedHelper.model.formclasses.ReserveSearch;
import com.holzhausen.MedHelper.model.projections.ReserveVisitItemProjectionImpl;
import com.holzhausen.MedHelper.model.projections.TimeVisitsProjectionImpl;
import com.holzhausen.MedHelper.model.projections.WizytaProjectionImpl;
import com.holzhausen.MedHelper.model.services.ReserveVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
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

    @PostMapping(value = "/getVisitItems")
    @ResponseBody
    public List<ReserveVisitItemProjectionImpl> getVisitItems(@RequestBody ReserveSearch search){
        return service.getDoctorsWithVisists(search.getDate(),search.getPlaceName(),search.getSpecialty());
    }

    @PostMapping(value = "/getVisitTimes")
    @ResponseBody
    public List<TimeVisitsProjectionImpl> getVisitTimes(@RequestBody ReserveSearch search){
        return service.getDoctorVisits(search.getDate(),search.getPlaceId(),search.getDoctorId());
    }

    @GetMapping(value = "/confirm")
    public ModelAndView reservationDetails(@RequestParam("visitId") int visitId){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("visitreservation/reservationDetails");
        WizytaProjectionImpl queryResult = service.getVisitGivenId(visitId);
        modelAndView.addObject("visitDetails", queryResult);
        modelAndView.addObject("visitId", visitId);
        return modelAndView;

    }

    @PostMapping(value = "/confirm")
    public ModelAndView reserveVisit(@RequestParam("visitId") int visitId, Principal principal){

        service.reserveVisit(visitId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/reserveVisit");
        return modelAndView;
    }

}
