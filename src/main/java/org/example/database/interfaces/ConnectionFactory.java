package org.example.database.interfaces;

import java.sql.Connection;

/**
 * Creates
 * @see Connection instances
 */
public interface ConnectionFactory {

    /**
     * Creates
     * @see Connection instances
     * @return new Connection instance
     */
    Connection createConnection();
}
