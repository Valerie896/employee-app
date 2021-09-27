package com.mastery.java.task.rest;

import com.mastery.java.task.Application;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@TestPropertySource(value = "/application-test.properties")
@AutoConfigureMockMvc
@DisplayName("employee-api-test")
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("getAll positive testcase")
    @SqlGroup(value = {
            @Sql(scripts = "/employees/create-three-employees.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "/employees/delete-all-employees.sql", executionPhase = AFTER_TEST_METHOD),
    })
    public void getAllPositiveTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)))

                //validating json object structure
                .andExpect(jsonPath("$[0].employeeId", is(not(nullValue()))))
                .andExpect(jsonPath("$[0].firstname", is(not(nullValue()))))
                .andExpect(jsonPath("$[0].lastname", is(not(nullValue()))))
                .andExpect(jsonPath("$[0].gender", is(not(nullValue()))))
                .andExpect(jsonPath("$[0].departmentId", is(not(nullValue()))))
                .andExpect(jsonPath("$[0].jobTitle", is(not(nullValue()))))
                .andExpect(jsonPath("$[0].dateOfBirth", is(not(nullValue()))));
    }

    @Test
    @DisplayName("get one positive testcase")
    @SqlGroup(value = {
            @Sql(scripts = "/employees/insert-one-employee.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "/employees/delete-all-employees.sql", executionPhase = AFTER_TEST_METHOD),
    })
    public void getOnePositiveTest() throws Exception {

        long employeeId = 10;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/" + employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId", is(10)))
                .andExpect(jsonPath("$.firstname", is("Nina")))
                .andExpect(jsonPath("$.lastname", is("Zacharova")))
                .andExpect(jsonPath("$.gender", is("FEMALE")))
                .andExpect(jsonPath("$.departmentId", is(3)))
                .andExpect(jsonPath("$.jobTitle", is("HR")))
                .andExpect(jsonPath("$.dateOfBirth", is("1985-08-18")));
    }

    @Test
    @DisplayName("get one negative testcase - 'id' is not found")
    public void getOneNegativeTest() throws Exception {

        long incorrectIdentifier = 758447L;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/" + incorrectIdentifier))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("create employee positive testcase")
    @Sql(scripts = "/employees/delete-all-employees.sql", executionPhase = AFTER_TEST_METHOD)
    public void createEmployeePositiveTest() throws Exception {

        String body = " {" +
                "    \"firstname\": \"Grisha\"," +
                "    \"lastname\": \"Pavlovich\"," +
                "    \"departmentId\": 1," +
                "    \"jobTitle\": \"Software engineer\"," +
                "    \"gender\": \"MALE\"," +
                "    \"dateOfBirth\": \"1998-08-21\"" +
                "  }";

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId", is(not(nullValue()))))

                .andExpect(jsonPath("$.firstname", is("Grisha")))
                .andExpect(jsonPath("$.lastname", is("Pavlovich")))
                .andExpect(jsonPath("$.gender", is("MALE")))
                .andExpect(jsonPath("$.departmentId", is(1)))
                .andExpect(jsonPath("$.jobTitle", is("Software engineer")))
                .andExpect(jsonPath("$.dateOfBirth", is("1998-08-21")));
    }

    @Test
    @DisplayName("create employee negative testcase - empty 'firstname'")
    public void createEmployeeNegativeTest() throws Exception {

        String emptyFirstname = "";
        String body = " {" +
                "    \"firstname\": \"" + emptyFirstname + "\"," +
                "    \"lastname\": \"Pavlovich\"," +
                "    \"departmentId\": 1," +
                "    \"jobTitle\": \"Software engineer\"," +
                "    \"gender\": \"MALE\"," +
                "    \"dateOfBirth\": \"1998-08-21\"" +
                "  }";

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("update employee positive testcase")
    @SqlGroup(value = {
            @Sql(scripts = "/employees/insert-one-employee.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "/employees/delete-all-employees.sql", executionPhase = AFTER_TEST_METHOD),
    })
    public void updateEmployeeTest() throws Exception {

        String body = " {" +
                "    \"employeeId\": 10," +
                "    \"firstname\": \"Grisha\"," +
                "    \"lastname\": \"Pavlovich\"," +
                "    \"departmentId\": 1," +
                "    \"jobTitle\": \"Software engineer\"," +
                "    \"gender\": \"MALE\"," +
                "    \"dateOfBirth\": \"1998-08-21\"" +
                "  }";

        mockMvc.perform(put("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId", is(10)))

                .andExpect(jsonPath("$.firstname", is("Grisha")))
                .andExpect(jsonPath("$.lastname", is("Pavlovich")))
                .andExpect(jsonPath("$.gender", is("MALE")))
                .andExpect(jsonPath("$.departmentId", is(1)))
                .andExpect(jsonPath("$.jobTitle", is("Software engineer")))
                .andExpect(jsonPath("$.dateOfBirth", is("1998-08-21")));
    }

    @Test
    @DisplayName("update employee negative testcase - 'id' is not found")
    public void updateEmployeeNegativeTest() throws Exception {

        long incorrectIdentifier = 89895L;
        String body = " {" +
                "    \"employeeId\": " + incorrectIdentifier + "," +
                "    \"firstname\": \"Grisha\"," +
                "    \"lastname\": \"Pavlovich\"," +
                "    \"departmentId\": 1," +
                "    \"jobTitle\": \"Software engineer\"," +
                "    \"gender\": \"MALE\"," +
                "    \"dateOfBirth\": \"1998-08-21\"" +
                "  }";

        mockMvc.perform(put("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("delete employee positive testcase")
    @SqlGroup(value = {
            @Sql(scripts = "/employees/insert-one-employee.sql", executionPhase = BEFORE_TEST_METHOD),
            @Sql(scripts = "/employees/delete-all-employees.sql", executionPhase = AFTER_TEST_METHOD),
    })
    public void deleteEmployeeTest() throws Exception {

        long incorrectIdentifier = 10L;

        mockMvc.perform(delete("/api/employees/" + incorrectIdentifier))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("delete employee negative testcase - 'id' is not found")
    public void deleteEmployeeNegativeTest() throws Exception {

        long incorrectIdentifier = 758447L;

        mockMvc.perform(delete("/api/employees/" + incorrectIdentifier))
                .andExpect(status().isNotFound());

    }
}
