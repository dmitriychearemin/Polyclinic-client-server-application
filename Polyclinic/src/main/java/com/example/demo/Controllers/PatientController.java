package com.example.demo.Controllers;

import com.example.demo.Entities.Patient;
import com.example.demo.Services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

/*    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }*/

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient,
                                 @RequestParam Long departmentId) {
        return patientService.createPatient(patient, departmentId);
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

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }
}
