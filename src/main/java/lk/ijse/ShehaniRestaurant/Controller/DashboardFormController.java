package lk.ijse.ShehaniRestaurant.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashboardFormController {


    public AnchorPane centerNode;

    public void initialize()  {

        try {
            loardDashBord();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void loardDashBord() throws IOException {
        AnchorPane dashBoardCenter = FXMLLoader.load(this.getClass().getResource("/View/DashBord_Center_Form.fxml"));

        centerNode.getChildren().clear();
        centerNode.getChildren().add(dashBoardCenter);
    }

    public void dashboardOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane dashBoardCenter = FXMLLoader.load(this.getClass().getResource("/View/DashBord_Center_Form.fxml"));

        centerNode.getChildren().clear();
        centerNode.getChildren().add(dashBoardCenter);
    }
    public void foodItemOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane btnItem = FXMLLoader.load(this.getClass().getResource("/View/ItemButtone_Form.fxml"));

        centerNode.getChildren().clear();
        centerNode.getChildren().add(btnItem);
    }

    public void userOnAction(ActionEvent actionEvent) throws IOException {
        LoginFormController.SUserName = "Admin"; // login form eke idn log wenkot mek ain krnn
        if (LoginFormController.SUserName.equals("Admin")){
            AnchorPane userForm = FXMLLoader.load(this.getClass().getResource("/View/User_Form.fxml"));

            centerNode.getChildren().clear();
            centerNode.getChildren().add(userForm);
        } else {
            new Alert(Alert.AlertType.ERROR, "sorry! This Option Access Admin Only").show();
        }

    }

    public void employeeOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane vehicleForm = FXMLLoader.load(this.getClass().getResource("/View/AddEmployeeButton_Form.fxml"));

        centerNode.getChildren().clear();
        centerNode.getChildren().add(vehicleForm);
    }

    public void customerOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane customerPane = FXMLLoader.load(this.getClass().getResource("/View/Customer_Form.fxml"));

        centerNode.getChildren().clear();
        centerNode.getChildren().add(customerPane);
    }

    public void orderOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane customerPane = FXMLLoader.load(this.getClass().getResource("/View/placeorder_form.fxml"));

        centerNode.getChildren().clear();
        centerNode.getChildren().add(customerPane);
    }

    public void vehicleOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane vehicleForm = FXMLLoader.load(this.getClass().getResource("/View/Vehicle_Form.fxml"));

        centerNode.getChildren().clear();
        centerNode.getChildren().add(vehicleForm);
    }

    public void deliveryOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane vehicleForm = FXMLLoader.load(this.getClass().getResource("/View/Delivery_Form.fxml"));

        centerNode.getChildren().clear();
        centerNode.getChildren().add(vehicleForm);
    }

    public void reportOnAction(ActionEvent actionEvent) {
    }

    public void settingOnAction(ActionEvent actionEvent) {
    }

    public void logoutOnAction(ActionEvent actionEvent) {

    }

    public void ingredientOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane vehicleForm = FXMLLoader.load(this.getClass().getResource("/View/Ingredient_Form.fxml"));

        centerNode.getChildren().clear();
        centerNode.getChildren().add(vehicleForm);
    }

    public void supplierOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane vehicleForm = FXMLLoader.load(this.getClass().getResource("/View/Supplier_Form.fxml"));

        centerNode.getChildren().clear();
        centerNode.getChildren().add(vehicleForm);
    }
}
