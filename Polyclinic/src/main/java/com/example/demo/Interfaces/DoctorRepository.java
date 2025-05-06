package com.example.demo.Interfaces;

import com.example.demo.Entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    //@Query("SELECT d FROM Doctor d WHERE d.department = :departmentId") @Param("departmentId")
    List<Doctor> findByDepartmentId(Long departmentId);

    @Query("SELECT COUNT(d) > 0 FROM Doctor d WHERE " +
            "LOWER(d.firstName) = LOWER(:firstName) AND " +
            "LOWER(d.lastName) = LOWER(:lastName) AND " +
            "d.specialization = :specialization AND " +
            "d.hireDate = :hireDate AND " +
            "d.phone = :phone AND " +
            "(d.email IS NULL AND :email IS NULL OR LOWER(d.email) = LOWER(:email))")
    boolean existsByUniqueFields(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("specialization") String specialization,
            @Param("hireDate") LocalDate hireDate,
            @Param("phone") String phone,
            @Param("email") String email
    );

    @Query("SELECT COUNT(d) > 0 FROM Doctor d WHERE " +
            "d.id != :id AND " +
            "LOWER(d.firstName) = LOWER(:firstName) AND " +
            "LOWER(d.lastName) = LOWER(:lastName) AND " +
            "d.specialization = :specialization AND " +
            "d.hireDate = :hireDate AND " +
            "d.phone = :phone AND " +
            "(d.email IS NULL AND :email IS NULL OR LOWER(d.email) = LOWER(:email))")
    boolean existsByUniqueFieldsExcludingId(
            @Param("id") Long id,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("specialization") String specialization,
            @Param("hireDate") LocalDate hireDate,
            @Param("phone") String phone,
            @Param("email") String email
    );

}