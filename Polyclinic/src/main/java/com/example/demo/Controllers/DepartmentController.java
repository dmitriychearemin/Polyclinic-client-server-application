package com.example.demo.Controllers;

import com.example.demo.Entities.Department;
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

    @GetMapping
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentService.getDepartmentTree();
        return departments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private DepartmentDTO convertToDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setDescription(department.getDescription());
        dto.setCapacity(department.getCapacity());
        dto.setCurrentNumberOfPatients(department.getCurrentNumberOfPatients());
        dto.setAvailable(department.isAvailable());

        if (department.getParent() != null) {
            DepartmentDTO.DepartmentParentDTO parentDTO = new DepartmentDTO.DepartmentParentDTO();
            parentDTO.setId(department.getParent().getId());
            parentDTO.setName(department.getParent().getName());
            dto.setParent(parentDTO);
        }

        if (department.getChildren() != null) {
            Set<DepartmentDTO> childrenDTOs = department.getChildren().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toSet());
            dto.setChildren(childrenDTOs);
        }

        return dto;
    }

    @PostMapping
    public ResponseEntity<?> createDepartment(@Valid @RequestBody DepartmentRequest request) {
        try {
            // Делегируем создание департамента сервису
            Department savedDepartment = departmentService.createDepartment(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDepartment);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred: " + e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public DepartmentDTO getDepartmentDetails(@PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id);
        return convertToDTO(department);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }
}
