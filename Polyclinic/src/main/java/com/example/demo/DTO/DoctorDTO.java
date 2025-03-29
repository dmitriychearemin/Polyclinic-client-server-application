package com.example.demo.DTO;

import com.example.demo.Entities.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public  class DoctorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
    private String email;
    private String phone;
    private Department department;
    private LocalDate hireDate;
    private Long departmentId;
    private String departmentName;

    public DoctorDTO(Long id,
                     String firstName,
                     String lastName,
                     String specialization,
                     String email,
                     String phone,
                     Department department,
                     String departmentName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.departmentName = departmentName;
    }

}