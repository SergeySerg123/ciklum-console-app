package org.example.database.configs;

import java.sql.Connection;

/**
 * Provides the
 * @see Connection instances
 */
public interface DatabaseConfigsProvider {

    /**
     * Provides database connection
     * @return Connection instance
     */
    Connection getDatabaseConnection();
}
