package com.luv2code.springboot.cruddemo.rest;

import com.luv2code.springboot.cruddemo.dao.EmployeeDAO;
import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

//    private EmployeeDAO employeeDAO;
    private EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
//        this.employeeDAO = employeeDAO;
        this.employeeService = employeeService;
    }

    // expose "/employees" and return list of employees
    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    // add mapping for GET /employees/{employeeId}
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId) {

        Employee employee = employeeService.findById(employeeId);
        if (employee == null) throw new RuntimeException("Employee id not found- " + employeeId);

        return employee;
    }

    // add mapping for POST /employees - add new employee
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {

        // also jst in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update
        employee.setId(0);

        employeeService.save(employee);
        return employee;
    }

    // add mapping for PUT /employees - update existing employee
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {

        employeeService.save(employee);
        return employee;
    }

    // add mapping for DELETE /employees/{employeeId} - delete employee
    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) {

        Employee employee = employeeService.findById(employeeId);

        // throw exception if null
        if (employee == null) throw new RuntimeException("Employee not found: " + employeeId);

        employeeService.deleteById(employeeId);
        return "Delete employee by employeeId: " + employeeId;
    }

}
