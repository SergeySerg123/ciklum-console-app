package org.example.repositories;

import org.example.core.DatabaseConnectionFactory;
import org.example.models.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private final DatabaseConnectionFactory databaseConnectionFactory = new DatabaseConnectionFactory();

    public void createProduct(Product product) {
        try (Connection connection = databaseConnectionFactory.createConnection()) {
            String sqlQuery = "INSERT INTO products(name, price, status) " +
                    "VALUES ('" + product.getName() + "', " + product.getPrice() + ", '" + product.getProductStatus() + "');";
            Statement statement = connection.createStatement();
            statement.execute(sqlQuery);

            statement.close();
            System.out.println("Product with name '" + product.getName() + "' was successfully created.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        try (Connection connection = databaseConnectionFactory.createConnection()) {
            String sqlQuery = "SELECT * FROM products";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            //retrieve products from result
            List<Product> productList = retrieveProductsFromResultSet(resultSet);

            statement.close();

            return productList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private List<Product> retrieveProductsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Product> resultList = new ArrayList<>();

        while (resultSet.next()) {
            resultList.add(
                    new Product(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt()
                            ));
        }

        return resultList;
    }
}
