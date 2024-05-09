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
import lk.ijse.ShehaniRestaurant.Model.Ingredient;
import lk.ijse.ShehaniRestaurant.Model.IngredientDetail;
import lk.ijse.ShehaniRestaurant.Model.Supplier;
import lk.ijse.ShehaniRestaurant.Model.tm.IngredientTm;
import lk.ijse.ShehaniRestaurant.Repository.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class IngredientFormController {

    public Label lblSupplierName;
    public Label lblIngredientId;
    public TextField txtUnitPrice;
    public DatePicker datePickerEXP;
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
    private AnchorPane centerNode;

    @FXML
    private JFXComboBox<String> cmbSupplierId;

    @FXML
    private TableColumn<?, ?> colEXPDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colUnit;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<IngredientTm> tbIngredient;

    @FXML
    private TextField txtEXP;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSearchId;

    @FXML
    private TextField txtUnit;



    public void initialize(){
        GetSupplierIds();
        getCurrentIngredientId();
        LoadAllIngredient();
        setCellValueFactory();

    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("ingredientId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEXPDate.setCellValueFactory(new PropertyValueFactory<>("EXP_Date"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
    }

    private void LoadAllIngredient() {
        ObservableList<IngredientTm> ingredientObservableList = FXCollections.observableArrayList();
        try {
            List<Ingredient> ingredientList = IngredientRepo.GetAll();

            for (Ingredient ingredient : ingredientList){
                String supplierId = IngredientDetailRepo.GetSupplierId(ingredient.getIngredientId());
                IngredientTm tm = new IngredientTm(
                        ingredient.getIngredientId(),
                        ingredient.getName(),
                        ingredient.getEXP_Date(),
                        ingredient.getQty(),
                        ingredient.getUnit(),
                        ingredient.getPrice(),
                        supplierId
                );
                ingredientObservableList.add(tm);
            }
            tbIngredient.setItems(ingredientObservableList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void getCurrentIngredientId() {
        try {
            String currentId = IngredientRepo.GetCurrentId();

            String nextOrderId = generateNextIngredientId(currentId);
            lblIngredientId.setText(nextOrderId);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private String generateNextIngredientId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("I");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "I" + ++idNum;
        }
        return "I1";
    }


    private void GetSupplierIds() {
        ObservableList<String> SupplierObList = FXCollections.observableArrayList();
        try {
            List<String> idList = SupplierRepo.getIds();

            for(String id : idList) {
                SupplierObList.add(id);
            }

            cmbSupplierId.setItems(SupplierObList);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    public void btnSearchOnAction(ActionEvent event) {
        String id = txtSearchId.getText();

        try {
            Ingredient ingredient = IngredientRepo.SearchByIngredientId(id);
            if (ingredient != null){
                IngredientDetail ingredientDetail = IngredientDetailRepo.GetSupplier(id);

                if (ingredientDetail != null){
                    lblIngredientId.setText(ingredient.getIngredientId());
                    txtName.setText(ingredient.getName());
                    datePickerEXP.setValue(ingredient.getEXP_Date());
                    txtQty.setText(String.valueOf(ingredient.getQty()));
                    txtUnit.setText(ingredient.getUnit());
                    txtUnitPrice.setText(String.valueOf(ingredient.getPrice()));
                    cmbSupplierId.setValue(ingredientDetail.getSupplierId());

                } else {
                    new Alert(Alert.AlertType.ERROR, "Supplier Not Found!").show();
                }

            } else {
                new Alert(Alert.AlertType.ERROR, "Ingredient Not Found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    public void clearOnAction(ActionEvent event) {
        ClearFields();
        getCurrentIngredientId();
    }

    private void ClearFields() {
        lblSupplierName.setText(null);
        cmbSupplierId.setValue(null);
        cmbSupplierId.setFocusColor(null);
        txtName.setText(null);
        datePickerEXP.setValue(null);
        txtUnit.setText(null);
        txtUnitPrice.setText(null);
        txtQty.setText(null);
        txtSearchId.setText(null);
    }

    @FXML
    public void cmbSupplierOnAction(ActionEvent event) {
        String id = cmbSupplierId.getValue();
        try {
            Supplier supplier = SupplierRepo.searchById(id);

            lblSupplierName.setText(supplier.getName());

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    public void deleteOnAction(ActionEvent event) {
        String ingredientId = lblIngredientId.getText();

        try {
            boolean isDeleted = IngredientRepo.Delete(ingredientId);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Ingredient Deleted!").show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Ingredient Not Deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            LoadAllIngredient();
            ClearFields();
            getCurrentIngredientId();
        }
    }

    @FXML
    public void saveOnAction(ActionEvent event) {
        String supplierId = cmbSupplierId.getValue();
        String ingredientId = lblIngredientId.getText();
        String name = txtName.getText();
        LocalDate EXP_Date = datePickerEXP.getValue();
        int qty = Integer.parseInt(txtQty.getText());
        String unit = txtUnit.getText();
        double price = Double.parseDouble(txtUnitPrice.getText());
        LocalDate date = LocalDate.now();

        Ingredient ingredient = new Ingredient(ingredientId,name,EXP_Date,qty,unit,price);
        IngredientDetail ingredientDetail = new IngredientDetail(supplierId,ingredientId,date);


        try {
            boolean isPlaced = PlaceIngredientRepo.PlaceIngredient(ingredient,ingredientDetail);
            if (isPlaced){
                new Alert(Alert.AlertType.CONFIRMATION, "Ingredient Saved!").show();
                getCurrentIngredientId();
            } else {
                new Alert(Alert.AlertType.WARNING, "Ingredient Saved Unsuccessfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            LoadAllIngredient();
            getCurrentIngredientId();
            ClearFields();
        }
    }

    @FXML
    public void txtSearchOnAction(ActionEvent event) {
        btnSearchOnAction(event);
    }

    @FXML
    public void updateOnAction(ActionEvent event) {
        String supplierId = cmbSupplierId.getValue();
        String ingredientId = lblIngredientId.getText();
        String name = txtName.getText();
        LocalDate EXP_Date = datePickerEXP.getValue();
        int qty = Integer.parseInt(txtQty.getText());
        String unit = txtUnit.getText();
        double price = Double.parseDouble(txtUnitPrice.getText());
        LocalDate date = LocalDate.now();

        Ingredient ingredient = new Ingredient(ingredientId,name,EXP_Date,qty,unit,price);
        IngredientDetail ingredientDetail = new IngredientDetail(supplierId,ingredientId,date);

        try {
            boolean isUpdate = PlaceIngredientRepo.UpdateIngredient(ingredient,ingredientDetail);
            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION, "Ingredient Update!").show();

            } else {
                new Alert(Alert.AlertType.WARNING, "Ingredient Update Unsuccessfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            LoadAllIngredient();
            getCurrentIngredientId();
            ClearFields();
        }
    }

}
