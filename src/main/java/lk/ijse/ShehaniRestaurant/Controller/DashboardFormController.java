package lk.ijse.ShehaniRestaurant.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;

public class DashboardFormController {

public AnchorPane rootNode;
    public AnchorPane centerNode;
    public Label lbltimenow;

    public void initialize()  {

        try {
            loardDashBord();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }


    private void loardDashBord() throws IOException {
        AnchorPane dashBoardCenter = FXMLLoader.load(this.getClass().getResource("/View/DashBordCenter_Form.fxml"));

        centerNode.getChildren().clear();
        centerNode.getChildren().add(dashBoardCenter);
    }

    public void dashboardOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane dashBoardCenter = FXMLLoader.load(this.getClass().getResource("/View/DashBordCenter_Form.fxml"));

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

//    public void deliveryOnAction(ActionEvent actionEvent) throws IOException {
//        AnchorPane vehicleForm = FXMLLoader.load(this.getClass().getResource("/View/Delivery_Form.fxml"));
//
//        centerNode.getChildren().clear();
//        centerNode.getChildren().add(vehicleForm);
//    }


    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/Login_Form.fxml"));
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Login Form");
        stage.centerOnScreen();
        stage.show();

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
