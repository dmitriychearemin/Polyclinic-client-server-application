package com.example.demo.Interfaces;

import com.example.demo.Entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Найти все корневые отделения (без родителя)
    //@Query("SELECT d.id, d.name, d.capacity, d.current_number_of_patients FROM departments d WHERE d.parent_id IS NULL")
    List<Department> findByParentIsNull();
}