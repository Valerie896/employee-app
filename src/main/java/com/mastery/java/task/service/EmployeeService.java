package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeDao employeeDao;

    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }

    public Employee getEmployee(long id) {
        return employeeDao.getEmployee(id);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Employee createEmployee(Employee employee) {

        Employee created = employeeDao.createEmployee(employee);
        log.info("Employee(id={}) created", created.getEmployeeId());

        return created;
    }

    @Transactional
    public Employee updateEmployee(Employee employee) {

        Employee updated = employeeDao.updateEmployee(employee);
        log.info("Employee(id={}) updated", updated.getEmployeeId());

        return updated;
    }

    @Transactional
    public void deleteEmployee(long id) {
        employeeDao.delete(id);
        log.info("Employee(id={}) deleted", id);
    }
}
