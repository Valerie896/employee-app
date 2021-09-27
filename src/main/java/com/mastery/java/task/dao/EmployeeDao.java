package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.EmployeeNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;

import static java.sql.Types.BIGINT;

public class EmployeeDao {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Employee> getAllEmployees() {
        return jdbcTemplate.query("select * from employees", new EmployeeMapper());
    }

    public Employee getEmployee(long id) {

        Object[] args = {id};
        int[] types = {BIGINT};

        String query = "select * from employees where employee_id=?";

        try {
            return jdbcTemplate.queryForObject(query, args, types, new EmployeeMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new EmployeeNotFoundException(id, e);
        }
    }

    public Employee createEmployee(Employee employee) {

        String insert = "" +
                "insert into employees(first_name, last_name, department_id, job_title, gender, date_of_birth)  " +
                "values(?,?,?,?,?,?) " +
                "returning employee_id";

        PreparedStatementCreator preparedStatementCreator = connection -> connection.prepareStatement(insert);

        PreparedStatementCallback<Long> preparedStatementCallback = preparedStatement -> {

            preparedStatement.setString(1, employee.getFirstname());
            preparedStatement.setString(2, employee.getLastname());
            preparedStatement.setLong(3, employee.getDepartmentId());
            preparedStatement.setString(4, employee.getJobTitle());
            preparedStatement.setString(5, employee.getGender().name());
            preparedStatement.setDate(6, Date.valueOf(employee.getDateOfBirth()));

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return resultSet.getLong("employee_id");
        };

        Long id = jdbcTemplate.execute(preparedStatementCreator, preparedStatementCallback);
        employee.setEmployeeId(id);

        return employee;
    }

    public Employee updateEmployee(Employee employee) {

        String updateSql = "" +
                "update employees " +
                "set first_name=?,last_name=?,department_id=?,job_title=?,gender=?,date_of_birth=? " +
                "where employee_id = ?";

        int updatedRows = jdbcTemplate.update(updateSql,
                employee.getFirstname(),
                employee.getLastname(),
                employee.getDepartmentId(),
                employee.getJobTitle(),
                employee.getGender().name(),
                employee.getDateOfBirth(),
                employee.getEmployeeId());

        if (updatedRows == 0) {
            throw new EmployeeNotFoundException(employee.getEmployeeId());
        }

        return employee;
    }

    public void delete(long id) {

        String query = "delete from employees where employee_id=?";

        int updateRows = jdbcTemplate.update(query, id);
        if (updateRows == 0) {
            throw new EmployeeNotFoundException(id);
        }
    }
}
