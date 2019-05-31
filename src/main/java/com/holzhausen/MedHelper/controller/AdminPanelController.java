package com.holzhausen.MedHelper.controller;

import com.holzhausen.MedHelper.model.entities.GabinetLekarski;
import com.holzhausen.MedHelper.model.entities.Lekarz;
import com.holzhausen.MedHelper.model.entities.Specjalnosc;
import com.holzhausen.MedHelper.model.formclasses.ReserveSearch;
import com.holzhausen.MedHelper.model.formclasses.VisitSearchDetail;
import com.holzhausen.MedHelper.model.projections.*;
import com.holzhausen.MedHelper.model.services.AdminPanelService;
import com.holzhausen.MedHelper.model.services.DataResolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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

    @PostMapping(value = "/getVisitsInfo")
    @ResponseBody
    public List<OccupiedVisitsProjectionImpl> getOccupiedVisits(@RequestBody VisitSearchDetail visitSearchDetail){
        return adminPanelService.getOccupiedVisits(visitSearchDetail);
    }

    @PostMapping(value = "/sendVisitData")
    @ResponseBody
    public boolean reserveVisits(@RequestBody VisitSearchDetail visitSearchDetail){
        adminPanelService.saveNewVisits(visitSearchDetail);
        return true;
    }

    @Cacheable("myCache")
    @GetMapping(value = "/lastWeekVisitStats")
    @ResponseBody
    public List<VisitQuantityProjectionImpl> getLastWeekVisitStats(){
        try {
            Thread.sleep(5000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return adminPanelService.getWeekVisitStats();
    }

    @Cacheable("mySecondCache")
    @GetMapping(value = "/doctorStats")
    @ResponseBody
    public List<LekarzProjectionImpl> getDoctorStats(){
        try {
            Thread.sleep(5000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return adminPanelService.getDoctorReserveStats();
    }

    @Cacheable("myThirdCache")
    @GetMapping(value = "/drugStats")
    @ResponseBody
    public List<DrugProjectionImpl> getDrugStats(){
        try {
            Thread.sleep(5000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return adminPanelService.getDrugStats();
    }

    @Cacheable("myFourthCache")
    @GetMapping(value = "/specialtyStats")
    @ResponseBody
    public List<SpecialtyProjectionImpl> getSpecialtyStats(){
        try {
            Thread.sleep(5000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return adminPanelService.getSpecialtyStats();
    }

    @GetMapping(value = "/updateCache")
    public RedirectView updateCache(){
        adminPanelService.getWeekVisitStats();
        adminPanelService.getDoctorReserveStats();
        adminPanelService.getDrugStats();
        adminPanelService.getSpecialtyStats();
        return new RedirectView("/adminPanel");
    }

    @GetMapping(value = "/findVisits")
    public ModelAndView reserveVisitPanel(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminpanel/findVisits");
        List<Specjalnosc> specialties = adminPanelService.getSpecialties();
        modelAndView.addObject("specialties", specialties);
        return modelAndView;
    }

    @PostMapping(value = "/getVisitItems")
    @ResponseBody
    public List<ReserveVisitItemProjectionImpl> getVisitItems(@RequestBody ReserveSearch search){
        return adminPanelService.getDoctorsWithVisists(search.getDate(),search.getPlaceName(),search.getSpecialty());
    }

    @PostMapping(value = "/getVisitTimes")
    @ResponseBody
    public List<TimeVisitsProjectionImpl> getVisitTimes(@RequestBody ReserveSearch search){
        return adminPanelService.getDoctorVisits(search.getDate(),search.getPlaceId(),search.getDoctorId());
    }

    @GetMapping(value = "/confirm")
    public ModelAndView changeVisitInfoPanel(@RequestParam("visitId") int visitId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminpanel/changeVisit");
        modelAndView.addObject("visitId", visitId);
        modelAndView.addObject("doctors", adminPanelService.getDoctors());
        modelAndView.addObject("rooms", adminPanelService.getRoomsFromVisit(visitId));
        return modelAndView;
    }

    @PostMapping(value = "/confirm")
    public RedirectView confirmVisitChanges(@RequestParam("visitId") int visitId, @RequestParam("roomId") int roomId,
                                            @RequestParam("doctorId") int doctorId){
        adminPanelService.updateVisit(visitId, doctorId, roomId);
        return new RedirectView("/adminPanel/findVisits");
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
