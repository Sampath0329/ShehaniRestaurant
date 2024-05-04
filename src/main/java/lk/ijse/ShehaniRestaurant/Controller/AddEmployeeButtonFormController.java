package lk.ijse.ShehaniRestaurant.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.security.cert.PolicyNode;

public class AddEmployeeButtonFormController {

    public AnchorPane centerNode;

    public void partTimeEmpOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane vehicleForm = FXMLLoader.load(this.getClass().getResource("/View/PartTimeEmployee_Form.fxml"));

        centerNode.getChildren().clear();
        centerNode.getChildren().add(vehicleForm);
    }

    public void fullTimeEmpOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane vehicleForm = FXMLLoader.load(this.getClass().getResource("/View/FullTimeEmployee_Form.fxml"));

        centerNode.getChildren().clear();
        centerNode.getChildren().add(vehicleForm);
    }
}
