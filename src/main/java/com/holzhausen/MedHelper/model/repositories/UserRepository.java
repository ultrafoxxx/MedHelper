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

}
