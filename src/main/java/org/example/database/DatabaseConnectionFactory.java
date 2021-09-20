package org.example.database;

import com.google.inject.Inject;
import org.example.database.configs.DatabaseConfigsProvider;
import org.example.database.interfaces.ConnectionFactory;

import java.sql.Connection;

/**
 * Implementation of
 * @see ConnectionFactory
 */
public class DatabaseConnectionFactory implements ConnectionFactory {

    private final DatabaseConfigsProvider databaseConfigsProvider;

    /**
     * The constructor
     * @param databaseConfigsProvider
     */
    @Inject
    public DatabaseConnectionFactory(DatabaseConfigsProvider databaseConfigsProvider) {
        this.databaseConfigsProvider = databaseConfigsProvider;
    }

    /**
     * Creates
     * @see Connection instances
     * @return new Connection instance
     */
    @Override
    public Connection createConnection() {
        return databaseConfigsProvider.getDatabaseConnection();
    }
}
