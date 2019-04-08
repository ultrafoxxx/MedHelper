package com.holzhausen.MedHelper.model.repositories;

import com.holzhausen.MedHelper.model.entities.User;
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


}
