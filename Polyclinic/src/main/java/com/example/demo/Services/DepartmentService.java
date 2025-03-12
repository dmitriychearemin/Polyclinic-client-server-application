package com.example.demo.Services;

import com.example.demo.Entities.Department;
import com.example.demo.Interfaces.DepartmentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    @Autowired
    private EntityManager entityManager;
    private final DepartmentRepository departmentRepository;

    public Department updateDepartment(Long id, Department updatedDepartment) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));
        department.setName(updatedDepartment.getName());
        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findByIdWithParent(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));
    }

    public Department createDepartment(DepartmentRequest request) {
        Department department = new Department();
        department.setName(request.getName());
        department.setDescription(request.getDescription());
        department.setCapacity(request.getCapacity());

        // Если parentId указан, находим родительский департамент
        if (request.getParentId() != null) {
            Department parent = departmentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent department not found"));
            department.setParent(parent);
        }

        // Поле createdAt заполнится автоматически благодаря аннотации @CreationTimestamp
        return departmentRepository.save(department);
    }

    public List<Department> getDepartmentTree() {

        List<Department> departments = departmentRepository.findByParentIsNull();

        for (var d : departments) {
            if (d.getCapacity()  <= d.getCurrentNumberOfPatients()) {
                d.setAvailable(false);
            }
        }

        return departments;
    }

    public DepartmentRepository getDepartmentRepository() {
        return departmentRepository;
    }

    @Override
    public String toString() {
        return "DepartmentService{" +
                "departmentRepository=" + departmentRepository +
                '}';
    }
}
