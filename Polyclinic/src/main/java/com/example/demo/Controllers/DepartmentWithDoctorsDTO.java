package com.example.demo.Controllers;

import com.example.demo.Entities.Department;
import com.example.demo.Entities.Doctor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentWithDoctorsDTO {
    private Long id;
    private String name;
    private String description;
    private boolean available;
    private Integer capacity;
    private Integer currentNumberOfPatients;
    private DepartmentParentDTO parent;
    private Set<DepartmentWithDoctorsDTO> children;

    private List<DoctorDTO> doctors;

    public DepartmentWithDoctorsDTO(Department department, List<Doctor> doctors) {
        this.id = department.getId();
        this.name = department.getName();
        this.description = department.getDescription();
        this.available = department.isAvailable(); // Добавлено поле available
        this.capacity = department.getCapacity();
        this.currentNumberOfPatients = department.getCurrentNumberOfPatients();
        this.parent = department.getParent() != null ?
                new DepartmentParentDTO(department.getParent().getId(), department.getParent().getName()) : null;
        this.doctors = doctors.stream()
                .map(doctor -> new DoctorDTO(
                        doctor.getId(),
                        doctor.getFirstName(),
                        doctor.getLastName(),
                        doctor.getSpecialization(),
                        doctor.getEmail(),
                        doctor.getPhone(),
                        doctor.getDepartment()))
                .collect(Collectors.toList());
    }

    @Getter
    @AllArgsConstructor
    //@NoArgsConstructor
    public static class DepartmentParentDTO {
        private Long id;
        private String name;


    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DoctorDTO {
        private Long id;
        private String firstName;
        private String lastName;
        private String specialization;
        private String email;
        private String phone;
        private Department department;

    }


}
