package com.holzhausen.MedHelper.model.repositories;

import com.holzhausen.MedHelper.model.entities.Pacjent;
import com.holzhausen.MedHelper.model.entities.Wizyta;
import com.holzhausen.MedHelper.model.projections.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Interfejs repozytorium powinien być zaadnotowany @Repository i rozszarzać interfejs JpaRepository.
 * Dla każdej encji powinno istnieć tylko jedno repozytorium.
 */
@Repository
public interface WizytaRepository extends JpaRepository<Wizyta, Integer> {

    /**
     * Zapisywanie encji do bazy musi być o takiej strukturze jak tu widać
     * @param wizyta
     * @return
     */
    Wizyta save(Wizyta wizyta);

    /**
     * Usuwanie z bazy na podstawie id
     * @param id
     */
    void deleteById(int id);

    /**
     * Szukanie wizyt na podstawie id.
     * @param id
     * @return
     */
    Wizyta findById(int id);

    /**
     * W przypadku chęci użycia zapytania SQL, należy dodać widoczną poniżej adnotację. Jeżeli metoda ma zwracać
     * obiekt, który obsługuje to repozytorium (w tym przypadku obiekt Wizyta), to w zapytaniu należy dać SELECT *,
     * natomiast, jeżeli nie chce się pobierać wszystkich danych albo w zapytaniu znajduje się JOIN, najlepszym
     * rozwiązaniem jest stworzenie interfejsu, który będzie działał na zasadzie projekcji. W tym przypadku pobieram
     * dane za pomocą interfejsu OccupiedVisitsProjection, który zawiera deklaracje metod getTime() i getDurationTime().
     * Oznacza, to że należy w zapytaniu zrobić projekcję na dwie kolumny, a następnie nadać tej kolumnie alias, jak widać poniżej
     * czas_trwania AS durationTime. Alias musi odpowiadać metodzie w interfejsie, w taki sposób, że jeżeli jest np.
     * w interfejsie getLollipop, to w aliasie odejmujemy get i pierwszą literę zamieniamy na małą, otrzymując lollipop.
     * Jeżeli w zapytaniu chcemy użyć jakichś zmiennych, które zostały wczytane na wejściu musimy oznaczyć je jako @Param
     * tak jak widać poniżej.
     * @param doctorId
     * @param date
     * @param gabinetId
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT time AS time, czas_trwania AS durationTime " +
                                        "FROM wizyta " +
                                        "WHERE data=:date AND (lekarz_id=:doctorId OR gabinet_lekarski_id=:gabinetId) " +
                                        "ORDER BY time")
    List<OccupiedVisitsProjection> getOccupiedVisits(@Param("doctorId") int doctorId, @Param("date")Date date,
                                                     @Param("gabinetId") int gabinetId);

    @Query(nativeQuery = true, value = "SELECT DISTINCT S.name AS specialty, L.user_id AS doctorId, CONCAT(L.imie,' ',L.nazwisko) " +
                                        "AS fullName, P.id AS placeId, P.miasto AS city, P.adres AS address " +
                                        "FROM wizyta W JOIN user L " +
                                        "ON W.lekarz_id = L.user_id JOIN specjalnosc_lekarz SL " +
                                        "ON L.user_id = SL.lekarz_id JOIN specjalnosc S " +
                                        "ON SL.specjalnosc_id = S.id JOIN gabinet_lekarski GL " +
                                        "ON W.gabinet_lekarski_id = GL.id JOIN placowka P " +
                                        "ON GL.placowka_id = P.id " +
                                        "WHERE (MATCH(S.name) AGAINST (:specialty IN BOOLEAN MODE)) AND " +
                                        "W.data=:date AND W.pacjent_id IS NULL AND " +
                                        "(MATCH(P.miasto, P.adres) AGAINST (:placeName IN BOOLEAN MODE))")
    List<ReserveVisitItemProjection> getDoctorsWithVisitsGivenSpecialty(@Param("date")Date date, @Param("placeName") String placeName,
                                                                        @Param("specialty") String specialty);

    @Query(nativeQuery = true, value = "SELECT DISTINCT S.name AS specialty, L.user_id AS doctorId, CONCAT(L.imie,' ',L.nazwisko) " +
            "AS fullName, P.id AS placeId, P.miasto AS city, P.adres AS address " +
            "FROM wizyta W JOIN user L " +
            "ON W.lekarz_id = L.user_id JOIN specjalnosc_lekarz SL " +
            "ON L.user_id = SL.lekarz_id JOIN specjalnosc S " +
            "ON SL.specjalnosc_id = S.id JOIN gabinet_lekarski GL " +
            "ON W.gabinet_lekarski_id = GL.id JOIN placowka P " +
            "ON GL.placowka_id = P.id " +
            "WHERE W.data=:date AND W.pacjent_id IS NULL AND " +
            "(MATCH(P.miasto, P.adres) AGAINST (:placeName IN BOOLEAN MODE))")
    List<ReserveVisitItemProjection> getDoctorsWithVisits(@Param("date")Date date, @Param("placeName") String placeName);

    @Query(nativeQuery = true, value = "SELECT DISTINCT S.name AS specialty, L.user_id AS doctorId, CONCAT(L.imie,' ',L.nazwisko) " +
            "AS fullName, P.id AS placeId, P.miasto AS city, P.adres AS address " +
            "FROM wizyta W JOIN user L " +
            "ON W.lekarz_id = L.user_id JOIN specjalnosc_lekarz SL " +
            "ON L.user_id = SL.lekarz_id JOIN specjalnosc S " +
            "ON SL.specjalnosc_id = S.id JOIN gabinet_lekarski GL " +
            "ON W.gabinet_lekarski_id = GL.id JOIN placowka P " +
            "ON GL.placowka_id = P.id " +
            "WHERE (MATCH(S.name) AGAINST (:specialty IN BOOLEAN MODE)) AND " +
            "W.data=:date AND " +
            "(MATCH(P.miasto, P.adres) AGAINST (:placeName IN BOOLEAN MODE))")
    List<ReserveVisitItemProjection> getDoctorsWithVisitsGivenSpecialtyToChange(@Param("date")Date date, @Param("placeName") String placeName,
                                                                        @Param("specialty") String specialty);

    @Query(nativeQuery = true, value = "SELECT DISTINCT S.name AS specialty, L.user_id AS doctorId, CONCAT(L.imie,' ',L.nazwisko) " +
            "AS fullName, P.id AS placeId, P.miasto AS city, P.adres AS address " +
            "FROM wizyta W JOIN user L " +
            "ON W.lekarz_id = L.user_id JOIN specjalnosc_lekarz SL " +
            "ON L.user_id = SL.lekarz_id JOIN specjalnosc S " +
            "ON SL.specjalnosc_id = S.id JOIN gabinet_lekarski GL " +
            "ON W.gabinet_lekarski_id = GL.id JOIN placowka P " +
            "ON GL.placowka_id = P.id " +
            "WHERE W.data=:date AND " +
            "(MATCH(P.miasto, P.adres) AGAINST (:placeName IN BOOLEAN MODE))")
    List<ReserveVisitItemProjection> getDoctorsWithVisitsToChange(@Param("date")Date date, @Param("placeName") String placeName);

    @Query(nativeQuery = true, value = "SELECT W.id AS id, W.time AS time " +
                                        "FROM wizyta W JOIN gabinet_lekarski GL " +
                                        "ON W.gabinet_lekarski_id = GL.id " +
                                        "WHERE W.data=:date AND W.lekarz_id=:doctorId AND GL.placowka_id = :placeId " +
                                        "AND W.pacjent_id IS NULL")
    List<TimeVisistsProjection> getDoctorsVisits(@Param("date")Date date, @Param("placeId") int placeId,
                                                 @Param("doctorId") int doctorId);

    @Query(nativeQuery = true, value = "SELECT W.id AS id, W.time AS time " +
            "FROM wizyta W JOIN gabinet_lekarski GL " +
            "ON W.gabinet_lekarski_id = GL.id " +
            "WHERE W.data=:date AND W.lekarz_id=:doctorId AND GL.placowka_id = :placeId")
    List<TimeVisistsProjection> getDoctorsVisitsToChange(@Param("date")Date date, @Param("placeId") int placeId,
                                                 @Param("doctorId") int doctorId);


    @Query(nativeQuery = true, value = "SELECT S.name AS hello, L.imie AS imie, L.nazwisko AS nazwisko, GL.nr_sali AS nrSali, " +
                                        "P.miasto AS miasto, P.adres AS adres, W.data AS data, W.time AS time " +
                                        "FROM wizyta W JOIN gabinet_lekarski GL " +
                                        "ON W.gabinet_lekarski_id = GL.id JOIN placowka P " +
                                        "ON GL.placowka_id = P.id JOIN user L " +
                                        "ON W.lekarz_id = L.user_id JOIN specjalnosc_lekarz SL " +
                                        "ON L.user_id = SL.lekarz_id JOIN specjalnosc S " +
                                        "ON SL.specjalnosc_id = S.id " +
                                        "WHERE W.id = :visitId")
    WizytaProjection getVisitGivenId(@Param("visitId") int visitId);



    @Query(nativeQuery = true, value = "SELECT data AS visitDate, COUNT(*) AS quantity " +
                                        "FROM wizyta " +
                                        "WHERE DATEDIFF(NOW(), data)>0 AND DATEDIFF(NOW(), data)<=7 " +
                                        "GROUP BY data " +
                                        "ORDER BY data")
    List<VisitQuantityProjection> getWeekVisitStats();

    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM wizyta " +
            "WHERE czy_sie_odbyla=true AND pacjent_id=:patientId")
    List<Wizyta> getPreviousVisit(@Param("patientId") int patientId);

}
