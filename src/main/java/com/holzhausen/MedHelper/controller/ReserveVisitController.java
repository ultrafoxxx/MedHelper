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

/**
 * Każda klasa kontrolera musi pusiadać adnotację @Controller, a także zawierać adnotację @RequestMapping, który wskazuje
 * na ten konkretny przypadek użycia.
 */
@Controller
@RequestMapping("/reserveVisit")
public class ReserveVisitController {

    /**
     * Kontroler posiada referencję do serwisu. Poza niektórymi przypadkami, powinno się zachować relację
     * 1 kontroler - 1 serwis
     */
    private ReserveVisitService service;

    /**
     * Klasa kontrolera musi zawierać konstruktor, który będzie przyjmował na wejściu obiekt serwisu.
     * Tutaj zachodzi zjawisko wstrzykiwania zależności, dlatego należe zaadnotować konstruktor @Autowired
     * @param service
     */
    @Autowired
    public ReserveVisitController(ReserveVisitService service) {
        this.service = service;
    }

    /**
     * Adnotację @GetMapping używa się zazwyczaj, kiedy chcemy uzyskać jakieś dane.
     * W kontrolerze każda metoda musi być zaadnotowana @GetMapping albo @PostMapping lub też innymi @(...)Mapping,
     * których teraz nie wymienię.
     * metoda setViewName ustawia nazwę widoku. Jeżeli widok, który znajduje się w folderze templates mieści się
     * jescze w innym folderze trzeba podać ścieżkę względną od folderu templates
     * metoda addObject przesyła do widoku odpowiedni obiekt, który został podany jako drugi argument i nazwany tak
     * jak widać w pierwszym argumencie
     * @return
     */
    @GetMapping(value = "")
    public ModelAndView reserveVisitPanel(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("visitreservation/reservationPatient");
        List<Specjalnosc> specialties = service.getSpecialties();
        modelAndView.addObject("specialties", specialties);
        return modelAndView;
    }

    /**
     * Adnotację @PostMapping używa się zazwyczaj, kiedy chcemy coś zapisać lub odnaleźć informacje na podstawie
     * wrażliwych danych. Adnotacja @ResponseBody oznacza, że kontroler nie prześle do klienta widoku, tylko obiekt
     * JSON. W przypadku tego projektu metody zaadnotowane @ResponseBody są używane to wywołań AJAX.
     * Obiekt zaadnotowany @RequestBody, to obiekt, którzy został przetłumaczony z JSON-a na obiekt klasy widocznej,
     * przy tejże adnotacji. @RequestBody jest używane przy wywołaniu AJAX.
     * @param search
     * @return
     */
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

    /**
     * obiekt authentication zwraca aktualnie zalogowanego użytkownika
     * obiekt session zwraca aktualną sesję. Można w tym obiekcie zapisywać i odzyskiwać zmienne / obiekty.
     * obiekt zaadnotowany @ModelAttribute jest dostępny w tym przypadku tylko wtedy, gdy wystąpi przekierowanie na tą
     * stronę ze strony, której odpowiada metoda findPatients.
     * Metoda getPrincipal zwróci aktualnie zalogowanego użytkownika.
     * @param visitId
     * @param authentication
     * @param session
     * @param data
     * @return
     */
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

    /**
     * Adnotacja @RequestParam może oznaczać dwie rzeczy. Po pierwsze jeżeli byłoby to rządanie typu GET, oznaczaloby to,
     * że visitId jest zmienną znajdującą się w linku np. localhost:8080/reserveVisit/confirm?visitId=6, w tym przypadku
     * zmienna visitId przybrałaby wartość 6. Natomiast jako, że to jest rządanie POST, oznacza to, że w kliencie
     * najprawdopodobniej znajduje się input, który posiada atrybut name, którego wartość to 'visitId'. W tym przypadku
     * visitId przybierze wartość, wpisaną w tym inpucie.
     * @param visitId
     * @return
     */
    @PostMapping(value = "/confirm")
    public ModelAndView reserveVisit(@RequestParam("visitId") int visitId){

        service.reserveVisit(visitId, -1);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("visitreservation/reservationConfirmation");
        return modelAndView;
    }

    /**
     * Tutaj używam schematu post-redirect-get, który oznacza, że np. po wysłaniu formularza strona jest przekierowywana,
     * w ten sposób, aby uniknąć tego, że przy odświerzaniu strony będzie można przesłać dany formularz jeszcze raz.
     * Przez obiekt RedirectView można przekazać atrybuty do strony, na którą przekierowujemy, na dwa sposoby.
     * Pierwszy to addAttribute, który umieści atrybut w linku, podobnie jak zostało omówione przy poprzedniej metodzie.
     * Natomiast addFlashAttribute nie powoduje umieszczenia danego atrybutu w linku. Można, go potem uzyskać, za pomocą
     * adnotacji obiektu na wejśćiu jako @ModelAttribute (patrz metoda reservationDetails).
     * @param data
     * @param attributes
     * @param session
     * @return
     */
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
        modelAndView.setViewName("visitreservation/reservationConfirmation");
        return modelAndView;
    }
}
