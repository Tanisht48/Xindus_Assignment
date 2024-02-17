package com.assessment.WishlistManagement.Controller;

import com.assessment.WishlistManagement.Model.Employee;
import com.assessment.WishlistManagement.Model.dto.EmployeeDto;
import com.assessment.WishlistManagement.Service.EmployeeService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
@Validated
public class HomeController {
   @Autowired
    private EmployeeService employeeService;
//
//    @GetMapping("/normal")
//    public ResponseEntity<String> normalUser(){
//        return ResponseEntity.ok("Yes i am a normal user");
//    }
//    @GetMapping("/admin")
//    public ResponseEntity<String> adminUser(){
//        return ResponseEntity.ok("Yes i am a admin user");
//    }
//
//    @GetMapping("/public")
//    public ResponseEntity<String> publicUser(){
//        return ResponseEntity.ok("Yes i am a public user");
//    }

//This Is The API For Registering a new User
    @Hidden
    @PostMapping("/register")
public ResponseEntity<String> register(@Valid @RequestBody EmployeeDto employeeDto) {
        try {
            Employee employee = employeeService.addEmployee(employeeDto);
            if (employee == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong..");
            } else {
                return ResponseEntity.ok("Registered Successfully");
            }
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }
    }

}
