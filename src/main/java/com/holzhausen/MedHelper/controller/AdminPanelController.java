package com.holzhausen.MedHelper.controller;

import com.holzhausen.MedHelper.model.services.AdminPanelService;
import com.holzhausen.MedHelper.model.services.DataResolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping(value = "/adminPanel")
public class AdminPanelController {

    private DataResolverService dataResolverService;

    @Autowired
    public AdminPanelController(DataResolverService dataResolverService) {
        this.dataResolverService = dataResolverService;
    }

    @GetMapping(value = "")
    public ModelAndView showAdministratorPanel(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminpanel/adminPanel");
        return modelAndView;
    }

    @GetMapping(value = "/addData")
    public ModelAndView addData(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tables", AdminPanelService.INPUT_TABLES);
        modelAndView.setViewName("adminpanel/addData");
        return modelAndView;
    }

    @PostMapping(value = "/addData")
    public ModelAndView uploadData(@RequestParam("file") MultipartFile file, @RequestParam("tables") String tableName){
        ModelAndView modelAndView = new ModelAndView();


        dataResolverService.resolveCSVData(file, tableName);
        modelAndView.setViewName("adminpanel/adminPanel");
        return modelAndView;
    }

    @GetMapping(value = "/findDoctor")
    public ModelAndView findDoctor(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminpanel/addVisits");
        modelAndView.addObject("warn", false);
        return modelAndView;

    }

}
