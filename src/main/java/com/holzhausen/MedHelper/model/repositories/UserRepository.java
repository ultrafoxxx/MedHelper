package com.holzhausen.MedHelper.model.repositories;

import com.holzhausen.MedHelper.model.entities.Lekarz;
import com.holzhausen.MedHelper.model.entities.User;
import com.holzhausen.MedHelper.model.projections.LekarzProjection;
import com.holzhausen.MedHelper.model.projections.PatientProjection;
import com.holzhausen.MedHelper.model.projections.WizytaProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User save(User user);

    User findByUserId(int id);

    @Query(nativeQuery = true, value = "SELECT * " +
                                        "FROM user " +
                                        "WHERE rola='Pacjent' AND ((imie LIKE CONCAT(:firstName,'%')) AND (nazwisko LIKE CONCAT(:lastName,'%')))")
    List<User> findPatientsStartingWith(@Param(value = "firstName") String firstName,
                                        @Param(value = "lastName") String lastName);

    User findByPesel(String pesel);

    @Query(nativeQuery = true, value = "SELECT W.id AS id, U2.imie AS imie, U2.nazwisko AS nazwisko, GL.nr_sali AS nrSali," +
                                        " P.miasto AS miasto, P.adres AS adres, W.data AS data, W.time AS time " +
                                        "FROM user U1 JOIN wizyta W ON U1.user_id=W.pacjent_id " +
                                        "JOIN user U2 ON W.lekarz_id = U2.user_id " +
                                        "JOIN gabinet_lekarski GL ON W.gabinet_lekarski_id = GL.id " +
                                        "JOIN placowka P ON GL.placowka_id = P.id " +
                                        "WHERE U1.user_id=:patientId " +
                                        "ORDER BY W.data, W.time")
    List<WizytaProjection> getVisitsForPatient(@Param(value = "patientId") int patientId);

    @Query(nativeQuery = true, value = "SELECT CONCAT(U.imie, ' ', U.nazwisko) AS name, U.pesel AS pesel," +
            " S.name AS specjalnosc, U.user_id AS userId " +
            "FROM user U JOIN specjalnosc_lekarz SL " +
            "ON U.user_id = SL.lekarz_id JOIN specjalnosc S " +
            "ON SL.specjalnosc_id = S.id " +
            "ORDER BY U.nazwisko")
    List<LekarzProjection> getDoctors(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT CONCAT(U.imie, ' ', U.nazwisko) AS name, U.user_id AS userId " +
            "FROM user U JOIN specjalnosc_lekarz SL " +
            "ON U.user_id = SL.lekarz_id JOIN specjalnosc S " +
            "ON SL.specjalnosc_id = S.id " +
            "ORDER BY U.nazwisko")
    List<LekarzProjection> getAllDoctors();

    @Query(nativeQuery = true, value = "SELECT COUNT(*) " +
                                        "FROM user " +
                                        "WHERE rola='Lekarz'")
    int getNumberOfDoctors();

    @Query(nativeQuery = true, value = "SELECT CONCAT(U.imie, ' ', U.nazwisko) AS name, U.pesel AS pesel," +
            " S.name AS specjalnosc, U.user_id AS userId " +
            "FROM user U JOIN specjalnosc_lekarz SL " +
            "ON U.user_id = SL.lekarz_id JOIN specjalnosc S " +
            "ON SL.specjalnosc_id = S.id " +
            "WHERE MATCH(imie, nazwisko, pesel) AGAINST (:data IN BOOLEAN MODE)" +
            "ORDER BY U.nazwisko")
    List<LekarzProjection> queryDoctors(@Param(value = "data") String data, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) " +
                                        "FROM user " +
                                        "WHERE rola='Lekarz' AND MATCH(imie, nazwisko, pesel) AGAINST (:data IN BOOLEAN MODE)")
    int getNumberOfDoctors(@Param(value = "data") String data);

    @Query(nativeQuery = true, value = "SELECT CONCAT(imie, ' ', nazwisko) AS name, pesel AS pesel," +
            "  user_id AS id, nr_dowodu AS licenseNumber " +
            "FROM user " +
            "WHERE (MATCH(imie, nazwisko, pesel) AGAINST (:data IN BOOLEAN MODE)) AND rola='Pacjent' " +
            "ORDER BY nazwisko")
    List<PatientProjection> queryPatients(@Param(value = "data") String data, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) " +
            "FROM user " +
            "WHERE rola='Pacjent' AND MATCH(imie, nazwisko, pesel) AGAINST (:data IN BOOLEAN MODE)")
    int getNumberOfPatients(@Param(value = "data") String data);

    @Query(nativeQuery = true, value = "SELECT CONCAT(U.imie, ' ', U.nazwisko) AS name, COUNT(*) AS userId " +
                                        "FROM user U JOIN wizyta W " +
                                        "ON U.user_id = W.lekarz_id " +
                                        "WHERE W.pacjent_id IS NOT NULL " +
                                        "GROUP BY U.nazwisko, U.imie " +
                                        "HAVING COUNT(*)>0 " +
                                        "ORDER BY userId DESC " +
                                        "LIMIT 5")
    List<LekarzProjection> getDoctorReserveStats();

}
