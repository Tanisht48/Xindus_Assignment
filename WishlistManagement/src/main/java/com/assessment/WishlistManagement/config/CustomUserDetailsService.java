package com.assessment.WishlistManagement.config;

import com.assessment.WishlistManagement.Model.Employee;
import com.assessment.WishlistManagement.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * CustomUserDetailsService is responsible for loading user-specific data during the authentication process.
 * It implements the UserDetailsService interface provided by Spring Security.
 */
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Load user by username (email in this case) from the database.
     *
     * @param email the email address of the user
     * @return UserDetails object representing the user
     * @throws UsernameNotFoundException if the user is not found
     */
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
