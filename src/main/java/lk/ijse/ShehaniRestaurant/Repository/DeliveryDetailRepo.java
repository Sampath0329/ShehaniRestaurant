package lk.ijse.ShehaniRestaurant.Repository;

import javafx.scene.control.Alert;
import lk.ijse.ShehaniRestaurant.DataBaseConnection.DbConnection;
import lk.ijse.ShehaniRestaurant.Model.Delivery;

import java.sql.Connection;
import java.sql.SQLException;

public class DeliveryDetailRepo {

    public static boolean Save(Delivery delivery, String orderId) throws SQLException {
        Connection connection = DbConnection.getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isDeliverSaved = DeliveryRepo.Save(delivery);
            if (isDeliverSaved){
                boolean isVehicleUpdated = VehicleRepo.Update(delivery.getPlateNumber());
                if (isVehicleUpdated){
                    boolean isOrderUpdated = OrderRepo.Update(orderId,delivery.getDeliveryId());
                    if (isOrderUpdated){
                        connection.commit();
                        return true;
                    } new Alert(Alert.AlertType.ERROR, "order not save Repo").show();
                } new Alert(Alert.AlertType.ERROR, "vehicle not save Repo").show();
            } new Alert(Alert.AlertType.ERROR, "Delivery not save Repo").show();
            connection.rollback();
            return false;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
