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
                    CREATE TABLE IF NOT EXISTS products (
                      id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(100) NOT NULL,
                      price INT NOT NULL,
                      status ENUM('out_of_stock', 'in_stock', 'running_low'),
                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP
                    );""";

            statement.execute(sqlCommand);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
