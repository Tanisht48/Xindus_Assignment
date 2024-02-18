package com.assessment.WishlistManagement.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity class representing an employee.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    @Column(unique = true)
    private String email;
    private Boolean enable;
    private String password;
    private String role;  // ROLE_USER
    private String verificationCode;
    private Boolean isAccountNonLocked;
    private Integer failedAttempt;


    /**
     * Constructor with parameters.
     *
     * @param name  the name of the employee
     * @param email the email of the employee
     */

    public Employee(String name,String email)
    {
        this.name = name;
        this.email = email;
    }

}
