package com.example.demo.Controllers;

import com.example.demo.Services.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewController {
    private final DepartmentService departmentService;

    public ViewController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("departments", departmentService.getDepartmentTree());
        return "index";
    }

    @GetMapping("/department/{id}")
    public String departmentDetails(@PathVariable Long id, Model model) {
        model.addAttribute("department", departmentService.getDepartmentById(id));
        return "department-details";
    }
}
