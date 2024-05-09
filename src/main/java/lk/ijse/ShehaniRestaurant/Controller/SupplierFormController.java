package lk.ijse.ShehaniRestaurant.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.ShehaniRestaurant.Model.Supplier;
import lk.ijse.ShehaniRestaurant.Model.tm.SupplierTm;
import lk.ijse.ShehaniRestaurant.Repository.CustomerRepo;
import lk.ijse.ShehaniRestaurant.Repository.SupplierRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierFormController {

    @FXML
    public TextField txtSearch;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> coltel;

    @FXML
    private AnchorPane centerNode;

    @FXML
    private TableView<SupplierTm> tbSupplier;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    public void initialize() {
        setCellValueFactory();
        loadAllSupplier();
    }

    private void loadAllSupplier() {
        ObservableList<SupplierTm> supObList = FXCollections.observableArrayList();
        try {
            List<Supplier> supplierList = SupplierRepo.GetAll();
            for (Supplier supplier : supplierList){
                SupplierTm tm = new SupplierTm(
                        supplier.getId(),
                        supplier.getName(),
                        supplier.getTel()
                );
                supObList.add(tm);
            }
            tbSupplier.setItems(supObList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        coltel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    public void clearOnAction(ActionEvent actionEvent) {
        ClearFields();
    }

    private void ClearFields() {
        txtId.setText("");
        txtName.setText("");
        txtTel.setText("");
        txtSearch.setText("");
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        try {
            boolean isDeleted = SupplierRepo.Delete(txtId.getText());
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Deleted!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Supplier Not Deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            loadAllSupplier();
            ClearFields();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {

        Supplier supplier = new Supplier(
                txtId.getText(),
                txtName.getText(),
                txtTel.getText()
        );

        try {
            boolean isUpdated = SupplierRepo.Update(supplier);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Updated!");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            loadAllSupplier();
            ClearFields();
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {

        Supplier supplier = new Supplier(
                txtId.getText(),
                txtName.getText(),
                txtTel.getText()
        );

        try {
            boolean isSaved = SupplierRepo.Save(supplier);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Saved!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Supplier Not Saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    public void btnSearchOnActon(ActionEvent actionEvent) {
        String id = txtSearch.getText();

        try {
            Supplier supplier = SupplierRepo.searchById(id);
            if (supplier != null){
                txtId.setText(supplier.getId());
                txtName.setText(supplier.getName());
                txtTel.setText(supplier.getTel());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        btnSearchOnActon(actionEvent);
    }
}
