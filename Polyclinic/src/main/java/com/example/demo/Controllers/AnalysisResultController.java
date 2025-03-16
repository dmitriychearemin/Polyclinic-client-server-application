package com.example.demo.Controllers;

import com.example.demo.Entities.AnalysisResult;
import com.example.demo.Entities.Doctor;
import com.example.demo.Interfaces.AnalysisResultRepository;
import com.example.demo.Interfaces.DoctorRepository;
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


    @GetMapping("/{id}")
    public ResponseEntity<AnalysisResultDTO> getAnalysisById(@PathVariable Long id) {
        AnalysisResult analysis = analysisResultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Analysis not found"));
        return ResponseEntity.ok(new AnalysisResultDTO(analysis));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnalysisResultDTO> updateAnalysis(
            @PathVariable Long id,
            @RequestBody AnalysisResult updatedAnalysis
    ) {
        AnalysisResult analysis = analysisResultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Analysis not found"));

        analysis.setTestType(updatedAnalysis.getTestType());
        analysis.setTestDate(updatedAnalysis.getTestDate());
        analysis.setResult(updatedAnalysis.getResult());

        if (updatedAnalysis.getDoctor() != null && updatedAnalysis.getDoctor().getId() != null) {
            Doctor doctor = doctorRepository.findById(updatedAnalysis.getDoctor().getId())
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
}