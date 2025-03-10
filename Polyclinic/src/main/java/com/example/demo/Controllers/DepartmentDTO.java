package com.example.demo.Controllers;

import java.util.Set;

public class DepartmentDTO {
    private Long id;
    private String name;
    private Set<DepartmentDTO> children;
    private int capacity;
    private int currentNumberOfPatients;
    private boolean available;


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

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }


}
