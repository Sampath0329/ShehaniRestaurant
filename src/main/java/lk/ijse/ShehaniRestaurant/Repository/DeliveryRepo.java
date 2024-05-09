package lk.ijse.ShehaniRestaurant.Repository;

import lk.ijse.ShehaniRestaurant.DataBaseConnection.DbConnection;
import lk.ijse.ShehaniRestaurant.Model.Bear;
import lk.ijse.ShehaniRestaurant.Model.Delivery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryRepo {

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT MAX(CAST(SUBSTRING(DeliveryId, 2) AS UNSIGNED)) AS HighestOrderId FROM Delivery";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {

            return resultSet.getString(1);
        }
        return null;
    }

    public static boolean Save(Delivery delivery) throws SQLException {
        String sql = "INSERT INTO Delivery VALUES (?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,delivery.getDeliveryId());
        pstm.setObject(2,delivery.getDescription());
        pstm.setObject(3,delivery.getAddress());
        pstm.setObject(4,delivery.getPlateNumber());

        return pstm.executeUpdate() > 0;
    }

    public static List<Delivery> GetAll() throws SQLException {
        String sql = "SELECT * FROM Delivery";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        List<Delivery> deliveryList = new ArrayList<>();
        while (resultSet.next()){
            String deliveryId = resultSet.getString(1);
            String desc = resultSet.getString(2);
            String address = resultSet.getString(3);
            String vehicleId = resultSet.getString(4);

            Delivery delivery = new Delivery(deliveryId,desc,address,vehicleId);

            deliveryList.add(delivery);
        }
        return deliveryList;
    }
}
