# Welcome to ciklum-console-app!

### Stack
- Java 16
- JDBC
- MySQL
- JUnit 5
- Guice 5
- Maven

# How to get start

### Prepare database
- open MySQL workbench (or analog) and create a new database (schema)
- updates credentials and database name in the `local.properties`

### Running application
- `git clone https://github.com/SergeySerg123/ciklum-console-app`
- `cd ciklum-console-app`
- `mvn compile`
- for running application with unit tests, please use `mvn verify` command
- for running only unit tests, please use `mvn test` command
- for running application without unit tests, please use `mvn exec:java` command

### IMPORTANT!
Database should be created before starting the application, otherwise you will receive
`java.sql.SQLSyntaxErrorException`

Happy checking and have a nice day =)