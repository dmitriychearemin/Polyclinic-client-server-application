package com.example.demo.Controllers;

import com.example.demo.Entities.Patient;
import com.example.demo.Interfaces.PatientRepository;
import com.example.demo.Services.PatientService;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {
    @Getter
    private final PatientService patientService;
    private final PatientRepository patientRepository;


    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(
            @RequestBody Patient patient,
            @RequestParam Long departmentId
    ) {
        Patient createdPatient = patientService.createPatient(patient, departmentId);
        return ResponseEntity.ok(new PatientDTO(createdPatient));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        return patientRepository.findById(id)
                .map(patient -> ResponseEntity.ok(new PatientDTO(patient)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-department/{departmentId}")
    public ResponseEntity<List<PatientDTO>> getPatientsByDepartment(@PathVariable Long departmentId) {
        List<PatientDTO> patients = patientRepository.findByDepartmentId(departmentId).stream()
                .map(PatientDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PatientDTO>> searchPatients(@RequestParam String query) {
        List<PatientDTO> patients = patientService.searchPatients(query).stream()
                .map(PatientDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(patients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        Patient updatedPatient = patientService.updatePatient(id, patient);
        return ResponseEntity.ok(new PatientDTO(updatedPatient));
    }

    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        List<PatientDTO> patients = patientRepository.findAll().stream()
                .map(PatientDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(patients);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/analyses")
    public ResponseEntity<List<AnalysisResultDTO>> getPatientAnalyses(@PathVariable Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        List<AnalysisResultDTO> analyses = patient.getAnalysisResults().stream()
                .map(AnalysisResultDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(analyses);
    }
}
