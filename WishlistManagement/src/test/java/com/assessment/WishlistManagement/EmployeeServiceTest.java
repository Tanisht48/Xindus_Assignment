package com.assessment.WishlistManagement;

import com.assessment.WishlistManagement.Model.Employee;
import com.assessment.WishlistManagement.Model.dto.EmployeeDto;
import com.assessment.WishlistManagement.Repository.IEmployeeRepository;
import com.assessment.WishlistManagement.Service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {
    @Mock
    private IEmployeeRepository iEmployeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddEmployee() {
        EmployeeDto employeeDto = new EmployeeDto("John", "john@example.com", "password");

        when(iEmployeeRepository.save(any(Employee.class))).thenReturn(new Employee());

        Employee result = employeeService.addEmployee(employeeDto);

        assertNotNull(result);
        assertEquals("ROLE_USER", result.getRole());
        assertTrue(result.getEnable());
        assertNotNull(result.getVerificationCode());
        assertTrue(result.getIsAccountNonLocked());
    }

    @Test
    void testAddEmployee_DataIntegrityViolationException() {
        EmployeeDto employeeDto = new EmployeeDto("John", "john@example.com", "password");

        when(iEmployeeRepository.save(any(Employee.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> employeeService.addEmployee(employeeDto));
    }
}
