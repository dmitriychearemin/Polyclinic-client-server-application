package com.example.demo.Services;

import com.example.demo.Entities.Department;
import com.example.demo.Entities.Patient;
import com.example.demo.Interfaces.DepartmentRepository;
import com.example.demo.Interfaces.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final DepartmentRepository departmentRepository;


    public Patient createPatient(Patient patient, Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));
        patient.setDepartment(department);
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        patient.setFirstName(updatedPatient.getFirstName());
        patient.setLastName(updatedPatient.getLastName());
        patient.setBirthDate(updatedPatient.getBirthDate());
        patient.setPhone(updatedPatient.getPhone());
        patient.setEmail(updatedPatient.getEmail());
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public List<Patient> searchPatients(String query) {
        return patientRepository.findByFirstNameContainingOrLastNameContaining(query, query);
    }

    @Override
    public String toString() {
        return "PatientService{" +
                "patientRepository=" + patientRepository +
                ", departmentRepository=" + departmentRepository +
                '}';
    }
}
