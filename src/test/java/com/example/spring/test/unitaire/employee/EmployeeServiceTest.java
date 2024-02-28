package com.example.spring.test.unitaire.employee;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    EmployeeService service;
    EmployeeRepository dao;

    @Before
    public void setUp(){
        this.dao = Mockito.mock(EmployeeRepository.class);
        this.service = new EmployeeService(dao);
    }

    @Test
    public void testFindAllEmployees() {
        Employee empOne = new Employee(1L,"John", "John");
        Employee empTwo = new Employee(2L,"Alex", "kolenchiski");
        Employee empThree = new Employee(3L,"Steve", "Waugh");

        List<Employee> list = new ArrayList<Employee>();
        list.add(empOne);
        list.add(empTwo);
        list.add(empThree);

        when(dao.findAll()).thenReturn(list);

        List<Employee> empList = service.findAllEmployees();

        assertEquals(3, empList.size());
        verify(dao, times(1)).findAll();
    }

    @Test
    public void testCreateOrSaveEmployee() {
        Employee employee = new Employee(4L,"Lokesh", "Gupta");

        when(dao.save(employee)).thenReturn(employee);

        Employee newEmployee = service.saveEmployee(employee);

        assertEquals("Gupta",newEmployee.getLastName());
        verify(dao, times(1)).save(employee);
    }
}
