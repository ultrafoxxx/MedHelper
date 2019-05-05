package com.holzhausen.MedHelper.controller;

import com.holzhausen.MedHelper.model.entities.Specjalnosc;
import com.holzhausen.MedHelper.model.formclasses.ReserveSearch;
import com.holzhausen.MedHelper.model.projections.PatientProjectionImpl;
import com.holzhausen.MedHelper.model.projections.ReserveVisitItemProjectionImpl;
import com.holzhausen.MedHelper.model.projections.TimeVisitsProjectionImpl;
import com.holzhausen.MedHelper.model.projections.WizytaProjectionImpl;
import com.holzhausen.MedHelper.model.services.AdminPanelService;
import com.holzhausen.MedHelper.model.services.ReserveVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
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
    public ModelAndView reservationDetails(@RequestParam("visitId") int visitId, Authentication authentication,
                                           HttpSession session, @ModelAttribute("patientData")String data){

        ModelAndView modelAndView = new ModelAndView();
        System.out.println(data);
        User user = (User)authentication.getPrincipal();
        if(user.getAuthorities().toString().equals("[Pacjent]")){
            WizytaProjectionImpl queryResult = service.getVisitGivenId(visitId);
            modelAndView.setViewName("visitreservation/reservationDetails");
            modelAndView.addObject("visitDetails", queryResult);
            modelAndView.addObject("visitId", visitId);
        }
        else if(user.getAuthorities().toString().equals("[Recepcjonista]")){
            modelAndView.setViewName("visitreservation/searchForPatient");
            if(data.isEmpty()){
                modelAndView.addObject("patients", new ArrayList<>());

            }
            else {
                List<PatientProjectionImpl> patients = service.getPatients(data, 0);
                modelAndView.addObject("patients", patients);

            }
            modelAndView.addObject("warn", false);
            session.setAttribute("visitId", visitId);
        }
        return modelAndView;

    }

    @PostMapping(value = "/confirm")
    public ModelAndView reserveVisit(@RequestParam("visitId") int visitId){

        service.reserveVisit(visitId, -1);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/reserveVisit");
        return modelAndView;
    }

    @PostMapping(value = "/getPatients")
    public RedirectView findPatients(@RequestParam("data") String data, RedirectAttributes attributes, HttpSession session){
        int visitId = (int)session.getAttribute("visitId");
        attributes.addAttribute("visitId", visitId);
        attributes.addFlashAttribute("patientData", data);
        return new RedirectView("confirm");
    }

    @GetMapping(value = "/receptionistReserve")
    public ModelAndView reserveForPatientConfirmation(@RequestParam("patientId") int patientId, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        int visitId = (int)session.getAttribute("visitId");
        session.setAttribute("patientId", patientId);
        session.removeAttribute("visitId");
        WizytaProjectionImpl queryResult = service.getVisitGivenId(visitId);
        modelAndView.setViewName("visitreservation/reservationDetails");
        modelAndView.addObject("visitDetails", queryResult);
        modelAndView.addObject("visitId", visitId);
        return modelAndView;
    }

    @PostMapping(value = "/receptionistReserve")
    public ModelAndView reserveVisitForPatient(@RequestParam("visitId") int visitId, HttpSession session){

        int patientId = (int)session.getAttribute("patientId");
        session.removeAttribute("patientId");
        service.reserveVisit(visitId, patientId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/reserveVisit");
        return modelAndView;
    }
}
