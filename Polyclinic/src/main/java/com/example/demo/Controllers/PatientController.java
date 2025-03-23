package com.example.demo.Controllers;

import com.example.demo.DTO.AnalysisResultDTO;
import com.example.demo.DTO.PatientDTO;
import com.example.demo.Entities.Patient;
import com.example.demo.Interfaces.PatientRepository;
import com.example.demo.Services.PatientService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
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
    public ResponseEntity<?> createPatient(@Valid @RequestBody PatientDTO patientDTO) {
        try {
            Patient createdPatient = patientService.createPatient(patientDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new PatientDTO(createdPatient));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating patient: " + e.getMessage());
        }
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
    public ResponseEntity<?> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientDTO patientDTO
    ) {
        try {
            patientDTO.setId(id);
            Patient updatedPatient = patientService.updatePatient(id, patientDTO);
            return ResponseEntity.ok(new PatientDTO(updatedPatient));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", e.getMessage())); // Возвращаем JSON
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Collections.singletonMap("error", "Error updating patient: " + e.getMessage()));
        }
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
