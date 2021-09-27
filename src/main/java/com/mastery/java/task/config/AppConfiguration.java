package com.mastery.java.task.config;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.service.EmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
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

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }
}
