package org.example.database.repositories;

import com.google.inject.Inject;
import org.example.database.interfaces.ConnectionFactory;
import org.example.database.interfaces.ProductRepository;
import org.example.models.viewmodels.OrderedProductViewModel;
import org.example.models.Product;
import org.example.models.viewmodels.ProductViewModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of
 * @see ProductRepository
 */
public class ProductRepositoryImpl implements ProductRepository {

    private final ConnectionFactory databaseConnectionFactory;

    /**
     * The constructor
     * @param databaseConnectionFactory
     */
    @Inject
    public ProductRepositoryImpl(ConnectionFactory databaseConnectionFactory) {
        this.databaseConnectionFactory = databaseConnectionFactory;
    }

    /**
     * Returns
     * @see ProductViewModel instaince by unique identifier from database
     * @param id represent the order id
     * @return list of ProductViewModel instances
     * @throws SQLException if sql query can't be fulfilled
     */
    @Override
    public ProductViewModel getProductById(int id) throws SQLException {
        ProductViewModel product = null;

        try (Connection connection = databaseConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {
            String sqlQuery = "SELECT * FROM products WHERE id = " + id;

            ResultSet resultSet = statement.executeQuery(sqlQuery);

            if (resultSet.next()) {
                product = createProductFromResultSet(resultSet);
            }
        }
        return product;
    }

    /**
     * Returns all
     * @see OrderedProductViewModel instainces from database
     * @return list of OrderedProductViewModel instances
     * @throws SQLException if sql query can't be fulfilled
     */
    @Override
    public List<OrderedProductViewModel> getAllOrderedProducts() throws SQLException {
        List<OrderedProductViewModel> productList = null;
        try (Connection connection = databaseConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {
            String sqlQuery = """
                        SELECT products.id as id, products.name as name, SUM(order_items.quantity) as quantity FROM products
                              INNER JOIN order_items
                              ON products.id = order_items.product_id
                              GROUP BY id
                              ORDER BY quantity DESC
                    """;
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            //retrieve products from result
            productList = retrieveOrderedProductsFromResultSet(resultSet);
        }
        return productList;
    }

    /**
     * Returns all
     * @see ProductViewModel instainces from database
     * @return list of ProductViewModel instances
     * @throws SQLException if sql query can't be fulfilled
     */
    @Override
    public List<ProductViewModel> getAllProducts() throws SQLException {
        List<ProductViewModel> productList = null;
        try (Connection connection = databaseConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {
            String sqlQuery = "SELECT * FROM products";
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            //retrieve products from result
            productList = retrieveProductsFromResultSet(resultSet);
        }
        return productList;
    }

    /**
     * Returns all
     * @see OrderedProductViewModel instainces from database by unique identifier
     * @return list of OrderedProductViewModel instances
     * @throws SQLException if sql query can't be fulfilled
     */
    @Override
    public List<OrderedProductViewModel> getProductIdsByOrderId(int orderId) throws SQLException {
        try(Connection connection = databaseConnectionFactory.createConnection();
            Statement statement = connection.createStatement()) {

            String sqlQuery = """
                    SELECT product_id as id, products.name as name, quantity FROM order_items
                    	INNER JOIN products
                        ON product_id = products.id
                        WHERE order_id =
                    """ + orderId;

            ResultSet resultSet = statement.executeQuery(sqlQuery);

            return retrieveOrderedProductsFromResultSet(resultSet);
        }
    }

    /**
     * Creates the new product in database
     * @param product represent
     * @see Product instance
     * @throws SQLException if sql query can't be fulfilled
     */
    @Override
    public void createProduct(Product product) throws SQLException {
        try (Connection connection = databaseConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {
            String sqlQuery = "INSERT INTO products(name, price, status) " +
                    "VALUES ('" + product.getName() + "', " + product.getPrice() + ", '" + product.getProductStatus() + "');";

            statement.execute(sqlQuery);
        }
    }

    /**
     * Updates product quantity in the order list by order id
     * @param orderId represent order unique identifier
     * @param newQuantity represent new quantity value
     * @throws SQLException if sql query can't be fulfilled
     */
    @Override
    public void updateProductQuantityInOrderList(int orderId, int newQuantity) throws SQLException {
        try (Connection connection = databaseConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {
            String sqlQuery = "UPDATE order_items SET quantity = " + newQuantity + " WHERE order_id = " + orderId;

            statement.execute(sqlQuery);
        }
    }

    /**
     * Deletes product by id include related orders
     * @param id represent product unique identifier
     * @throws SQLException if sql query can't be fulfilled
     */
    @Override
    public void deleteProductById(int id) throws SQLException {
        try (Connection connection = databaseConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {
            String sqlQuery = "DELETE FROM products WHERE id = " + id;

            statement.execute(sqlQuery);
        }
    }

    /**
     * Deletes all products from database include related orders
     * @throws SQLException if sql query can't be fulfilled
     */
    @Override
    public void deleteAllProducts() throws SQLException {
        try (Connection connection = databaseConnectionFactory.createConnection();
             Statement statement = connection.createStatement()) {
            String sqlQuery = "DELETE FROM products";

            statement.execute(sqlQuery);
        }
    }

    private List<ProductViewModel> retrieveProductsFromResultSet(ResultSet resultSet) throws SQLException {
        List<ProductViewModel> resultList = new ArrayList<>();
        while (resultSet.next()) {
            resultList.add(createProductFromResultSet(resultSet));
        }
        return resultList;
    }

    private List<OrderedProductViewModel> retrieveOrderedProductsFromResultSet(ResultSet resultSet) throws SQLException {
        List<OrderedProductViewModel> resultList = new ArrayList<>();
        while (resultSet.next()) {
            resultList.add(createOrderedProductFromResultSet(resultSet));
        }
        return resultList;
    }

    private ProductViewModel createProductFromResultSet(ResultSet resultSet) throws SQLException {
        return new ProductViewModel(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("status")
        );
    }

    private OrderedProductViewModel createOrderedProductFromResultSet(ResultSet resultSet) throws SQLException {
        return new OrderedProductViewModel(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("quantity")
        );
    }
}
