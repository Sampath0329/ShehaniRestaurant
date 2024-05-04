package lk.ijse.ShehaniRestaurant.Repository;

import lk.ijse.ShehaniRestaurant.DataBaseConnection.DbConnection;
import lk.ijse.ShehaniRestaurant.Model.Order;

import java.sql.*;

public class OrderRepo {
    public static String getCurrentId() throws SQLException {
        String sql = "SELECT OrderId FROM Orders ORDER BY OrderId DESC LIMIT 1";

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
}
