package com.assessment.WishlistManagement.Service;

import com.assessment.WishlistManagement.Model.Employee;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface IEmployeeUserService {

    public Employee addEmployee(Employee employee, String url);

    public void removeSessionMessage();

    public BCryptPasswordEncoder bCryptPasswordEncoder();

    public Employee findByEmail(String email);

    public boolean findByVerificationCode(String code);



}
