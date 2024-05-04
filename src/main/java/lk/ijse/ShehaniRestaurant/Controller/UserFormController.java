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
import lk.ijse.ShehaniRestaurant.Model.Customer;
import lk.ijse.ShehaniRestaurant.Model.User;
import lk.ijse.ShehaniRestaurant.Model.tm.UserTm;
import lk.ijse.ShehaniRestaurant.Repository.UserRepo;

import java.sql.SQLException;
import java.util.List;

public class UserFormController {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableView<UserTm> tblUser;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPw;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtPw;

    @FXML
    private TextField txtname;

    public void initialize(){
        setCellValueFactory();
        loadAllUser();
    }

    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPw.setCellValueFactory(new PropertyValueFactory<>("pw"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("NIC"));
    }


    private void loadAllUser() {

        ObservableList<UserTm> obList = FXCollections.observableArrayList();
        try {
            List<User> userList = UserRepo.getAll();
            for (User user : userList){
                UserTm tm = new UserTm(
                        user.getId(),
                        user.getName(),
                        user.getPw(),
                        user.getNIC(),
                        user.getActive()
                );
                obList.add(tm);

            }
            tblUser.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            Boolean isDeleted = UserRepo.Delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "User Deleted!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "User Not Deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }finally {
            loadAllUser();
            clearFields();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        User user = new User(
                txtId.getText(),
                txtname.getText(),
                txtPw.getText(),
                txtNIC.getText(),
                "Active");
        try {
            Boolean isUpdated = UserRepo.update(user);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "User updated!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "User Not updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            loadAllUser();
        }

    }

    public void saveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtname.getText();
        String pw = txtPw.getText();
        String NIC = txtNIC.getText();
        String active = "Active";

        User user = new User(id,name,pw,NIC,active);

        try {
            Boolean isSaved = UserRepo.Saved(user);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "User saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }finally {
            loadAllUser();
        }


    }
    private void clearFields() {
        txtId.setText("");
        txtPw.setText("");
        txtNIC.setText("");
        txtname.setText("");
    }

    public void txtSearchOnActionId(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            User user = UserRepo.SearchById(id);
            if (user != null){
                txtId.setText(user.getId());
                txtname.setText(user.getName());
                txtPw.setText(user.getPw());
                txtNIC.setText(user.getNIC());

            } else{
                new Alert(Alert.AlertType.ERROR, "User Nno Found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
