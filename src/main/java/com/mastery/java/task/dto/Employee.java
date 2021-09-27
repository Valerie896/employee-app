package com.mastery.java.task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Employee {

    private Long employeeId;

    @NotEmpty(message = "firstname must not be null or empty")
    private String firstname;

    @NotEmpty(message = "lastname must not be null or empty")
    private String lastname;

    @Min(value = 1, message = "departmentId can not be less than 1")
    @NotNull(message = "departmentId must not be null")
    private Long departmentId;

    @NotEmpty(message = "jobTitle must not be null or empty")
    private String jobTitle;

    @NotNull(message = "jobTitle must not be null")
    private Gender gender;

    @Past(message = "dateOfBirth must be in the past")
    private LocalDate dateOfBirth;

}
