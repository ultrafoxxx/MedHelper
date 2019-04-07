package com.holzhausen.MedHelper.controller;

import com.holzhausen.MedHelper.model.entities.Pacjent;
import com.holzhausen.MedHelper.model.entities.User;
import com.holzhausen.MedHelper.model.formclasses.Credential;
import com.holzhausen.MedHelper.model.services.ReceptionistService;
import com.holzhausen.MedHelper.validators.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/receptionistPanel")
public class ReceptionistPanelController {

    private ReceptionistService service;

    private BCryptPasswordEncoder encoder;

    @Autowired
    public ReceptionistPanelController(ReceptionistService service, BCryptPasswordEncoder encoder) {
        this.service = service;
        this.encoder = encoder;
    }

    @GetMapping(value = "/findPatient")
    public ModelAndView findPatient(){
        ModelAndView view = new ModelAndView();
        view.setViewName("patientpanel/findPatient");
        return view;
    }

    @GetMapping(value = "/getPatient")
    @ResponseBody
    public List<Credential> getCredentials(@RequestParam("name") String fullName){
        return service.findUserByName(fullName);
    }

    @PostMapping(value = "/getPatient")
    public ModelAndView getControlPanel(@RequestParam("patientInfo") String patientInfo, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("patientpanel/patientPanel");
        User patient = service.getUserBySearchInfo(patientInfo);
        session.setAttribute("userId", patient.getUserId());
        modelAndView.addObject("patient", patient);
        return modelAndView;
    }

    @PostMapping(value = "/submitChanges")
    public ModelAndView changeInformation(@Valid Pacjent pacjent, BindingResult result, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        PasswordValidator validator = new PasswordValidator();
        User previousData = service.findUserById((int)session.getAttribute("userId"));
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
            modelAndView.setViewName("patientpanel/patientPanel");
        }
        else{
            if(pacjent.getPassword().length() == 0){
                pacjent.setPassword(previousData.getPassword());
            }
            else {
                pacjent.setPassword(encoder.encode(pacjent.getPassword()));
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
        return view;
    }

}
