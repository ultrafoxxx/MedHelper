package com.holzhausen.MedHelper.controller;

import com.holzhausen.MedHelper.model.entities.Lek;
import com.holzhausen.MedHelper.model.entities.Wizyta;
import com.holzhausen.MedHelper.model.formclasses.Visit;
import com.holzhausen.MedHelper.model.services.DoctorPanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/doctorPanel")
public class DoctorPanelController {

    private DoctorPanelService doctorPanelService;

    @Autowired
    public DoctorPanelController(DoctorPanelService doctorPanelService) {
        this.doctorPanelService = doctorPanelService;
    }

    @GetMapping(value = "")
    public ModelAndView getVisits(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("doctorpanel/acceptVisits");
        modelAndView.addObject("patients", doctorPanelService.getPatientsForVisits());
        return modelAndView;

    }

    @GetMapping(value = "/startVisit")
    public ModelAndView startVisit(@RequestParam(value = "visitId") int visitId){


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("doctorpanel/manageVisit");
        modelAndView.addObject("patient", doctorPanelService.getPatientForVisit(visitId));
        modelAndView.addObject("myVisitId", visitId);
        Wizyta wizyta = doctorPanelService.getVisitById(visitId);
        Visit visit = new Visit();
        visit.setVisitId(visitId);
        if(wizyta.getNotka() != null){
            visit.setNote(wizyta.getNotka());
        }
        visit.setHasEnded(false);
        modelAndView.addObject("visit", visit);
        return modelAndView;

    }

    @PostMapping(value = "/endVisit")
    public RedirectView endVisit(@Valid Visit visit, RedirectAttributes attributes, HttpSession session){

        if(!visit.isHasEnded()){
            doctorPanelService.endVisit(visit, true);
            if(session.getAttribute("visitId") != null){
                session.removeAttribute("visitId");
            }
            return new RedirectView("/doctorPanel");
        }
        else {
            doctorPanelService.endVisit(visit, false);
            attributes.addAttribute("visitId", visit.getVisitId());
            return new RedirectView("/doctorPanel/prescribe");
        }

    }

    @GetMapping(value = "/prescribe")
    public ModelAndView writePrescribtion(@RequestParam("visitId") int visitId, HttpSession session){

        ModelAndView modelAndView = new ModelAndView();
        session.setAttribute("visitId", visitId);
        modelAndView.setViewName("doctorpanel/prescribe");
        return modelAndView;

    }

    @PostMapping(value = "/getDrugData")
    @ResponseBody
    public List<Lek> getDrugData(@RequestBody String data){

        return doctorPanelService.getDrugData(data);

    }

    @PostMapping(value = "/submitPrescription")
    @ResponseBody
    public boolean submitPrescribtion(@RequestBody List<Integer> drugIds, HttpSession session){
        int visitId = (int)session.getAttribute("visitId");
        doctorPanelService.submitPrescription(drugIds, visitId);
        return true;
    }

    @GetMapping(value = "/getPrevious")
    public ModelAndView getPrevoiusVisits(@RequestParam("visitId")int visitId){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("doctorpanel/recentVisits");
        modelAndView.addObject("visits", doctorPanelService.getVistsByPatient(visitId));
        return modelAndView;

    }

}
