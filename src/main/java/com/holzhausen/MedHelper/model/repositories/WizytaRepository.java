package com.holzhausen.MedHelper.model.repositories;

import com.holzhausen.MedHelper.model.entities.Wizyta;
import com.holzhausen.MedHelper.model.projections.OccupiedVisitsProjection;
import com.holzhausen.MedHelper.model.projections.ReserveVisitItemProjection;
import com.holzhausen.MedHelper.model.projections.TimeVisistsProjection;
import com.holzhausen.MedHelper.model.projections.WizytaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface WizytaRepository extends JpaRepository<Wizyta, Integer> {

    Wizyta save(Wizyta wizyta);

    void deleteById(int id);

    Wizyta findById(int id);

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

    @Query(nativeQuery = true, value = "SELECT W.id AS id, W.time AS time " +
                                        "FROM wizyta W JOIN gabinet_lekarski GL " +
                                        "ON W.gabinet_lekarski_id = GL.id " +
                                        "WHERE W.data=:date AND W.lekarz_id=:doctorId AND GL.placowka_id = :placeId " +
                                        "AND W.pacjent_id IS NULL")
    List<TimeVisistsProjection> getDoctorsVisits(@Param("date")Date date, @Param("placeId") int placeId,
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
}
