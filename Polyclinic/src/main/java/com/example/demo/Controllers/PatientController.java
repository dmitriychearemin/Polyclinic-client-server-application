package com.example.demo.Controllers;

import com.example.demo.Entities.Patient;
import com.example.demo.Interfaces.PatientRepository;
import com.example.demo.Services.PatientService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final PatientRepository patientRepository;


    @PostMapping
    public Patient createPatient(@RequestBody Patient patient,
                                 @RequestParam Long departmentId) {
        return patientService.createPatient(patient, departmentId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        return patientRepository.findById(id)
                .map(patient -> ResponseEntity.ok(new PatientDTO(patient)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-department/{departmentId}")
    public List<Patient> getPatientsByDepartment(@PathVariable Long departmentId) {
        return patientService.getPatientRepository().findByDepartmentId(departmentId);
    }

    public PatientService getPatientService() {
        return patientService;
    }

    @GetMapping("/search")
    public List<Patient> searchPatients(@RequestParam String query) {
        return patientService.searchPatients(query);
    }

    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        return patientService.updatePatient(id, patient);
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getPatientRepository().findAll();
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }
}
