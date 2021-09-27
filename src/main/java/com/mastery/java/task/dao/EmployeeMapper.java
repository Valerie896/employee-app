package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployeeMapper implements RowMapper<Employee> {

    private final DateTimeFormatter dateOfBirthFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {

        Employee employee = new Employee();

        employee.setEmployeeId(rs.getLong("employee_id"));
        employee.setFirstname(rs.getString("first_name"));
        employee.setLastname(rs.getString("last_name"));
        employee.setDepartmentId(rs.getLong("department_id"));
        employee.setJobTitle(rs.getString("job_title"));
        employee.setGender(Gender.valueOf(rs.getString("gender")));
        employee.setDateOfBirth(LocalDate.parse(rs.getString("date_of_birth"), dateOfBirthFormat));

        return employee;
    }
}
