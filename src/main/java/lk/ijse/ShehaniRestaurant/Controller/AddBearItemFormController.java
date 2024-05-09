package lk.ijse.ShehaniRestaurant.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.ShehaniRestaurant.Model.Bear;
import lk.ijse.ShehaniRestaurant.Model.FoodItem;
import lk.ijse.ShehaniRestaurant.Model.tm.BearTm;
import lk.ijse.ShehaniRestaurant.Repository.BearRepo;
import lk.ijse.ShehaniRestaurant.Repository.FoodItemRepo;

import java.sql.SQLException;
import java.util.List;

public class AddBearItemFormController {
    @FXML
    public JFXButton btnSearch;
    @FXML
    public Label lblBearId;
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
    private TableColumn<?, ?> colAlcCon;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableView<BearTm> tblBear;

    @FXML
    private TextField txtAlcoholContent;

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
        loadAllBear();
        getCurrentBearId();

    }

    private void getCurrentBearId() {
        try {
            int currentId = BearRepo.getCurrentId();
            String nextOrderId = generateNextFoodId(currentId);

            lblBearId.setText(nextOrderId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private String generateNextFoodId(int currentId) {
        if(currentId != 0) {

            int idNum = currentId;
            return "B" + ++idNum;
        }
        return "B1";
    }

    private void loadAllBear() {
        ObservableList<BearTm> obList = FXCollections.observableArrayList();
        try {
            List<Bear> bearList = BearRepo.GetAll();
            for (Bear bear : bearList){
                BearTm tm = new BearTm(
                        bear.getId(),
                        bear.getName(),
                        bear.getPrice(),
                        bear.getAvailable(),
                        bear.getAlcoholContent(),
                        bear.getQty()
                );
                obList.add(tm);
            }
            tblBear.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colAlcCon.setCellValueFactory(new PropertyValueFactory<>("alcoholContent"));

    }

    @FXML
    void clearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
//        txtId.setText("");
        getCurrentBearId();
        txtname.setText("");
        txtAlcoholContent.setText("");
        txtPrice.setText("");
        txtQty.setText("");
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
//        String id = txtId.getText();
        String id = lblBearId.getText();

        try {
            Boolean isDeleted = BearRepo.Delete(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Bear Delete!").show();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Bear Not Delete!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            loadAllBear();
        }
    }

    @FXML
    void saveOnAction(ActionEvent event) {
//        String id = txtId.getText();
        String id = lblBearId.getText();
        String name = txtname.getText();
        String price = txtPrice.getText();
        String qty = txtQty.getText();
        String alcoholContent = txtAlcoholContent.getText();

        Bear bear = new Bear(id,name,price,"Yes",alcoholContent,qty);

        try {
            Boolean isSaved = BearRepo.Save(bear);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Bear Item saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            loadAllBear();
        }
    }

    @FXML
    void txtSearchOnActionId(ActionEvent event) {
        btnSearchOnAction(event);
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        Bear bear = new Bear(
//                txtId.getText(),
                lblBearId.getText(),
                txtname.getText(),
                txtPrice.getText(),
                "Yes",
                txtAlcoholContent.getText(),
                txtQty.getText());
        try {
            Boolean isUpdated = BearRepo.Update(bear);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Beat updated!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Bear Not updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            loadAllBear();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id = txtSearch.getText();

        try {
            Bear bear = BearRepo.SearchById(id);
            if (bear != null){
//                txtId.setText(bear.getId());
                lblBearId.setText(bear.getId());
                txtname.setText(bear.getName());
                txtPrice.setText(bear.getPrice());
                txtQty.setText(bear.getQty());
                txtAlcoholContent.setText(bear.getAlcoholContent());

            } else{
                new Alert(Alert.AlertType.ERROR, "Bear Item Not Found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
