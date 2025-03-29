package com.example.demo.DTO;

import com.example.demo.Entities.Patient;
import lombok.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    @NotNull(message = "ID is required for update")
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private LocalDate birthDate;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\+?[0-9\\-\\s()]{7,20}$", message = "Invalid phone format")
    private String phone;

    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Department ID is required")
    private Long departmentId;

    @NotNull(message = "Gender ID is required")
    private String gender;

    private String departmentName;

    public PatientDTO(Patient patient) {
        this.id = patient.getId();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.birthDate = patient.getBirthDate();
        this.phone = patient.getPhone();
        this.email = patient.getEmail();
        this.gender = patient.getGender();
        this.departmentId = patient.getDepartment() != null ?
                patient.getDepartment().getId() : null;
        this.departmentName = patient.getDepartment() != null ?
                patient.getDepartment().getName() : "N/A";
    }

    

}