package Interfaces;

import Entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByDepartmentId(Long departmentId);
    List<Patient> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);

}
