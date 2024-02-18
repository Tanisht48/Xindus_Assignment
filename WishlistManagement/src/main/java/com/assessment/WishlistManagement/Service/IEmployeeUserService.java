package com.assessment.WishlistManagement.Service;

import com.assessment.WishlistManagement.Model.Employee;
import com.assessment.WishlistManagement.Model.dto.EmployeeDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface IEmployeeUserService {

    public Employee addEmployee(EmployeeDto employeeDto);


    public BCryptPasswordEncoder bCryptPasswordEncoder();

    public Employee findByEmail(String email);

    public boolean findByVerificationCode(String code);



}
