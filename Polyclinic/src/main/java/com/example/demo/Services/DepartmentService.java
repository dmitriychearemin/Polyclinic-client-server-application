package com.example.demo.Services;

import com.example.demo.Entities.Department;
import com.example.demo.Entities.Doctor;
import com.example.demo.Interfaces.DepartmentRepository;
import com.example.demo.Interfaces.DoctorRepository;
import com.example.demo.Requests.DepartmentRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    @Autowired
    private EntityManager entityManager;
    @Getter
    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    public Department updateDepartment(Long id, DepartmentRequest request) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));

        department.setName(request.getName());
        department.setDescription(request.getDescription());
        department.setCapacity(request.getCapacity());

        if (request.getParentId() != null) {
            Department parent = departmentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent department not found"));
            department.setParent(parent);
        } else {
            department.setParent(null);
        }

        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));

        if (!department.getChildren().isEmpty()) {
            throw new IllegalStateException("Cannot delete department with child departments");
        }

        List<Doctor> doctors = doctorRepository.findByDepartmentId(id);
        if (!doctors.isEmpty()) {
            throw new IllegalStateException("Cannot delete department with assigned doctors");
        }

        departmentRepository.delete(department);
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findByIdWithParent(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));    }

    public Department createDepartment(DepartmentRequest request) {
        Department department = new Department();
        department.setName(request.getName());
        department.setDescription(request.getDescription());
        department.setCapacity(request.getCapacity());

        if (request.getParentId() != null) {
            Department parent = departmentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent department not found"));
            department.setParent(parent);
        }

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

    @Override
    public String toString() {
        return "DepartmentService{" +
                "departmentRepository=" + departmentRepository +
                '}';
    }

    public List<Department> getAllDepartmentsFlat() {
        return departmentRepository.findAll();
    }
}
