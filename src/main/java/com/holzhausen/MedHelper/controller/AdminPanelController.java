package com.holzhausen.MedHelper.controller;

import com.holzhausen.MedHelper.model.entities.Lekarz;
import com.holzhausen.MedHelper.model.projections.LekarzProjectionImpl;
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
import java.util.List;

@Controller
@RequestMapping(value = "/adminPanel")
public class AdminPanelController {

    private DataResolverService dataResolverService;

    private AdminPanelService adminPanelService;

    @Autowired
    public AdminPanelController(DataResolverService dataResolverService, AdminPanelService adminPanelService) {
        this.dataResolverService = dataResolverService;
        this.adminPanelService = adminPanelService;
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
        List<LekarzProjectionImpl> doctors = adminPanelService.getDoctors("", 0);

        return prepareForDoctor(modelAndView, doctors, 1, "");

    }

    @GetMapping(value = "/findDoctorPage")
    public ModelAndView findDoctorPage(@RequestParam(name = "page") int page){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminpanel/addVisits");
        List<LekarzProjectionImpl> doctors = adminPanelService.getDoctors("", page-1);

        return prepareForDoctor(modelAndView, doctors, page, "");
    }

    @PostMapping(value = "/findDoctor")
    public ModelAndView getDoctor(@RequestParam(name = "data") String data){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminpanel/addVisits");
        List<LekarzProjectionImpl> doctors = adminPanelService.getDoctors(data, 0);

        return prepareForDoctor(modelAndView, doctors, 1, data);

    }

    @GetMapping(value = "/reserveVisits")
    public ModelAndView reserveVisit(@RequestParam(name = "doctorId") int doctorId){

        ModelAndView  modelAndView = new ModelAndView();
        modelAndView.setViewName("/adminpanel/visitReserve");
        return modelAndView;

    }


    private ModelAndView prepareForDoctor(ModelAndView modelAndView, List<LekarzProjectionImpl> doctors, int page,
                                          String data){
        modelAndView.addObject("doctors", doctors);
        modelAndView.addObject("warn", false);
        modelAndView.addObject("page", page);
        int numOfDoctors;
        if(data.isEmpty()){
            numOfDoctors = adminPanelService.getNumberOfDoctors("");
        }
        else {
            numOfDoctors = adminPanelService.getNumberOfDoctors(data);
        }
        int maxPage = Math.floorDiv(numOfDoctors, AdminPanelService.NUM_PAGES);
        if(numOfDoctors % AdminPanelService.NUM_PAGES != 0) {
            maxPage++;
        }
        modelAndView.addObject("maxPage", maxPage);
        return modelAndView;
    }

}
