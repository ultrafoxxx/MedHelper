package com.holzhausen.MedHelper.controller;

import com.holzhausen.MedHelper.model.entities.Pacjent;
import com.holzhausen.MedHelper.model.entities.User;
import com.holzhausen.MedHelper.model.formclasses.Credential;
import com.holzhausen.MedHelper.model.services.PatientPanelService;
import com.holzhausen.MedHelper.model.services.ReceptionistService;
import com.holzhausen.MedHelper.validators.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/patientPanel")
public class PatientPanelController {

    private PatientPanelService service;

    private BCryptPasswordEncoder encoder;

    @Autowired
    public PatientPanelController(PatientPanelService service, BCryptPasswordEncoder encoder) {
        this.service = service;
        this.encoder = encoder;
    }

    @GetMapping(value = "")
    public ModelAndView registerSite( HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        User patient = service.getPatient();
        session.setAttribute("userID", patient.getUserId());
        modelAndView.addObject("patient", patient);
        modelAndView.setViewName("patientpanel/panelPatientPanel");
        return modelAndView;
    }

    @PostMapping(value = "/submitPatientChanges")
    public ModelAndView changeInformation(@Valid Pacjent pacjent, BindingResult result, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        PasswordValidator validator = new PasswordValidator();
        User previousData = service.findUserById((int)session.getAttribute("userID"));
        if(pacjent.getPassword() == null){
            pacjent.setPassword("");
        }
        Pattern pattern = Pattern.compile(
                "(?=.*[A-Z])" +
                        "(?=.*[a-z])" +
                        "(?=.*\\d)" +
                        "^[^\\d]" +
                        ".*" +
                        "[a-zA-Z\\d]$");
        Matcher matcher = pattern.matcher(pacjent.getPassword());
        if((pacjent.getPassword().length()<10 || !matcher.matches()) && pacjent.getPassword().length()>0){
            result.rejectValue("password", "error.password", "Hasło nie spełnia wymogów");

        }
        if(result.hasErrors()){
            modelAndView.addObject("patient", previousData);
            modelAndView.setViewName("patientpanel/panelPatientPanel");
        }
        else{
            if(pacjent.getPassword().length() == 0){
                pacjent.setPassword(previousData.getPassword());
            }
            else {
                pacjent.setPassword(encoder.encode(pacjent.getPassword()));
//                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//                ((User)auth.getPrincipal()).setPassword(encoder.encode(pacjent.getPassword()));
            }
            pacjent.setUserId(previousData.getUserId());
            pacjent.setCzyKontoPotwierdzone(true);
            service.updateUserInfo(pacjent);
            session.removeAttribute("userId");
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }



    @GetMapping(value = "/getVisits")
    public ModelAndView getVisits(HttpSession session){
        ModelAndView view = new ModelAndView();
        view.setViewName("patientpanel/visits");
        view.addObject("visits", service.getVisits());
        return view;
    }

    @PostMapping(value = "/visitResign")
    public RedirectView submitDeletedVisit(@RequestParam(name = "visitId") int visitId){
        service.resignVisit(visitId);
        return new RedirectView("/patientPanel/getVisits");
    }

}
