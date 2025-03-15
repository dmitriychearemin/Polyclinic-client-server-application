package com.example.demo.Services;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DepartmentRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private Long parentId;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be a positive number")
    private Integer capacity;



}