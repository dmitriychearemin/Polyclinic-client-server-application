package com.example.demo.Controllers;

import com.example.demo.DTO.DepartmentWithDoctorsDTO;
import com.example.demo.DTO.DoctorDTO;
import com.example.demo.Entities.Department;
import com.example.demo.Entities.Doctor;
import com.example.demo.Interfaces.DoctorRepository;
import com.example.demo.Services.DepartmentRequest;
import com.example.demo.Services.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DoctorRepository doctorRepository;


    @GetMapping
    public List<DepartmentWithDoctorsDTO> getAllDepartments() {
        List<Department> departments = departmentService.getDepartmentTree();
        return departments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private DepartmentWithDoctorsDTO convertToDTO(Department department) {
        DepartmentWithDoctorsDTO dto = new DepartmentWithDoctorsDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setDescription(department.getDescription());
        dto.setCapacity(department.getCapacity());
        dto.setCurrentNumberOfPatients(department.getCurrentNumberOfPatients());
        dto.setAvailable(department.isAvailable());

        if (department.getParent() != null) {
            DepartmentWithDoctorsDTO.DepartmentParentDTO parentDTO =
                    new DepartmentWithDoctorsDTO.DepartmentParentDTO(
                            department.getParent().getId(),
                            department.getParent().getName()
                    );
            dto.setParent(parentDTO);
        }

        // Обработка children
        if (department.getChildren() != null) {
            Set<DepartmentWithDoctorsDTO> childrenDTOs = department.getChildren()
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toSet());
            dto.setChildren(childrenDTOs);
        }

        List<Doctor> doctors = doctorRepository.findByDepartmentId(department.getId());
        dto.setDoctors(doctors.stream()
                .map(doctor -> new DoctorDTO(
                        doctor.getId(),
                        doctor.getFirstName(),
                        doctor.getLastName(),
                        doctor.getSpecialization(),
                        doctor.getEmail(),
                        doctor.getPhone(),
                        doctor.getDepartment()))
                .collect(Collectors.toList()));

        return dto;
    }

    private DoctorDTO convertDoctorToDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setFirstName(doctor.getFirstName());
        dto.setLastName(doctor.getLastName());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setEmail(doctor.getEmail());
        dto.setPhone(doctor.getPhone());
        return dto;
    }

    @PostMapping
    public ResponseEntity<?> createDepartment(@Valid @RequestBody DepartmentRequest request) {
        try {
            Department savedDepartment = departmentService.createDepartment(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDepartment);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred: " + e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<DepartmentWithDoctorsDTO> getDepartmentDetails(@PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id);
        List<Doctor> doctors = doctorRepository.findByDepartmentId(id);
        DepartmentWithDoctorsDTO dto = new DepartmentWithDoctorsDTO(department, doctors);

        // Добавляем children, если нужно
        if (department.getChildren() != null) {
            Set<DepartmentWithDoctorsDTO> childrenDTOs = department.getChildren()
                    .stream()
                    .map(child -> convertToDTO(child))
                    .collect(Collectors.toSet());
            dto.setChildren(childrenDTOs);
        }

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        try {
            departmentService.deleteDepartment(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deleting department: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentRequest request) {
        try {
            Department updatedDepartment = departmentService.updateDepartment(id, request);
            return ResponseEntity.ok(updatedDepartment);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating department: " + e.getMessage());
        }
    }
}
