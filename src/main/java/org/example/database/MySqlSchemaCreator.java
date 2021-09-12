package org.example.database;

import org.example.core.DatabaseConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlSchemaCreator implements SchemaCreator {
    private DatabaseConnectionFactory connectionFactory = new DatabaseConnectionFactory();

    @Override
    public void createProductTable() {
        try(Connection connection = connectionFactory.createConnection()) {
            Statement statement = connection.createStatement();

            String sqlCommand = """
                    CREATE TABLE developers (
                      id   INT          NOT NULL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL
                      salary VARCHAR(100) NOT NULL
                    );""";

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
