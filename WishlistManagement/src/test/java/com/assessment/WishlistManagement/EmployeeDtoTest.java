package com.assessment.WishlistManagement;

import com.assessment.WishlistManagement.Model.dto.EmployeeDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeDtoTest {
    @Test
    void testEmployeeDtoValidation_ValidDto() {
        EmployeeDto employeeDto = new EmployeeDto("John", "john@example.com", "password");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        assertTrue(validator.validate(employeeDto).isEmpty());
    }

    @Test
    void testEmployeeDtoValidation_InvalidDto() {
        EmployeeDto employeeDto = new EmployeeDto("", "johnexample.com", "pass");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        assertFalse(validator.validate(employeeDto).isEmpty());
    }
}
