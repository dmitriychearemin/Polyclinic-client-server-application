package com.example.demo.Controllers;

import com.example.demo.DTO.AnalysisResultDTO;
import com.example.demo.Entities.AnalysisResult;
import com.example.demo.Entities.Doctor;
import com.example.demo.Entities.Patient;
import com.example.demo.Interfaces.AnalysisResultRepository;
import com.example.demo.Interfaces.DoctorRepository;
import com.example.demo.Interfaces.PatientRepository;
import com.example.demo.Services.AnalysisResultService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analyses")
@RequiredArgsConstructor
public class AnalysisResultController {

    private final AnalysisResultService analysisResultService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnalysisById(@PathVariable Long id) {
        try {
            AnalysisResult analysis = analysisResultService.getAnalysisById(id);
            return ResponseEntity.ok(new AnalysisResultDTO(analysis));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createAnalysis(@RequestBody AnalysisResultDTO request) {
        try {
            AnalysisResult createdAnalysis = analysisResultService.createAnalysis(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new AnalysisResultDTO(createdAnalysis));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnalysis(
            @PathVariable Long id,
            @RequestBody AnalysisResultDTO request
    ) {
        try {
            AnalysisResult updatedAnalysis = analysisResultService.updateAnalysis(id, request);
            return ResponseEntity.ok(new AnalysisResultDTO(updatedAnalysis));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnalysis(@PathVariable Long id) {
        try {
            analysisResultService.deleteAnalysis(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}