package com.assessment.WishlistManagement.config;

import com.assessment.WishlistManagement.Model.Employee;
import com.assessment.WishlistManagement.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeService employeeService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         Employee employee = employeeService.findByEmail(email);

         if(employee == null){
             throw  new UsernameNotFoundException("Username Not Found");
         }
         else {
             return new CustomUserDetails(employee);
         }
    }
}
