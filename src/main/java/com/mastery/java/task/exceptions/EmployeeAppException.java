package com.mastery.java.task.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class EmployeeAppException extends RuntimeException {

    private HttpStatus status;
    private String logMessage;
    private String responseMessage;

    public EmployeeAppException(int status, String logMessage, String responseMessage, Throwable cause) {
        super(logMessage, cause);
        setFields(status, logMessage, responseMessage);
    }

    public EmployeeAppException(int status, String logMessage, String responseMessage) {
        super(logMessage);
        setFields(status, logMessage, responseMessage);
    }

    private void setFields(int status, String logMessage, String responseMessage) {
        this.status = HttpStatus.resolve(status);
        if (this.status == null) {//prevent use of invalid http statuses
            throw new RuntimeException("HttpStatus couldn't be resolved");
        }
        this.logMessage = logMessage;
        this.responseMessage = responseMessage;
    }
}
