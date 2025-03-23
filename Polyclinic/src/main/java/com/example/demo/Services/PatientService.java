package com.example.demo.Services;

import com.example.demo.DTO.PatientDTO;
import com.example.demo.Entities.Department;
import com.example.demo.Entities.Patient;
import com.example.demo.Interfaces.DepartmentRepository;
import com.example.demo.Interfaces.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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


    public Patient createPatient(PatientDTO patientDTO) {
        Department department = departmentRepository.findById(patientDTO.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));

        if (department.getCurrentNumberOfPatients() >= department.getCapacity()) {
            throw new IllegalStateException("Department is full");
        }

        Patient patient = new Patient();
        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setBirthDate(patientDTO.getBirthDate());
        patient.setPhone(patientDTO.getPhone());
        patient.setEmail(patientDTO.getEmail());
        patient.setDepartment(department);
        patient.setGender(patientDTO.getGender());

        department.setCurrentNumberOfPatients(department.getCurrentNumberOfPatients() + 1);
        departmentRepository.save(department);

        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long id, PatientDTO patientDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        Department department = departmentRepository.findById(patientDTO.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));

        if (!patient.getDepartment().getId().equals(patientDTO.getDepartmentId())) {
            Department oldDepartment = patient.getDepartment();
            oldDepartment.setCurrentNumberOfPatients(oldDepartment.getCurrentNumberOfPatients() - 1);
            departmentRepository.save(oldDepartment);

            department.setCurrentNumberOfPatients(department.getCurrentNumberOfPatients() + 1);
            departmentRepository.save(department);
        }

        if (patientDTO.getId() == null) {
            throw new IllegalArgumentException("Patient ID must not be null");
        }


        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setBirthDate(patientDTO.getBirthDate());
        patient.setPhone(patientDTO.getPhone());
        patient.setEmail(patientDTO.getEmail());
        patient.setDepartment(department);
        patient.setGender(patientDTO.getGender());


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
