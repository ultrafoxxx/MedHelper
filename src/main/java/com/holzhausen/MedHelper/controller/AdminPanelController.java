package com.holzhausen.MedHelper.controller;

import com.holzhausen.MedHelper.model.entities.GabinetLekarski;
import com.holzhausen.MedHelper.model.entities.Lekarz;
import com.holzhausen.MedHelper.model.projections.LekarzProjectionImpl;
import com.holzhausen.MedHelper.model.projections.OccupiedVisitsProjectionImpl;
import com.holzhausen.MedHelper.model.projections.PlaceProjectionImpl;
import com.holzhausen.MedHelper.model.services.AdminPanelService;
import com.holzhausen.MedHelper.model.services.DataResolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
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
        modelAndView.addObject("doctorId", doctorId);
        return modelAndView;

    }

    @GetMapping(value = "/getHints")
    @ResponseBody
    public List<PlaceProjectionImpl> getPlaceHints(@RequestParam(name = "data") String data){
        return adminPanelService.hintPlaces(data);
    }

    @GetMapping(value = "/getRooms")
    @ResponseBody
    public List<GabinetLekarski> getRooms(@RequestParam(name = "placeId") int placeId){
        return adminPanelService.getRooms(placeId);
    }

    @GetMapping(value = "/getVisitsInfo")
    @ResponseBody
    public List<OccupiedVisitsProjectionImpl> getOccupiedVisits(@RequestParam(name = "date") Date date,
                                                                @RequestParam(name = "gabinetId") int gabinetId,
                                                                @RequestParam(name = "doctorId") int doctorId){
        return adminPanelService.getOccupiedVisits(doctorId, date, gabinetId);
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
