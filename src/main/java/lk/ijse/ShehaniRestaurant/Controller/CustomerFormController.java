package lk.ijse.ShehaniRestaurant.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.ShehaniRestaurant.Model.Customer;
import lk.ijse.ShehaniRestaurant.Model.tm.CustomerTm;
import lk.ijse.ShehaniRestaurant.Repository.CustomerRepo;

import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {

//    @FXML
//    private JFXButton btnClear;
//
//    @FXML
//    private JFXButton btnDelete;
//
//    @FXML
//    private JFXButton btnSave;
//
//    @FXML
//    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private Label lblUserName;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    public void initialize(){
        setCellValueFactory();
        setUserNameForLable();
        loadAllCustomers();


    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("NIC"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("username"));
    }

    private void loadAllCustomers() {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
        try {
            List<Customer> customerList = CustomerRepo.getAll();
            for (Customer customer:customerList){
                CustomerTm tm = new CustomerTm(
                        customer.getId(),
                        customer.getName(),
                        customer.getNIC(),
                        customer.getAddress(),
                        customer.getTel(),
                        customer.getUsername()

                );
                obList.add(tm);
            }
            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setUserNameForLable() {
//        lblUserName.setText(LoginFormController.SUserName);
        lblUserName.setText("Admin");

    }


    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            Boolean isDeleted = CustomerRepo.Delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "customer Deleted!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "customer NOt Deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }finally {
            loadAllCustomers();
            setCellValueFactory();
            clearFields();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String NIC = txtNIC.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();
        String userName = lblUserName.getText();


        Customer customer = new Customer(id,name,NIC,address,tel,userName);

        try {
            Boolean isUpdated = CustomerRepo.updated(customer);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }finally {
            loadAllCustomers();
            setCellValueFactory();
            clearFields();
        }

    }

    public void saveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String NIC = txtNIC.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();
        String userName = lblUserName.getText();

        Customer customer = new Customer(id,name,NIC,address,tel,userName);

        try {
            Boolean isSaved = CustomerRepo.save(customer);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                clearFields();
            }
        } catch (SQLException e) {

            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }finally {
            loadAllCustomers();
            setCellValueFactory();
            clearFields();
        }


    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtNIC.setText("");
        txtAddress.setText("");
        txtTel.setText("");
    }

    public void txtSearchOnActionId(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            Customer customer = CustomerRepo.searchById(id);
            if (customer != null){
                txtId.setText(customer.getId());
                txtName.setText(customer.getName());
                txtAddress.setText(customer.getAddress());
                txtNIC.setText(customer.getNIC());
                txtTel.setText(customer.getTel());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
}
