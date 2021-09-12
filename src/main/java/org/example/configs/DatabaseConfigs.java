package org.example.configs;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfigs {
    private Connection databaseConnection;

    public DatabaseConfigs() throws SQLException, IOException, ClassNotFoundException {
        this.buildConnection();
    }

    public Connection getDatabaseConnection() {
        return databaseConnection;
    }

    private void buildConnection() throws IOException, SQLException, ClassNotFoundException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("local.properties"));

        String databaseName = properties.getProperty("database_name");
        String userName = properties.getProperty("user_name");
        String password = properties.getProperty("password");

        databaseConnection = DriverManager.getConnection("jdbc:mysql://localhost/" + databaseName, userName, password);
    }
}
