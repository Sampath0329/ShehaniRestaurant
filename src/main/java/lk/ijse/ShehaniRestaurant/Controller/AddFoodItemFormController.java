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
import lk.ijse.ShehaniRestaurant.Model.FoodItem;
import lk.ijse.ShehaniRestaurant.Model.tm.FoodItemTm;
import lk.ijse.ShehaniRestaurant.Repository.FoodItemRepo;

import java.sql.SQLException;
import java.util.List;

public class AddFoodItemFormController {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private AnchorPane centerNode;

    @FXML
    private TableColumn<?, ?> colDes;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableView<FoodItemTm> tblFoodItem;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtname;

    public void initialize(){
        setCellValueFactory();
        loadAllFoodItem();
    }

    private void loadAllFoodItem() {
        ObservableList<FoodItemTm> obList = FXCollections.observableArrayList();
        try {
            List<FoodItem> footItemList = FoodItemRepo.GetAll();
            for (FoodItem foodItem : footItemList){
                FoodItemTm tm = new FoodItemTm(
                        foodItem.getId(),
                        foodItem.getName(),
                        foodItem.getDesc(),
                        foodItem.getPrice(),
                        foodItem.getQty(),
                        foodItem.getActive()
                );
                obList.add(tm);
            }
            tblFoodItem.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDes.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        txtId.setText("");
        txtname.setText("");
        txtDesc.setText("");
        txtPrice.setText("");
        txtQty.setText("");
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            Boolean isDeleted = FoodItemRepo.Delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Food Item Delete!").show();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Food Item Not Delete!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            loadAllFoodItem();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        FoodItem foodItem = new FoodItem(
                txtId.getText(),
                txtname.getText(),
                txtDesc.getText(),
                txtPrice.getText(),
                txtQty.getText(),
                "Active");
        try {
            Boolean isUpdated = FoodItemRepo.Update(foodItem);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Food Item updated!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Food Item Not updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            loadAllFoodItem();
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtname.getText();
        String desc = txtDesc.getText();
        String price = txtPrice.getText();
        String qty = txtQty.getText();

        FoodItem foodItem = new FoodItem(id,name,desc,price,qty,"Active");

        try {
            Boolean isSaved = FoodItemRepo.Save(foodItem);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Food Item saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            loadAllFoodItem();
        }
    }

    public void txtSearchOnActionId(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            FoodItem foodItem = FoodItemRepo.SearchById(id);
            if (foodItem != null){
                txtId.setText(foodItem.getId());
                txtname.setText(foodItem.getName());
                txtDesc.setText(foodItem.getDesc());
                txtPrice.setText(foodItem.getPrice());
                txtQty.setText(foodItem.getQty());

            } else{
                new Alert(Alert.AlertType.ERROR, "Food Item Not Found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
