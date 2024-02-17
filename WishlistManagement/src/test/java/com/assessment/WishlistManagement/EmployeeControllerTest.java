package com.assessment.WishlistManagement;

import com.assessment.WishlistManagement.Controller.EmployeeController;
import com.assessment.WishlistManagement.Model.Employee;
import com.assessment.WishlistManagement.Model.dto.EmployeeDto;
import com.assessment.WishlistManagement.Service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegister_Success() {
        EmployeeDto employeeDto = new EmployeeDto("John", "john@example.com", "password");
        Employee savedEmployee = new Employee();
        when(employeeService.addEmployee(any(EmployeeDto.class))).thenReturn(savedEmployee);

        ResponseEntity<String> response = employeeController.register(employeeDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Registered Successfully", response.getBody());
    }

    @Test
    void testRegister_InternalServerError() {
        EmployeeDto employeeDto = new EmployeeDto("John", "john@example.com", "password");
        when(employeeService.addEmployee(any(EmployeeDto.class))).thenReturn(null);

        ResponseEntity<String> response = employeeController.register(employeeDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Something went wrong..", response.getBody());
    }

    @Test
    void testRegister_BadRequest() {
        EmployeeDto employeeDto = new EmployeeDto("John", "john@example.com", "password");
        when(employeeService.addEmployee(any(EmployeeDto.class)))
                .thenThrow(DataIntegrityViolationException.class);

        ResponseEntity<String> response = employeeController.register(employeeDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User already exists", response.getBody());
    }


}
