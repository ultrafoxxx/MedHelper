package com.holzhausen.MedHelper.model.services;

import com.holzhausen.MedHelper.model.entities.*;
import com.holzhausen.MedHelper.model.formclasses.VisitSearchDetail;
import com.holzhausen.MedHelper.model.projections.*;
import com.holzhausen.MedHelper.model.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import javax.persistence.EntityManager;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Service
public class AdminPanelService {

    public static final String[] INPUT_TABLES = {"Lek", "User", "Placowka"};

    public static final int NUM_PAGES = 10;

    private static final int NUM_HINTS = 6;

    private UserRepository userRepository;
    private PlacowkaRepository placowkaRepository;
    private GabinetLekarskiRepository gabinetLekarskiRepository;
    private WizytaRepository wizytaRepository;
    private ReserveVisitService reserveVisitService;
    private EmailService emailService;
    private LekRepository lekRepository;
    private TemplateEngine engine;
    private SpecjalnoscRepository specjalnoscRepository;

    private EntityManager manager;

    @Autowired
    public AdminPanelService(UserRepository userRepository, PlacowkaRepository placowkaRepository,
                             GabinetLekarskiRepository gabinetLekarskiRepository, WizytaRepository wizytaRepository,
                             EntityManager manager, ReserveVisitService reserveVisitService, EmailService emailService,
                             TemplateEngine engine, LekRepository lekRepository, SpecjalnoscRepository specjalnoscRepository) {
        this.userRepository = userRepository;
        this.placowkaRepository = placowkaRepository;
        this.gabinetLekarskiRepository = gabinetLekarskiRepository;
        this.wizytaRepository = wizytaRepository;
        this.manager = manager;
        this.reserveVisitService = reserveVisitService;
        this.emailService = emailService;
        this.engine = engine;
        this.lekRepository = lekRepository;
        this.specjalnoscRepository = specjalnoscRepository;
    }

    public List<LekarzProjectionImpl> getDoctors(String data, int page){

        List<LekarzProjectionImpl> doctors = new ArrayList<>();

        List<LekarzProjection> sourceData;

        Pageable pageable = PageRequest.of(page, NUM_PAGES);

        if(data.isEmpty()){
            sourceData = userRepository.getDoctors(pageable);
        }
        else {
            data+="*";
            sourceData = userRepository.queryDoctors(data, pageable);
        }

        for(LekarzProjection sourceDoctor : sourceData){

            LekarzProjectionImpl doctor = new LekarzProjectionImpl();

            doctor.setName(sourceDoctor.getName());
            doctor.setPesel(sourceDoctor.getPesel());
            doctor.setSpecjalnosc(sourceDoctor.getSpecjalnosc());
            doctor.setUserId(sourceDoctor.getUserId());
            doctors.add(doctor);

        }

        return doctors;

    }

    public int getNumberOfDoctors(String data){

        if(data.isEmpty()){
            return userRepository.getNumberOfDoctors();
        }
        else {
            return userRepository.getNumberOfDoctors(data);
        }

    }

    public List<PlaceProjectionImpl> hintPlaces(String data){

        Pageable pageable = PageRequest.of(0, NUM_HINTS);

        data+="*";

        List<PlaceProjection> places = placowkaRepository.findAllPlacowkaGivenQuery(data, pageable);

        List<PlaceProjectionImpl> result = new ArrayList<>();

        for(PlaceProjection place : places){

            PlaceProjectionImpl projection = new PlaceProjectionImpl();
            projection.setId(place.getId());
            projection.setFullName(place.getFullName());
            result.add(projection);

        }

        return result;
    }

    public List<GabinetLekarski> getRooms(int placeId){
        return gabinetLekarskiRepository.getGabinetsByPlaceId(placeId);
    }

    public List<OccupiedVisitsProjectionImpl> getOccupiedVisits(VisitSearchDetail visitSearchDetail){

        List<OccupiedVisitsProjection> occupiedVisits = wizytaRepository
                .getOccupiedVisits(visitSearchDetail.getDoctorId(), visitSearchDetail.getDate(),
                        visitSearchDetail.getGabinetId());

        Placowka placowka = placowkaRepository.getPlacowkaWhereISGivenRoom(visitSearchDetail.getGabinetId());
        String[] openTime = placowka.getCzasOtwarcia().toString().split(":");
        String[] closeTime = placowka.getCzasZamkniecia().toString().split(":");

        String[] timeIter = openTime.clone();

        List<OccupiedVisitsProjectionImpl> availableVisits = new ArrayList<>();

        for(OccupiedVisitsProjection visits : occupiedVisits){
            String[] occupiedVisitTime = visits.getTime().toString().split(":");
            int difference = getMinutes(occupiedVisitTime) - getMinutes(timeIter);
            for(int i=visitSearchDetail.getVisitTime();i<=difference;i+=visitSearchDetail.getVisitTime()){
                OccupiedVisitsProjectionImpl newVisit = new OccupiedVisitsProjectionImpl();
                newVisit.setDurationTime(visitSearchDetail.getVisitTime());
                newVisit.setTime(getTranslatedTime(timeIter));
                timeIter = getTime(getMinutes(timeIter)+visitSearchDetail.getVisitTime());
                availableVisits.add(newVisit);
            }
            int durationTime = visits.getDurationTime();
            timeIter = getTime(getMinutes(visits.getTime().toString().split(":"))+durationTime);
        }

        int difference = getMinutes(closeTime) - getMinutes(timeIter);
        for(int i=visitSearchDetail.getVisitTime();i<=difference;i+=visitSearchDetail.getVisitTime()){
            OccupiedVisitsProjectionImpl newVisit = new OccupiedVisitsProjectionImpl();
            newVisit.setDurationTime(visitSearchDetail.getVisitTime());
            newVisit.setTime(getTranslatedTime(timeIter));
            timeIter = getTime(getMinutes(timeIter)+visitSearchDetail.getVisitTime());
            availableVisits.add(newVisit);
        }

        return availableVisits;
    }

    public void saveNewVisits(VisitSearchDetail visitSearchDetail){

        Date visitDate = visitSearchDetail.getDate();
        int durationTime = visitSearchDetail.getVisitTime();
        Lekarz doctor = manager.getReference(Lekarz.class, visitSearchDetail.getDoctorId());
        GabinetLekarski doctorsRoom = manager.getReference(GabinetLekarski.class, visitSearchDetail.getGabinetId());
        for(String visitTime : visitSearchDetail.getTime()){
            Wizyta wizyta = new Wizyta();
            wizyta.setData(visitDate);
            wizyta.setCzasTrwania(durationTime);
            wizyta.setLekarz(doctor);
            wizyta.setGabinetLekarski(doctorsRoom);
            wizyta.setTime(Time.valueOf(visitTime));
            wizytaRepository.save(wizyta);
        }
    }

    @CachePut("myCache")
    public List<VisitQuantityProjectionImpl> getWeekVisitStats(){


        List<VisitQuantityProjection> lastWeekVisits = wizytaRepository.getWeekVisitStats();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        List<VisitQuantityProjectionImpl> result = new ArrayList<>();
        for(int i=0;i<7;i++){
            VisitQuantityProjectionImpl visitQuantity = new VisitQuantityProjectionImpl();
            Date date = new Date(calendar.getTime().getTime());
            visitQuantity.setDate(date);
            if(!lastWeekVisits.isEmpty() && lastWeekVisits.get(0).getVisitDate().toString().equals(date.toString())){
                visitQuantity.setQuantity(lastWeekVisits.remove(0).getQuantity());
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            result.add(visitQuantity);
        }
        return result;
    }


    @CachePut("mySecondCache")
    public List<LekarzProjectionImpl> getDoctorReserveStats(){
        List<LekarzProjection> queryResult = userRepository.getDoctorReserveStats();
        List<LekarzProjectionImpl> result = new ArrayList<>();
        for(LekarzProjection doctor : queryResult){
            LekarzProjectionImpl newDoctor = new LekarzProjectionImpl();
            newDoctor.setName(doctor.getName());
            newDoctor.setUserId(doctor.getUserId());
            result.add(newDoctor);
        }
        return result;
    }

    @CachePut("myThirdCache")
    public List<DrugProjectionImpl> getDrugStats(){
        List<DrugProjection> queryResult = lekRepository.getDrugStatistics();
        List<DrugProjectionImpl> result = new ArrayList<>();
        for(DrugProjection drug : queryResult){
            DrugProjectionImpl newDrug = new DrugProjectionImpl();
            newDrug.setDrugCount(drug.getDrugCount());
            newDrug.setDrugName(drug.getDrugName());
            result.add(newDrug);
        }
        return result;
    }

    @CachePut("myFourthCache")
    public List<SpecialtyProjectionImpl> getSpecialtyStats(){
        List<SpecialtyProjection> queryResult = specjalnoscRepository.getSpecialtyStatistics();
        List<SpecialtyProjectionImpl> result = new ArrayList<>();
        for(SpecialtyProjection specialty : queryResult){
            SpecialtyProjectionImpl newSpecialty = new SpecialtyProjectionImpl();
            newSpecialty.setSpecialtyCount(specialty.getSpecialtyCount());
            newSpecialty.setSpecialtyName(specialty.getSpecialtyName());
            result.add(newSpecialty);
        }
        return result;
    }

    public List<Specjalnosc> getSpecialties(){
        return reserveVisitService.getSpecialties();
    }

    public List<ReserveVisitItemProjectionImpl> getDoctorsWithVisists(Date date, String placeName,String specialty){

        List<ReserveVisitItemProjection> queryResult;
        if(specialty.equals("default")){
            queryResult = wizytaRepository.getDoctorsWithVisitsToChange(date, placeName+"*");
        }
        else {
            queryResult = wizytaRepository.getDoctorsWithVisitsGivenSpecialtyToChange(date, placeName+"*",
                    specialty);
        }
        List<ReserveVisitItemProjectionImpl> result = new ArrayList<>();
        for(ReserveVisitItemProjection myResult : queryResult){

            ReserveVisitItemProjectionImpl newItem = new ReserveVisitItemProjectionImpl();
            newItem.setAddress(myResult.getAddress());
            newItem.setCity(myResult.getCity());
            newItem.setDoctorId(myResult.getDoctorId());
            newItem.setFullName(myResult.getFullName());
            newItem.setPlaceId(myResult.getPlaceId());
            newItem.setSpecialty(myResult.getSpecialty());
            result.add(newItem);
        }
        return result;
    }

    public List<TimeVisitsProjectionImpl> getDoctorVisits(Date date, int placeId, int doctorId){

        List<TimeVisistsProjection> queryResult = wizytaRepository.getDoctorsVisitsToChange(date, placeId, doctorId);
        List<TimeVisitsProjectionImpl> result = new ArrayList<>();
        for(TimeVisistsProjection myResult : queryResult){
            TimeVisitsProjectionImpl newItem = new TimeVisitsProjectionImpl();
            newItem.setId(myResult.getId());
            newItem.setTime(myResult.getTime());
            result.add(newItem);
        }
        return result;
    }

    public WizytaProjectionImpl getVisitGivenId(int visitId){
        return reserveVisitService.getVisitGivenId(visitId);
    }

    public List<RoomProjectionImpl> getRoomsFromVisit(int visitId){
        List<RoomProjection> queryResult = gabinetLekarskiRepository.queryRoomsByVisit(visitId);
        List<RoomProjectionImpl> myResult = new ArrayList<>();
        for(RoomProjection result : queryResult){
            RoomProjectionImpl room = new RoomProjectionImpl();
            room.setNrSali(result.getNrSali());
            room.setId(result.getId());
            myResult.add(room);
        }
        System.out.println(queryResult.size());
        return myResult;
    }

    public List<LekarzProjectionImpl> getDoctors(){
        List<LekarzProjection> queryResult = userRepository.getAllDoctors();
        List<LekarzProjectionImpl> myResult = new ArrayList<>();
        for(LekarzProjection doctor : queryResult){
            LekarzProjectionImpl myDoctor = new LekarzProjectionImpl();
            myDoctor.setUserId(doctor.getUserId());
            myDoctor.setName(doctor.getName());
            myResult.add(myDoctor);
        }
        return myResult;
    }

    public void updateVisit(int visitId, int doctorId, int roomId){
        Wizyta visit = wizytaRepository.findById(visitId);
        visit.setLekarz((Lekarz)manager.getReference(User.class, doctorId));
        visit.setGabinetLekarski(manager.getReference(GabinetLekarski.class, roomId));
        visit = wizytaRepository.save(visit);
        if(visit.getPacjent()!=null){
            Context context = new Context();
            context.setVariable("doctor", visit.getLekarz().getImie()+" "+visit.getLekarz().getNazwisko());
            context.setVariable("room", visit.getGabinetLekarski().getNrSali());
            context.setVariable("place", visit.getGabinetLekarski().getPlacowka().getMiasto()+", "+
                    visit.getGabinetLekarski().getPlacowka().getAdres());
            context.setVariable("date", visit.getData());
            context.setVariable("time", visit.getTime());
            String message = engine.process("visitChangeTemplate", context);
            emailService.sendEmail(visit.getPacjent().getEmail(), "Zmiana danych twojej wizyty", message);
        }
    }

    private Time getTranslatedTime(String[] time){

        StringBuilder builder = new StringBuilder();

        for(int i=0;i<time.length;i++){
            builder.append(time[i]);
            builder.append(":");
        }
        builder.deleteCharAt(builder.length()-1);
        return Time.valueOf(builder.toString());

    }

    private int getMinutes(String[] time){
        return Integer.parseInt(time[0])*60 + Integer.parseInt(time[1]);
    }

    private String[] getTime(int minutes){
        String[] time = new String[3];
        int hours = (int)Math.floor(minutes / 60);
        time[0] = String.valueOf(hours);
        if(hours<10){
            time[0] = "0" + time[0];
        }
        int mins = minutes % 60;
        time[1] = String.valueOf(mins);
        if(mins < 10){
            time[1] = "0" + time[1];
        }
        time[2] = "00";
        return time;
    }


}
