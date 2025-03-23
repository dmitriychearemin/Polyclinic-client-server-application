package com.example.demo.Controllers;

import com.example.demo.DTO.AnalysisResultDTO;
import com.example.demo.Entities.AnalysisResult;
import com.example.demo.Entities.Doctor;
import com.example.demo.Entities.Patient;
import com.example.demo.Interfaces.AnalysisResultRepository;
import com.example.demo.Interfaces.DoctorRepository;
import com.example.demo.Interfaces.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analyses")
@RequiredArgsConstructor
public class AnalysisResultController {
    private final AnalysisResultRepository analysisResultRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;


    @GetMapping("/{id}")
    public ResponseEntity<AnalysisResultDTO> getAnalysisById(@PathVariable Long id) {
        AnalysisResult analysis = analysisResultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Analysis not found"));
        return ResponseEntity.ok(new AnalysisResultDTO(analysis));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnalysisResultDTO> updateAnalysis(
            @PathVariable Long id,
            @RequestBody AnalysisResultDTO request
    ) {
        AnalysisResult analysis = analysisResultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Analysis not found"));

        analysis.setTestType(request.getTestType());
        analysis.setTestDate(request.getTestDate());
        analysis.setResult(request.getResult());

        if (request.getDoctorId() != null) {
            Doctor doctor = doctorRepository.findById(request.getDoctorId())
                    .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
            analysis.setDoctor(doctor);
        }

        AnalysisResult savedAnalysis = analysisResultRepository.save(analysis);
        return ResponseEntity.ok(new AnalysisResultDTO(savedAnalysis));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnalysis(@PathVariable Long id) {
        analysisResultRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<AnalysisResultDTO> createAnalysis(@RequestBody AnalysisResultDTO request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        AnalysisResult analysis = new AnalysisResult();
        analysis.setPatient(patient);
        analysis.setDoctor(doctor);
        analysis.setTestType(request.getTestType());
        analysis.setTestDate(request.getTestDate());
        analysis.setResult(request.getResult());

        AnalysisResult savedAnalysis = analysisResultRepository.save(analysis);
        return ResponseEntity.ok(new AnalysisResultDTO(savedAnalysis));
    }
}