package com.assessment.WishlistManagement.Service;

import com.assessment.WishlistManagement.Model.Employee;
import com.assessment.WishlistManagement.Model.dto.EmployeeDto;
import com.assessment.WishlistManagement.Repository.IEmployeeRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.UUID;

/**
 * Service class for managing employee users.
 */
@Service
public class EmployeeService implements IEmployeeUserService{

    @Autowired
    private IEmployeeRepository iEmployeeRepository;


    /**
     * Adds a new employee user.
     *
     * @param employeeDto the DTO object representing the employee details
     * @return the added Employee object
     */

    @Override
    public Employee addEmployee(EmployeeDto employeeDto) {
        if (employeeDto == null || employeeDto.getName() == null || employeeDto.getEmail() == null || employeeDto.getPassword() == null) {
            throw new IllegalArgumentException("EmployeeDto and its properties cannot be null");
        }

        String encryptPassword = bCryptPasswordEncoder().encode(employeeDto.getPassword());
        if (encryptPassword == null) {
            throw new RuntimeException("Failed to encrypt password");
        }

        Employee employee = new Employee(employeeDto.getName(), employeeDto.getEmail());
        employee.setPassword(encryptPassword);
        employee.setRole("ROLE_USER");
        employee.setEnable(true);
        employee.setVerificationCode(UUID.randomUUID().toString());
        employee.setIsAccountNonLocked(true);

        try {
           iEmployeeRepository.save(employee);

            return employee;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save employee", e);
        }
    }


    /**
     * Retrieves the BCryptPasswordEncoder instance.
     *
     * @return the BCryptPasswordEncoder instance
     */
    @Override
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Finds an employee user by email.
     *
     * @param email the email of the employee user to find
     * @return the found Employee object, or null if not found
     */
    @Override
    public Employee findByEmail(String email) {
        return iEmployeeRepository.findByEmail(email);
    }

    /**
     * Finds an employee user by verification code and enables the account if found.
     *
     * @param code the verification code to search for
     * @return true if a matching user is found and account is enabled, false otherwise
     */
    @Override
    @Transactional
    public boolean findByVerificationCode(String code) {
        Employee employee = iEmployeeRepository.findByVerificationCode(code);
        if(employee != null){
            employee.setVerificationCode(null);
            employee.setEnable(true);
            iEmployeeRepository.save(employee);
            return true;
        }
        else return false;
    }

}
