package com.example.demo.Interfaces;

import com.example.demo.Entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
        @Query("SELECT d FROM Department d " +
                "LEFT JOIN FETCH d.parent " +
                "WHERE d.id = :id")
        Optional<Department> findByIdWithParent(@Param("id") Long id);

    List<Department> findByParentIsNull();
}