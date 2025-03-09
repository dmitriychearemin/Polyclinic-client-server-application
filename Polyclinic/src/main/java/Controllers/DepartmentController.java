package Controllers;

import Entities.Department;
import Services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getDepartmentTree();
    }

    @PostMapping
    public Department createDepartment(@RequestBody Department department,
                                       @RequestParam(required = false) Long parentId) {
        return departmentService.createDepartment(department, parentId);
    }

    public DepartmentService getDepartmentService() {
        return departmentService;
    }

    @GetMapping("/{id}")
    public Department getDepartmentDetails(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        return departmentService.updateDepartment(id, department);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }

    @Override
    public String toString() {
        return "DepartmentController{" +
                "departmentService=" + departmentService +
                '}';
    }
}
