package com.example.demo.Controllers;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public class DepartmentDTO {
    private Long id;
    private String name;
    private String description; // Добавлено поле description
    private Set<DepartmentDTO> children;
    private int capacity;
    private int currentNumberOfPatients;
    private boolean available;
    private DepartmentParentDTO parent;

    @Getter
    @Setter
    public static class DepartmentParentDTO {
        private Long id;
        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public DepartmentParentDTO getParent() {
        return parent;
    }

    public void setParent(DepartmentParentDTO parent) {
        this.parent = parent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() { // Добавлен геттер для description
        return description;
    }

    public void setDescription(String description) { // Добавлен сеттер для description
        this.description = description;
    }

    public Set<DepartmentDTO> getChildren() {
        return children;
    }

    public void setChildren(Set<DepartmentDTO> children) {
        this.children = children;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrentNumberOfPatients() {
        return currentNumberOfPatients;
    }

    public void setCurrentNumberOfPatients(int currentNumberOfPatients) {
        this.currentNumberOfPatients = currentNumberOfPatients;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
