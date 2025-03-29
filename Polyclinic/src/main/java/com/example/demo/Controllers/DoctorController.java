package com.example.demo.Controllers;

import com.example.demo.DTO.DepartmentWithDoctorsDTO;
import com.example.demo.DTO.DoctorDTO;
import com.example.demo.Entities.Department;
import com.example.demo.Entities.Doctor;
import com.example.demo.Interfaces.DepartmentRepository;
import com.example.demo.Interfaces.DoctorRepository;
import com.example.demo.Interfaces.DoctorRepository;
import com.example.demo.Services.DoctorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final DoctorService doctorService;



    public DoctorController(DoctorRepository doctorRepository,
                            DepartmentRepository departmentRepository, DoctorService doctorService) {
        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
        this.doctorService = doctorService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id) {
        return doctorRepository.findById(id)
                .map(doctor -> ResponseEntity.ok(new DoctorDTO(
                        doctor.getId(),
                        doctor.getFirstName(),
                        doctor.getLastName(),
                        doctor.getSpecialization(),
                        doctor.getEmail(),
                        doctor.getPhone(),
                        doctor.getDepartment() != null ? doctor.getDepartment() : null)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createDoctor(@RequestBody DoctorDTO request) {
        try {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));

            Doctor doctor = new Doctor();
            doctor.setFirstName(request.getFirstName());
            doctor.setLastName(request.getLastName());
            doctor.setSpecialization(request.getSpecialization());
            doctor.setDepartment(department);
            doctor.setHireDate(request.getHireDate());
            doctor.setEmail(request.getEmail());
            doctor.setPhone(request.getPhone());

            Doctor savedDoctor = doctorRepository.save(doctor);
            return ResponseEntity.ok(savedDoctor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctor(
            @PathVariable Long id,
            @RequestBody DoctorDTO doctorDTO
    ) {
        try {
            Doctor updatedDoctor = doctorService.updateDoctor(id, doctorDTO);
            return ResponseEntity.ok(convertToDTO(updatedDoctor));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating doctor: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
        try {
            doctorService.deleteDoctor(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deleting doctor: " + e.getMessage());
        }
    }

    private DoctorDTO convertToDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setFirstName(doctor.getFirstName());
        dto.setLastName(doctor.getLastName());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setEmail(doctor.getEmail());
        dto.setPhone(doctor.getPhone());
        dto.setHireDate(doctor.getHireDate());
        if (doctor.getDepartment() != null) {
            dto.setDepartmentId(doctor.getDepartment().getId());
        }
        return dto;
    }

    @GetMapping("/for-selection")
    public ResponseEntity<List<DoctorDTO>> getDoctorsForSelection() {
        List<DoctorDTO> doctors = doctorRepository.findAll().stream()
                .map(d -> new DoctorDTO(
                        d.getId(),
                        d.getFirstName(),
                        d.getLastName(),
                        d.getSpecialization(),
                        d.getPhone(),
                        d.getEmail(),
                        d.getDepartment()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(doctors);
    }
}