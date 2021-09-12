package org.example.repositories;

import org.example.core.DatabaseConnectionFactory;
import org.example.models.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ProductRepository {
    private final DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();

    public void createProduct(Product product) {
        try (Connection connection = databaseConnectionFactory.createConnection()) {
            String sqlQuery = "INSERT INTO products (name, price) VALUES (" + product.getName() + ", " + product.getPrice() + ")";
            Statement statement = connection.createStatement();
            statement.execute(sqlQuery);

            statement.close();
            System.out.println("Product with name " + product.getName() + "was successfully created.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // TODO
    public List<Product> getAllProducts() {
        try (Connection connection = databaseConnectionFactory.createConnection()) {
            String sqlQuery = "SELECT * FROM products";
            Statement statement = connection.createStatement();
            statement.execute(sqlQuery);
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
