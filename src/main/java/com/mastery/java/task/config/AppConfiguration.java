package com.mastery.java.task.config;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.service.EmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class AppConfiguration {

    private final JdbcTemplate jdbcTemplate;

    public AppConfiguration(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    public EmployeeDao getEmployeeDao() {
        return new EmployeeDao(jdbcTemplate);
    }

    @Bean
    public EmployeeService getEmployeeService(){
        return new EmployeeService(getEmployeeDao());
    }
}
