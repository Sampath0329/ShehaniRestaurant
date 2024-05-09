package lk.ijse.ShehaniRestaurant.Controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.ShehaniRestaurant.Repository.CustomerRepo;
import lk.ijse.ShehaniRestaurant.Repository.OrderRepo;

import java.sql.SQLException;

public class DashBoardCenterFormController {

    @FXML
    private AnchorPane centerNode;

    @FXML
    private BarChart<?, ?> chatIncom;

    @FXML
    private BarChart<?, ?> chatOrders;

    @FXML
    private Label lblCustomerCOunt;

    @FXML
    private Label lblIncomeCount;

    @FXML
    private Label lblOrderCount;

    public void initialize(){
        LoadCustomerCount();
        LoadOrderCount();
        LoadIncome();
    }

    private void LoadIncome() {
        try {
            double income = OrderRepo.GetTotalIncome();
            lblIncomeCount.setText(String.valueOf(income));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void LoadOrderCount() {
        try {
            int orderCount = OrderRepo.GetOrderCount();
            lblOrderCount.setText(String.valueOf(orderCount));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void LoadCustomerCount() {
        try {
            int customerCount = CustomerRepo.GetCustomerCount();
            lblCustomerCOunt.setText(String.valueOf(customerCount));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
