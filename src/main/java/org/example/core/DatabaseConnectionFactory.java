package org.example.core;

import org.example.configs.DatabaseConfigs;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class DatabaseConnectionFactory implements Closeable {
    private Connection databaseConnection;

    public Connection createConnection() {
        DatabaseConfigs databaseConfigs = null;
        try {
            databaseConfigs = new DatabaseConfigs();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        databaseConnection = Objects.requireNonNull(databaseConfigs).getDatabaseConnection();
        return databaseConnection;
    }

    @Override
    public void close() {
        try {
            databaseConnection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
