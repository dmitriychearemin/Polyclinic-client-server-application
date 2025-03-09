package Interfaces;

import Entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Найти все корневые отделения (без родителя)
    List<Department> findByParentIsNull();
}