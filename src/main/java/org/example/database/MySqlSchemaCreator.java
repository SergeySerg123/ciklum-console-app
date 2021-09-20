package org.example.database;

import com.google.inject.Inject;
import org.example.database.interfaces.ConnectionFactory;
import org.example.database.interfaces.SchemaCreator;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Implementation of
 * @see SchemaCreator
 */
public class MySqlSchemaCreator implements SchemaCreator {

    private final ConnectionFactory connectionFactory;

    /**
     * The constructor
     * @param connectionFactory
     */
    @Inject
    public MySqlSchemaCreator(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
     * Creates products table schema
     */
    @Override
    public void createProductsTable() {
        try(Connection connection = connectionFactory.createConnection();
            Statement statement = connection.createStatement()) {

            String sqlCommand = """
                    CREATE TABLE IF NOT EXISTS products (
                      id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(100) NOT NULL,
                      price INT NOT NULL,
                      status ENUM('out_of_stock', 'in_stock', 'running_low'),
                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;""";

            statement.execute(sqlCommand);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Creates orders table schema
     */
    @Override
    public void createOrdersTable() {
        try(Connection connection = connectionFactory.createConnection();
            Statement statement = connection.createStatement()) {

            String sqlCommand = """
                    CREATE TABLE IF NOT EXISTS orders (
                      id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                      user_id INT NOT NULL,
                      status VARCHAR(100) NOT NULL,
                      created_at VARCHAR(100) NOT NULL
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;""";

            statement.execute(sqlCommand);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Creates order_items table schema
     */
    @Override
    public void createOrderItemsTable() {
        try(Connection connection = connectionFactory.createConnection();
            Statement statement = connection.createStatement()) {
            String sqlCommand = """
                    CREATE TABLE IF NOT EXISTS order_items (
                      order_id INT NOT NULL,
                      product_id INT NOT NULL,
                      quantity INT NOT NULL,
                      FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                      FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;""";

            statement.execute(sqlCommand);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
