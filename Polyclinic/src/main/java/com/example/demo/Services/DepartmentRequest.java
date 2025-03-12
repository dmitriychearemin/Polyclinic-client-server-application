package com.example.demo.Services;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DepartmentRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private Long parentId;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be a positive number")
    private Integer capacity;

    // Геттеры и сеттеры

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}