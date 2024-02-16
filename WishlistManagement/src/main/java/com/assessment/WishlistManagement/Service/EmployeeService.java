package com.assessment.WishlistManagement.Service;

import com.assessment.WishlistManagement.Model.Employee;
import com.assessment.WishlistManagement.Repository.IEmployeeRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

public class EmployeeService implements IEmployeeUserService{

    @Autowired
    private IEmployeeRepository iEmployeeRepository;



    @Override
    public Employee addEmployee(Employee employee, String url) {
        String encryptPassword = bCryptPasswordEncoder().encode(employee.getPassword());
        employee.setPassword((encryptPassword));
        employee.setRole("ROLE_USER");
        employee.setEnable(false);
        employee.setVerificationCode(UUID.randomUUID().toString());
        employee.setIsAccountNonLocked(true);
        return iEmployeeRepository.save(employee);
    }

    @Override
    public void removeSessionMessage() {
        HttpSession httpSession = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        httpSession.removeAttribute("msg");
    }

    @Override
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public Employee findByEmail(String email) {
        return iEmployeeRepository.findByEmail(email);
    }

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
