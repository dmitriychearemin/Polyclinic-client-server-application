package com.example.demo.Controllers;

import com.example.demo.Entities.Department;
import com.example.demo.Services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        dto.setChildren(department.getChildren().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toSet()));
        dto.setAvailable(department.isAvailable());
        return dto;
    }

    @PostMapping
    public Department createDepartment(@RequestBody Department department,
                                       @RequestParam(required = false) Long parentId) {
        return departmentService.createDepartment(department, parentId);
    }

    public DepartmentService getDepartmentService() {
        return departmentService;
    }

    @GetMapping("/{id}")
    public Department getDepartmentDetails(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        return departmentService.updateDepartment(id, department);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }

    @Override
    public String toString() {
        return "DepartmentController{" +
                "departmentService=" + departmentService +
                '}';
    }
}
