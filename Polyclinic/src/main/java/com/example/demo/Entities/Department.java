package com.example.demo.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.misc.NotNull;

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

    private String name;

    @Transient
    private boolean available = true;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Department parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Department> children = new HashSet<>();

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "current_number_of_patients")
    private Integer currentNumberOfPatients;

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
}