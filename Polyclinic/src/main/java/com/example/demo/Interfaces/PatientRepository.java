package com.example.demo.Interfaces;

import com.example.demo.Entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByDepartmentId(Long departmentId);
    List<Patient> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);

    @Query("SELECT COUNT(p) > 0 FROM Patient p WHERE " +
            "LOWER(p.firstName) = LOWER(:firstName) AND " +
            "LOWER(p.lastName) = LOWER(:lastName) AND " +
            "p.birthDate = :birthDate AND " +
            "p.phone = :phone AND " +
            "(p.email IS NULL AND :email IS NULL OR LOWER(p.email) = LOWER(:email))")
    boolean existsByUniqueFields(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("birthDate") LocalDate birthDate,
            @Param("phone") String phone,
            @Param("email") String email
    );

    @Query("SELECT COUNT(p) > 0 FROM Patient p WHERE " +
            "p.id != :id AND " +
            "LOWER(p.firstName) = LOWER(:firstName) AND " +
            "LOWER(p.lastName) = LOWER(:lastName) AND " +
            "p.birthDate = :birthDate AND " +
            "p.phone = :phone AND " +
            "(p.email IS NULL AND :email IS NULL OR LOWER(p.email) = LOWER(:email))")
    boolean existsByUniqueFieldsExcludingId(
            @Param("id") Long id,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("birthDate") LocalDate birthDate,
            @Param("phone") String phone,
            @Param("email") String email
    );


}
