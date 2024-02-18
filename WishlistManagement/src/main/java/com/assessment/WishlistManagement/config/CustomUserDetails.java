package com.assessment.WishlistManagement.config;

import com.assessment.WishlistManagement.Model.Employee;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * CustomUserDetails class implements the UserDetails interface provided by Spring Security.
 * It represents a custom UserDetails object containing user-specific data retrieved from the database.
 */
@Data
public class CustomUserDetails implements UserDetails {


    private Employee employee;


    /**
     * Constructs a new CustomUserDetails object with the provided Employee data.
     *
     * @param employee the Employee object representing the user
     */
    public  CustomUserDetails(Employee employee)
    {
        this.employee = employee;
    }

    /**
     * Retrieves the authorities (roles) granted to the user.
     *
     * @return a collection of GrantedAuthority objects representing the user's roles
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(employee.getRole());
        return List.of(authority);
    }

    /**
     * Retrieves the password used to authenticate the user.
     *
     * @return the password
     */
    @Override
    public String getPassword() {
        return employee.getPassword();
    }

    /**
     * Retrieves the username used to authenticate the user.
     *
     * @return the username (email)
     */

    @Override
    public String getUsername() {
        return employee.getEmail();
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return true if the user's account is not expired, false otherwise
     */

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     *
     * @return true if the user is not locked, false otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return employee.getIsAccountNonLocked();
    }

    /**
     * Indicates whether the user's credentials (password) has expired.
     *
     * @return true if the user's credentials are not expired, false otherwise
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    /**
     * Indicates whether the user is enabled or disabled.
     *
     * @return true if the user is enabled, false otherwise
     */
    @Override
    public boolean isEnabled() {
        return employee.getEnable();
    }
}
