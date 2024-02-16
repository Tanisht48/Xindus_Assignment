package com.assessment.WishlistManagement.Repository;

import com.assessment.WishlistManagement.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;



public interface IEmployeeRepository extends JpaRepository<Employee,Long> {


    Employee findByEmail(String email);

    Employee findByVerificationCode(String code);
}
