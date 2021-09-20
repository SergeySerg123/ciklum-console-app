package org.example.database.repositories;

import com.google.inject.Inject;
import org.example.database.interfaces.ConnectionFactory;
import org.example.database.interfaces.OrderRepository;
import org.example.models.Order;
import org.example.models.OrderDetail;
import org.example.models.viewmodels.OrderViewModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of
 * @see OrderRepository
 */
public class OrderRepositoryImpl implements OrderRepository {

    @Inject
    private ConnectionFactory databaseConnectionFactory;


    /**
     * Returns
     * @see OrderViewModel instaince by unique identifier from database
     * @param id represent the order id
     * @return OrderViewModel instance
     * @throws SQLException if sql query can't be fulfilled
     */
    public List<OrderViewModel> getOrderById(int id) throws SQLException {
        try(Connection connection = databaseConnectionFactory.createConnection();
            Statement statement = connection.createStatement()) {

            String sqlQuery = "SELECT order_id, products.name AS product_name, (quantity * products.price) as total_price, quantity AS product_quantity, orders.created_at AS order_time " +
                    "FROM order_items " +
                    "INNER JOIN products " +
                    "ON order_items.product_id = products.id " +
                    "INNER JOIN orders " +
                    "ON order_items.order_id = orders.id " +
                    "HAVING order_id = " + id;

            ResultSet resultSet = statement.executeQuery(sqlQuery);

            return getOrdersFromResultSet(resultSet);
        }
    }

    /**
     * Returns all
     * @see OrderViewModel instainces from database
     * @return list of OrderViewModel instances
     * @throws SQLException if sql query can't be fulfilled
     */
    public List<OrderViewModel> getAllOrders() throws SQLException {
        try(Connection connection = databaseConnectionFactory.createConnection();
            Statement statement = connection.createStatement()) {

            String sqlQuery = "SELECT order_id, products.name AS product_name, (quantity * products.price) as total_price, quantity AS product_quantity, orders.created_at AS order_time " +
                    "FROM order_items " +
                    "INNER JOIN products " +
                    "ON order_items.product_id = products.id " +
                    "INNER JOIN orders " +
                    "ON order_items.order_id = orders.id";

            ResultSet resultSet = statement.executeQuery(sqlQuery);

            return getOrdersFromResultSet(resultSet);
        }
    }

    /**
     * Creates new order in database
     * @param order represent
     * @see Order instance
     * @throws SQLException if sql query can't be fulfilled
     */
    public void createOrder(Order order) throws SQLException {
        int randomRangeStart = 1;
        int randomRangeEnd = 1000000;

        String sqlQuery = "INSERT INTO orders(user_id, status, created_at) " +
                "VALUES (RAND()*" + (randomRangeEnd - randomRangeStart) + randomRangeStart + ", '" + order.getStatus() + "', '" + order.getCreatedAt() + "');";

        try (Connection connection = databaseConnectionFactory.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            StringBuilder sqlOrderListQuerySb = new StringBuilder("INSERT INTO order_items(order_id, product_id, quantity) VALUES");

            preparedStatement.executeUpdate(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            resultSet.next();

            int createdOrderId =  resultSet.getInt(1);

            for (OrderDetail orderDetail: order.getOrderDetails()) {
                sqlOrderListQuerySb.append(" (").append(createdOrderId).append(", ").append(orderDetail.getProductId()).append(", ").append(orderDetail.getQuantity()).append("),");
            }
            sqlOrderListQuerySb.deleteCharAt(sqlOrderListQuerySb.length() - 1);

            preparedStatement.executeUpdate(sqlOrderListQuerySb.toString());
        }
    }

    private List<OrderViewModel> getOrdersFromResultSet(ResultSet resultSet) throws SQLException {
        List<OrderViewModel> orderViewModelList = new ArrayList<>();

        while (resultSet.next()) {
            orderViewModelList.add(getOrderViewModelFromResultSet(resultSet));
        }

        return orderViewModelList;
    }

    private OrderViewModel getOrderViewModelFromResultSet(ResultSet resultSet) throws SQLException {
        return new OrderViewModel(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getInt(3),
                resultSet.getInt(4),
                resultSet.getString(5)
        );
    }
}
