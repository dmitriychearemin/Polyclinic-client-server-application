package com.example.demo.Controllers;

import com.example.demo.DTO.DepartmentWithDoctorsDTO;
import com.example.demo.DTO.DoctorDTO;
import com.example.demo.Entities.Department;
import com.example.demo.Entities.Doctor;
import com.example.demo.Interfaces.DepartmentRepository;
import com.example.demo.Interfaces.DoctorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;



    public DoctorController(DoctorRepository doctorRepository,
                            DepartmentRepository departmentRepository) {
        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
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

}