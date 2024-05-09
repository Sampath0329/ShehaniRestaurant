package lk.ijse.ShehaniRestaurant.Repository;

import javafx.scene.control.Alert;
import lk.ijse.ShehaniRestaurant.DataBaseConnection.DbConnection;
import lk.ijse.ShehaniRestaurant.Model.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderRepo {

    public static Boolean PlaceOrder(PlaceOrder po) throws SQLException {
        Connection connection = DbConnection.getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isSaved = OrderRepo.Save(po.getOrder());
            if (isSaved){
                boolean isOrderDetailSaved = OrderDetailRepo.Save(po.getOrderDetailList());
                if (isOrderDetailSaved){
                    boolean isQtyUpdated = FoodItemRepo.CheckFoodItemOrBear(po.getOrderDetailList());
                    if (isQtyUpdated){
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            connection.rollback();
            return false;

        } finally {
            connection.setAutoCommit(true);
        }

    }
}
