## Employee App
### Setup Notes
Application uses Java 11 and Spring Boot 2.5.

PostgreSQL was chosen as a main data storage.
DB connection properties should be set through environment variables:
1. DB_URL
2. DB_USER
3. DB_PASSWORD

Be aware that Liquibase will automatically create 'employees' table(along with liquibase specific tables).

#### Adding employees to database
SQL script [insert_employees](src/main/resources/sql/insert_employees.sql) can be used to insert employees in database.

### REST API tests
[EmployeeControllerTest.java](src/test/java/com/mastery/java/task/rest/EmployeeControllerTest.java) contains all API tests.
**Note** that [application-test.properties](src/test/resources/application-test.properties) file contains test datasource properties that should be altered.

### REST API 
API can be accessed through "/swagger-ui" endpoint or **Insomnia** REST API client.
Import [insomnia rest api config](employee-insomnia-rest-api.json) to access API in a convenient way.