package lk.ijse.ShehaniRestaurant.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import lk.ijse.ShehaniRestaurant.Model.Vehicle;
import lk.ijse.ShehaniRestaurant.Model.VehicleColour;
import lk.ijse.ShehaniRestaurant.Model.tm.VehicleTm;
import lk.ijse.ShehaniRestaurant.Repository.VehicleRepo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class VehicleFormController {

    public Label lblColour;
    public DatePicker datepicker;
    @FXML
    private TableColumn<?, ?> ColLicenseDate;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXComboBox<VehicleColour> cBoxColour;

    @FXML
    private CheckBox chBoxAvailability;

    @FXML
    private TableColumn<?, ?> colAvailability;

    @FXML
    private TableColumn<?, ?> colColour;

    @FXML
    private TableColumn<?, ?> colLicenseDate;

    @FXML
    private TableColumn<?, ?> colPlateNumber;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableView<VehicleTm> tblVehicle;


    @FXML
    private TextField txtPlateNumber;

    @FXML
    private TextField txtSearchId;

    @FXML
    private TextField txtType;

    public void initialize(){
        LordVehicleColour();
        setUserNameForLabel();
        LordAllVehicle();

    }

    private void setUserNameForLabel() {
        colPlateNumber.setCellValueFactory(new PropertyValueFactory<>("PlateNumber"));
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        colColour.setCellValueFactory(new PropertyValueFactory<>("Color"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("Availability"));
        colLicenseDate.setCellValueFactory(new PropertyValueFactory<>("LicenseDate"));
    }

    private void LordAllVehicle() {
        ObservableList<VehicleTm> vehicleTmObservableList = FXCollections.observableArrayList();
        try {
            List<Vehicle> vehicleList =VehicleRepo.GetAll();
            for (Vehicle vehicle : vehicleList){
                VehicleTm tm = new VehicleTm(
                        vehicle.getPlateNumber(),
                        vehicle.getType(),
                        vehicle.getColor(),
                        vehicle.getAvailability(),
                        vehicle.getLicenseDate()
                );
                vehicleTmObservableList.add(tm);
            }
            tblVehicle.setItems(vehicleTmObservableList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void LordVehicleColour() {
        List<VehicleColour> colourList = VehicleRepo.GetColour();

        ObservableList<VehicleColour> vehicleColourObservableList = FXCollections.observableArrayList();

        vehicleColourObservableList.addAll(colourList);


        cBoxColour.setItems(vehicleColourObservableList);


    }

    @FXML
    public void BtnSearchOnAction(ActionEvent event) {
        TxtSearchIdOnAction(event);
    }

    @FXML
    public void ClearOnAction(ActionEvent event) {
        ClearFields();

    }

    private void ClearFields() {
        txtPlateNumber.setText("");
        txtType.setText("");
        txtSearchId.setText("");
        datepicker.setValue(null);
        cBoxColour.setValue(null);// Clear the combo box selection
        cBoxColour.setFocusColor(null);
        lblColour.setStyle(null);
        chBoxAvailability.setSelected(false);
    }



    @FXML
    public void DeleteOnAction(ActionEvent event) {
        String plateNumber = txtPlateNumber.getText();

        try {
            boolean isDeleted = VehicleRepo.Delete(plateNumber);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Vehicle Deleted!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Vehicle Not Deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            LordAllVehicle();
            ClearFields();
        }
    }

    @FXML
    public void SaveOnAction(ActionEvent event) {
        String plateNum = txtPlateNumber.getText();
        String type = txtType.getText();
        VehicleColour colour =  cBoxColour.getValue();
        String availability = "NO";
        if (chBoxAvailability.isSelected()){
            availability = "Yes";
        }
        LocalDate licenseDate = datepicker.getValue();

        Vehicle vehicle = new Vehicle(plateNum,type,colour,availability,licenseDate);

        try {
            boolean isSaved = VehicleRepo.Save(vehicle);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Vehicle Saved!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Vehicle Not Saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            LordAllVehicle();
            ClearFields();
        }


    }

    @FXML
    public void TxtSearchIdOnAction(ActionEvent event) {
        String plateNumber = txtSearchId.getText();

        try {
            Vehicle vehicle = VehicleRepo.SearchByPlateNumber(plateNumber);
            if (vehicle != null){
                txtPlateNumber.setText(vehicle.getPlateNumber());
                txtType.setText(vehicle.getType());
                cBoxColour.setValue(vehicle.getColor());
                chBoxAvailability.setSelected(vehicle.getAvailability().equals("Yes"));
                datepicker.setValue(vehicle.getLicenseDate());

            } else {
                new Alert(Alert.AlertType.ERROR, "Vehicle Not Found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    public void UpdateOnAction(ActionEvent event) {
        String plateNumber = txtPlateNumber.getText();
        String type = txtType.getText();
        VehicleColour colour = cBoxColour.getValue();
        String availability = "No";
        if (chBoxAvailability.isSelected()){
            availability = "Yes";
        }
        LocalDate lDate = datepicker.getValue();

        Vehicle vehicle = new Vehicle(plateNumber,type,colour,availability,lDate);

        try {
            boolean isUpdated = VehicleRepo.Update(vehicle);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Vehicle Updated!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Vehicle Not Updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            LordAllVehicle();
            ClearFields();
        }
    }

    public void ColourSetOnAction(ActionEvent actionEvent) {
        VehicleColour selectedColour = cBoxColour.getValue();
        if (selectedColour != null) {
            switch (selectedColour) {
                case RED:
                    cBoxColour.setFocusColor(Paint.valueOf("RED"));
                    lblColour.setStyle("-fx-background-radius: 100; -fx-background-color: RED;");
                    break;
                case BLUE:
                    cBoxColour.setFocusColor(Paint.valueOf("BLUE"));
                    lblColour.setStyle("-fx-background-radius: 100; -fx-background-color: BLUE;");
                    break;
                case GREEN:
                    cBoxColour.setFocusColor(Paint.valueOf("GREEN"));
                    lblColour.setStyle("-fx-background-radius: 100; -fx-background-color: GREEN;");
                    break;
                case WHITE:
                    cBoxColour.setFocusColor(Paint.valueOf("WHITE"));
                    lblColour.setStyle("-fx-background-radius: 100; -fx-background-color: WHITE;");
                    break;
            }
        }
    }




}
