package lk.ijse.ShehaniRestaurant.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.ShehaniRestaurant.Model.Delivery;
import lk.ijse.ShehaniRestaurant.Model.tm.DeliveryTm;
import lk.ijse.ShehaniRestaurant.Repository.DeliveryDetailRepo;
import lk.ijse.ShehaniRestaurant.Repository.DeliveryRepo;
import lk.ijse.ShehaniRestaurant.Repository.VehicleRepo;

import java.sql.SQLException;
import java.util.List;

public class DeliveryFormController {

    @FXML
    private Label lblDeliveryId;

    @FXML
    private JFXComboBox<String> CBoxVehicleId;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnSave;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colDesc;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colVehicleId;

    @FXML
    private AnchorPane centerNode;

    @FXML
    private Label lblOrderId;

    @FXML
    private TableView<DeliveryTm> tblDelivery;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtId;

    public void initialize(){
        setOrderId();
        getPlateNumbers();
        getCurrentDeliveryId();
        LoadAllDeliver();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("DeliveryId"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("PlateNumber"));
    }

    private void LoadAllDeliver() {
        ObservableList<DeliveryTm> obList = FXCollections.observableArrayList();
        try {
            List<Delivery> deliveryList = DeliveryRepo.GetAll();
            for (Delivery delivery : deliveryList){
                DeliveryTm tm = new DeliveryTm(
                        delivery.getDeliveryId(),
                        delivery.getDescription(),
                        delivery.getAddress(),
                        delivery.getPlateNumber()
                );
                obList.add(tm);
            }
            tblDelivery.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void getCurrentDeliveryId() {
        try {
            String currentId = DeliveryRepo.getCurrentId();
            String nextDeliveryId = generateNextDeliveryId(currentId);
            lblDeliveryId.setText(nextDeliveryId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private String generateNextDeliveryId(String currentId) {
        if (currentId != null){
            int idNum = Integer.parseInt(currentId);
            return "D" + ++idNum;
        }
        return "D1";
    }

    private void getPlateNumbers() {
        ObservableList<String> plateList = FXCollections.observableArrayList();
        try {
            List<String> plateNum = VehicleRepo.GetPlateNum();
            plateList.addAll(plateNum);
            CBoxVehicleId.setItems(plateList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void setOrderId() {
        if (PlaceorderFormController.DeliveryOrderId != null){
            lblOrderId.setText(PlaceorderFormController.DeliveryOrderId);
        }
    }

    public void clearOnAction(ActionEvent actionEvent) {
        ClearFields();
    }

    private void ClearFields() {
        txtAddress.setText("");
        txtDesc.setText("");
    }


    public void saveOnAction(ActionEvent actionEvent) {
        String orderId = lblOrderId.getText();
        String deliveryId = lblDeliveryId.getText();
        String desc = txtDesc.getText();
        String address = txtAddress.getText();
        String vehicleId = CBoxVehicleId.getValue();

        Delivery delivery = new Delivery(deliveryId,desc,address, vehicleId);

        try {
            boolean isSaved = DeliveryDetailRepo.Save(delivery,orderId);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Delivery Saved!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Delivery Not Saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }
}
