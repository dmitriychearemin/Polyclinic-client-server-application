package com.example.demo.Controllers;

import com.example.demo.Entities.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private String departmentName;

    public PatientDTO(Patient patient) {
        this.id = patient.getId();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.birthDate = patient.getBirthDate();
        this.phone = patient.getPhone();
        this.email = patient.getEmail();
        this.departmentName = patient.getDepartment() != null ?
                patient.getDepartment().getName() : "N/A";
    }
}