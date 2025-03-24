package com.example.demo.Services;

import com.example.demo.DTO.AnalysisResultDTO;
import com.example.demo.Entities.AnalysisResult;
import com.example.demo.Entities.Doctor;
import com.example.demo.Entities.Patient;
import com.example.demo.Interfaces.AnalysisResultRepository;
import com.example.demo.Interfaces.DoctorRepository;
import com.example.demo.Interfaces.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnalysisResultService {

    private final AnalysisResultRepository analysisResultRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AnalysisResult getAnalysisById(Long id) {
        return analysisResultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Analysis not found with id: " + id));
    }

    @Transactional
    public AnalysisResult createAnalysis(AnalysisResultDTO request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + request.getPatientId()));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id: " + request.getDoctorId()));

        AnalysisResult analysis = new AnalysisResult();
        analysis.setPatient(patient);
        analysis.setDoctor(doctor);
        analysis.setTestType(request.getTestType());
        analysis.setTestDate(request.getTestDate());
        analysis.setResult(request.getResult());

        return analysisResultRepository.save(analysis);
    }

    @Transactional
    public AnalysisResult updateAnalysis(Long id, AnalysisResultDTO request) {
        AnalysisResult analysis = analysisResultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Analysis not found with id: " + id));

        analysis.setTestType(request.getTestType());
        analysis.setTestDate(request.getTestDate());
        analysis.setResult(request.getResult());

        if (request.getDoctorId() != null) {
            Doctor doctor = doctorRepository.findById(request.getDoctorId())
                    .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id: " + request.getDoctorId()));
            analysis.setDoctor(doctor);
        }

        return analysisResultRepository.save(analysis);
    }

    @Transactional
    public void deleteAnalysis(Long id) {
        if (!analysisResultRepository.existsById(id)) {
            throw new EntityNotFoundException("Analysis not found with id: " + id);
        }
        analysisResultRepository.deleteById(id);
    }
}