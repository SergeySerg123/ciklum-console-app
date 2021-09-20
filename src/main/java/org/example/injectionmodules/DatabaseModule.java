package org.example.injectionmodules;

import com.google.inject.AbstractModule;
import org.example.database.DatabaseConnectionFactory;
import org.example.database.MySqlSchemaCreator;
import org.example.database.configs.DatabaseConfigsProvider;
import org.example.database.configs.MySQLDatabaseConfigsProvider;
import org.example.database.interfaces.ConnectionFactory;
import org.example.database.interfaces.OrderRepository;
import org.example.database.interfaces.ProductRepository;
import org.example.database.interfaces.SchemaCreator;
import org.example.database.repositories.OrderRepositoryImpl;
import org.example.database.repositories.ProductRepositoryImpl;

/**
 * Module with database dependencies
 */
public class DatabaseModule extends AbstractModule {
    protected void configure() {
        // provider
        bind(DatabaseConfigsProvider.class).to(MySQLDatabaseConfigsProvider.class);

        // factory
        bind(ConnectionFactory.class).to(DatabaseConnectionFactory.class);

        // repositories
        bind(OrderRepository.class).to(OrderRepositoryImpl.class);
        bind(ProductRepository.class).to(ProductRepositoryImpl.class);

        // schema
        bind(SchemaCreator.class).to(MySqlSchemaCreator.class);
    }
}
