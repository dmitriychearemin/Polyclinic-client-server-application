package Services;

import Entities.Department;
import Interfaces.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department updateDepartment(Long id, Department updatedDepartment) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));
        department.setName(updatedDepartment.getName());
        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));
    }

    public Department createDepartment(Department department, Long parentId) {
        if (parentId != null) {
            Department parent = departmentRepository.findById(parentId)
                    .orElseThrow(() -> new EntityNotFoundException("Parent department not found"));
            department.setParent(parent);
        }
        return departmentRepository.save(department);
    }

    public List<Department> getDepartmentTree() {
        return departmentRepository.findByParentIsNull();
    }

    public DepartmentRepository getDepartmentRepository() {
        return departmentRepository;
    }

    @Override
    public String toString() {
        return "DepartmentService{" +
                "departmentRepository=" + departmentRepository +
                '}';
    }
}
