package com.assessment.WishlistManagement.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String mobile;
    @Column(unique = true)
    private String email;
    private Boolean enable;
    private String password;
    private String role;  // ROLE_USER
    private String verificationCode;
    private Boolean isAccountNonLocked;
    private Integer failedAttempt;
    private Date lockTime;


}
