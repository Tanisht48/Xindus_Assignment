package com.assessment.WishlistManagement;

import com.assessment.WishlistManagement.Model.Employee;
import com.assessment.WishlistManagement.Model.dto.EmployeeDto;
import com.assessment.WishlistManagement.Repository.IEmployeeRepository;
import com.assessment.WishlistManagement.Service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


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

            when(iEmployeeRepository.save(any(Employee.class))).thenReturn(new Employee());
            EmployeeDto employeeDto = new EmployeeDto("John", "john@example.com", "password");

            Employee result = employeeService.addEmployee(employeeDto);

            assertNotNull(result);
            assertNull(result.getId());
            assertEquals("ROLE_USER", result.getRole());
            assertTrue(result.getEnable());
            assertNotNull(result.getVerificationCode());
            assertTrue(result.getIsAccountNonLocked());
        }

        @Mock
        private HttpSession httpSession;

        @Mock
        private RequestContextHolder requestContextHolder;





        @Test
        void testBCryptPasswordEncoder() {
            // Call the method
            BCryptPasswordEncoder encoder = employeeService.bCryptPasswordEncoder();

            // Verify that the returned object is not null
            assertNotNull(encoder);
        }

        @Test
        void testFindByEmail() {
            // Mocking the behavior of the repository to return an Employee
            Employee expectedEmployee = new Employee();
            when(iEmployeeRepository.findByEmail(anyString())).thenReturn(expectedEmployee);

            // Call the method
            Employee result = employeeService.findByEmail("test@example.com");

            // Verify that the correct method of the repository is called
            verify(iEmployeeRepository).findByEmail("test@example.com");

            // Verify that the returned object is the same as the expected one
            assertEquals(expectedEmployee, result);
        }

        @Test
        void testFindByVerificationCode_WhenEmployeeExists() {
            // Mocking the behavior of the repository to return an Employee
            Employee employee = new Employee();
            when(iEmployeeRepository.findByVerificationCode(anyString())).thenReturn(employee);

            // Call the method
            boolean result = employeeService.findByVerificationCode("verificationCode");

            // Verify that the correct method of the repository is called
            verify(iEmployeeRepository).findByVerificationCode("verificationCode");

            // Verify that the method returns true
            assertTrue(result);

            // Verify that the employee's verification code is set to null and enable is true
            assertTrue(employee.getEnable());
            assertNull(employee.getVerificationCode());

            // Verify that the save method of the repository is called
            verify(iEmployeeRepository).save(employee);
        }

        @Test
        void testFindByVerificationCode_WhenEmployeeDoesNotExist() {
            // Mocking the behavior of the repository to return null
            when(iEmployeeRepository.findByVerificationCode(anyString())).thenReturn(null);

            // Call the method
            boolean result = employeeService.findByVerificationCode("verificationCode");

            // Verify that the correct method of the repository is called
            verify(iEmployeeRepository).findByVerificationCode("verificationCode");

            // Verify that the method returns false
            assertFalse(result);
        }
    }


