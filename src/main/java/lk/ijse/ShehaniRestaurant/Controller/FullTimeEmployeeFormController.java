package lk.ijse.ShehaniRestaurant.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.ShehaniRestaurant.Model.FullTimeEmployee;
import lk.ijse.ShehaniRestaurant.Model.tm.FullTimeEmployeTm;
import lk.ijse.ShehaniRestaurant.Repository.FoodItemRepo;
import lk.ijse.ShehaniRestaurant.Repository.FullTimeEmployeeRepo;

import java.sql.SQLException;
import java.util.List;

public class FullTimeEmployeeFormController {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colHireDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private Label lbUserId;

    @FXML
    private TableView<FullTimeEmployeTm> tblFullTimeEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtHireDate;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtTel;

    public void initialize(){
        SetUserIdForLabel();
        setCellValueFactory();
        loadAllFullTimeEmployee();
    }

    private void loadAllFullTimeEmployee() {
        ObservableList<FullTimeEmployeTm> obList = FXCollections.observableArrayList();
        try {
            List<FullTimeEmployee> fullTimeEmployeeList = FullTimeEmployeeRepo.GetAll();
            for (FullTimeEmployee fullTimeEmployee : fullTimeEmployeeList){
                FullTimeEmployeTm tm = new FullTimeEmployeTm(
                        fullTimeEmployee.getFullTimeEmployeeId(),
                        fullTimeEmployee.getName(),
                        fullTimeEmployee.getAddress(),
                        fullTimeEmployee.getContact(),
                        fullTimeEmployee.getFixedSalary(),
                        fullTimeEmployee.getHireDate(),
                        fullTimeEmployee.getUserId(),
                        fullTimeEmployee.getActive()
                );
                obList.add(tm);
            }
            tblFullTimeEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("FullTimeEmployeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("FixedSalary"));
        colHireDate.setCellValueFactory(new PropertyValueFactory<>("HireDate"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("UserId"));
    }

    private void SetUserIdForLabel() {
        lbUserId.setText("Admin"); // login form eke idn log wenw nm ain krnn
//        lbUserId.setText(LoginFormController.SUserName);
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtHireDate.setText("");
        txtSalary.setText("");
        txtTel.setText("");
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            Boolean isDeleted = FullTimeEmployeeRepo.Delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Delete!").show();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee Not Delete!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            loadAllFullTimeEmployee();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        FullTimeEmployee fullTimeEmployee = new FullTimeEmployee(
                txtId.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtTel.getText(),
                txtSalary.getText(),
                txtHireDate.getText(),
                lbUserId.getText(),
                "Active"

        );
        try {
            Boolean isUpdated = FullTimeEmployeeRepo.Update(fullTimeEmployee);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated!").show();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee Not updated!").show();
                clearFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            loadAllFullTimeEmployee();
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        FullTimeEmployee fullTimeEmployee = new FullTimeEmployee(
                txtId.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtTel.getText(),
                txtSalary.getText(),
                txtHireDate.getText(),
                lbUserId.getText(),
                "Active"

        );
        try {
            Boolean isSaved = FullTimeEmployeeRepo.Save(fullTimeEmployee);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Employee saved!").show();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee Not saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            loadAllFullTimeEmployee();
        }
    }

    public void txtSearchOnActionId(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            FullTimeEmployee fullTimeEmployee = FullTimeEmployeeRepo.SearchById(id);
            if (fullTimeEmployee != null){
                txtId.setText(fullTimeEmployee.getFullTimeEmployeeId());
                txtName.setText(fullTimeEmployee.getName());
                txtAddress.setText(fullTimeEmployee.getAddress());
                txtTel.setText(fullTimeEmployee.getContact());
                txtSalary.setText(fullTimeEmployee.getFixedSalary());
                txtHireDate.setText(fullTimeEmployee.getHireDate());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
