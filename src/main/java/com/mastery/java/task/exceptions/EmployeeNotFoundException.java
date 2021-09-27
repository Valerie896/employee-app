package com.mastery.java.task.exceptions;


public class EmployeeNotFoundException extends EmployeeAppException {

    public EmployeeNotFoundException(long employeeId, Throwable cause) {
        super(
                404,
                "Employee with id=" + employeeId + " was not found",
                "Employee with id=" + employeeId + " was not found",
                cause
        );
    }

    public EmployeeNotFoundException(Long employeeId) {
        super(
                404,
                "Employee with id=" + employeeId + " was not found",
                "Employee with id=" + employeeId + " was not found"
        );
    }
}
