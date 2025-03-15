package com.example.demo.Interfaces;

import com.example.demo.Entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    //@Query("SELECT d FROM Doctor d WHERE d.department = :departmentId") @Param("departmentId")
    List<Doctor> findByDepartmentId(Long departmentId);

}