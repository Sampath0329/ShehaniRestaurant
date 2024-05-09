package lk.ijse.ShehaniRestaurant.Repository;

import lk.ijse.ShehaniRestaurant.DataBaseConnection.DbConnection;
import lk.ijse.ShehaniRestaurant.Model.Order;

import java.sql.*;

public class OrderRepo {
    public static String getCurrentId() throws SQLException {
//        String sql = "SELECT OrderId FROM Orders ORDER BY OrderId DESC LIMIT 1";
        String sql = "SELECT MAX(CAST(SUBSTRING(OrderId, 2) AS UNSIGNED)) AS HighestOrderId FROM Orders;";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String orderId = resultSet.getString(1);

            return orderId;
        }
        return null;
    }

    public static boolean Save(Order order) throws SQLException {
        String sql = "INSERT INTO Orders VALUES(?, ?, ?, ?, ?,?)";
        PreparedStatement pstm = DbConnection.getConnection()
                .prepareStatement(sql);

        pstm.setString(1, order.getOrderId());
        pstm.setString(2, order.getCustomerId());
        pstm.setDate(3, order.getOrderDate());
        pstm.setTime(4, order.getTimePlace());
        pstm.setObject(5,order.getPaymentAmount());
        pstm.setObject(6,null);

        return pstm.executeUpdate() > 0;

    }

    public static boolean Update(String orderId, String deliveryId) throws SQLException {
        String sql = "UPDATE Orders SET DeliveryId = ? WHERE OrderId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);
        pstm.setObject(1,deliveryId);
        pstm.setObject(2,orderId);

        return pstm.executeUpdate() > 0;
    }

    public static int GetOrderCount() throws SQLException {
        String sql = "SELECT * FROM Orders";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        int orderCount = 0;
        while (resultSet.next()){
           ++orderCount;
        }
        return orderCount;
    }

    public static double GetTotalIncome() throws SQLException {
        String sql = "SELECT SUM(PaymentAmount) FROM Orders";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            return Double.parseDouble(resultSet.getString(1));
        }
        return 0.0;
    }
}
