package com.example.demo.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @Positive
    private Integer capacity;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Transient
    private boolean available = true;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Department parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Department> children = new HashSet<>();

    @Column(name = "current_number_of_patients")
    private Integer currentNumberOfPatients = 0;

    public void setParent(Department parent) {
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

    public Department getParent() {
        return parent;
    }

    public Set<Department> getChildren() {
        return children;
    }

    public void setChildren(Set<Department> children) {
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
        if (currentNumberOfPatients > this.capacity) {
            throw new IllegalArgumentException("Current number of patients cannot exceed capacity");
        }
        this.currentNumberOfPatients = currentNumberOfPatients;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCurrentNumberOfPatients(Integer currentNumberOfPatients) {
        this.currentNumberOfPatients = currentNumberOfPatients;
    }
}