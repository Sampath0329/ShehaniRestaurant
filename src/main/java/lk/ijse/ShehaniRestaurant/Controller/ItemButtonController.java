package lk.ijse.ShehaniRestaurant.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ItemButtonController {

    public AnchorPane centerNode;
    public void addBearItemOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane dashBoardCenter = FXMLLoader.load(this.getClass().getResource("/View/AddBearItem_Form.fxml"));

        centerNode.getChildren().clear();
        centerNode.getChildren().add(dashBoardCenter);
    }

    public void addFoodItemOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane dashBoardCenter = FXMLLoader.load(this.getClass().getResource("/View/AddFoodItem_Form.fxml"));

        centerNode.getChildren().clear();
        centerNode.getChildren().add(dashBoardCenter);
    }
}
