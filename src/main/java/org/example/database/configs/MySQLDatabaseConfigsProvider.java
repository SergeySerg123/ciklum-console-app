package org.example.database.configs;

import com.google.inject.Inject;
import org.example.core.properties.PropertiesProvider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Implementation of
 * @see DatabaseConfigsProvider
 */
public class MySQLDatabaseConfigsProvider implements DatabaseConfigsProvider {

    private final PropertiesProvider propertiesProvider;

    /**
     * The constructor
     * @param propertiesProvider
     */
    @Inject
    public MySQLDatabaseConfigsProvider(PropertiesProvider propertiesProvider) {
        this.propertiesProvider = propertiesProvider;
    }

    /**
     * Provides database connection
     * @return Connection instance
     */
    @Override
    public Connection getDatabaseConnection() {
        Connection databaseConnection = null;

        try {
            databaseConnection = this.buildConnection();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        return databaseConnection;
    }

    private Connection buildConnection() throws IOException, SQLException {
        Properties properties = propertiesProvider.provideProperties();

        String databaseName = properties.getProperty("database_name");
        String userName = properties.getProperty("user_name");
        String password = properties.getProperty("password");

        return DriverManager.getConnection("jdbc:mysql://localhost/" + databaseName, userName, password);
    }
}
