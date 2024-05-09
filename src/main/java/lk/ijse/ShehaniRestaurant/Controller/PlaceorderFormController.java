package lk.ijse.ShehaniRestaurant.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.ShehaniRestaurant.Model.*;
import lk.ijse.ShehaniRestaurant.Model.tm.CartTm;
import lk.ijse.ShehaniRestaurant.Repository.*;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.valueOf;

public class PlaceorderFormController {

    @FXML
    public TextField txtCusTel;

    @FXML
    public AnchorPane centerNode;

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXComboBox<String> cmbFoodItemId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Label lbltime;

    @FXML
    private Label lblCustomerId;

    @FXML
    private TableView<CartTm> tblOrderCart;

    @FXML
    private TextField txtQty;

    private ObservableList<CartTm> obList = FXCollections.observableArrayList();

    public void initialize() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            updateTime();
        }));
        timeline.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely
        timeline.play();
        updateDate();
        getFoodItemCodes();
        getCurrentOrderId();
        setCellValueFactory();
        LoadCustomerAllTel();
    }

    private void LoadCustomerAllTel() {
        try {
            List<String> cusTel = CustomerRepo.GetCustomerTel();
            String[] possibleNames = cusTel.toArray(new String[0]);

            TextFields.bindAutoCompletion(txtCusTel, possibleNames);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("foodId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private void getCurrentOrderId() {
        try {
            String currentId = OrderRepo.getCurrentId();

            String nextOrderId = generateNextOrderId(currentId);
            lblOrderId.setText(nextOrderId);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private String generateNextOrderId(String currentId) {
        if(currentId != null) {
//            String[] split = currentId.split("O");  //" ", "2"
//            int idNum = Integer.parseInt(split[1]);
            int idNum = Integer.parseInt(currentId);
            return "O" + ++idNum;
        }
        return "O1";
    }

    private void getFoodItemCodes() {
        ObservableList<String> obList1 = FXCollections.observableArrayList();
        try {
            List<String> codeList = FoodItemRepo.GetIds();
            List<String> bearList = BearRepo.getIds();

            for (String ids : codeList) {
                obList1.add(ids);
            }
            for (String ids : bearList) {
                obList1.add(ids);
            }

            cmbFoodItemId.setItems(obList1);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }



    private void updateDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(valueOf(now));

    }

    private void updateTime() {
        // Get current time and format it
        LocalTime currentTime = LocalTime.now();
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        // Update the label text
        lbltime.setText(formattedTime);
    }
    @FXML
    public void btnAddToCartOnAction(ActionEvent event) {
        String foodId = cmbFoodItemId.getValue();
        int qty = Integer.parseInt(txtQty.getText());
        if ('F' == foodId.charAt(0)){
            try {
                boolean isAvailabilityQty = FoodItemRepo.isAvailable(foodId,qty);
                if (isAvailabilityQty){
//                    new Alert(Alert.AlertType.CONFIRMATION, "Can Add to Cart").show();
                    addToCart(foodId,qty);
                } else {
                    new Alert(Alert.AlertType.CONFIRMATION, "Can not Add to Cart").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            try {
                boolean isAvailabilityQty = BearRepo.isAvailable(foodId,qty);
                if (isAvailabilityQty){
//                    new Alert(Alert.AlertType.CONFIRMATION, "Can Add to Cart").show();
                    addToCart(foodId,qty);
                } else {
                    new Alert(Alert.AlertType.CONFIRMATION, "Can not Add to Cart").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }


    }

    private void addToCart(String foodId, int qty) {
        String description = lblDescription.getText();
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double total = qty * unitPrice;
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove?", yes, no).showAndWait();

            if (result.isPresent() && result.get() == yes) {
                int selectedIndex = tblOrderCart.getSelectionModel().getSelectedIndex();
                // methna index ek gann than error ekk enw
                obList.remove(selectedIndex);
                System.out.println("Delete operation confirmed.");
            } else {
                // User clicked on "no" or closed the dialog
                System.out.println("Delete operation canceled.");
            }
        });

        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            if(foodId.equals(colItemCode.getCellData(i))) {

                CartTm tm = obList.get(i);
                qty += tm.getQty();
                total = qty * unitPrice;

                tm.setQty(qty);
                tm.setTotal(total);

                tblOrderCart.refresh();

                calculateNetTotal();
                return;
            }
        }
        CartTm tm = new CartTm(foodId, description, qty, unitPrice, total, btnRemove);
        obList.add(tm);

        tblOrderCart.setItems(obList);
        calculateNetTotal();
        txtQty.setText("");

    }

    private void calculateNetTotal() {
        double netTotal = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(valueOf(netTotal));
    }

    @FXML
    public void btnPlaceOrderOnAction(ActionEvent event) {

        String orderId = lblOrderId.getText();
        String customerId = lblCustomerId.getText();

        Date date = Date.valueOf(LocalDate.now());
        Time time = Time.valueOf(LocalTime.now());
        String total = lblNetTotal.getText();

        Order order = new Order(orderId,customerId,date,time,total);

        List<OrderDetail> orderDetailList = new ArrayList<>();

        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            CartTm tm = obList.get(i);

            OrderDetail orderDetail = new OrderDetail(
                    orderId,
                    tm.getFoodId(),
                    tm.getQty(),
                    tm.getUnitPrice()
            );

            orderDetailList.add(orderDetail);
        }

        PlaceOrder po = new PlaceOrder(order,orderDetailList);

        try {
            Boolean isPlaced = PlaceOrderRepo.PlaceOrder(po);
            if (isPlaced){
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
                isCanLoadDeliveryPage = true;
            } else {
                new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    public void cmbFoodItemIdOnAction(ActionEvent event) {
        String id = cmbFoodItemId.getValue();

        Boolean idsF_B = checkIdsFoodOrBear(id);

        if (idsF_B){
            try {
                FoodItem foodItem = FoodItemRepo.SearchById(id);

                lblDescription.setText(foodItem.getDesc());
                lblUnitPrice.setText(foodItem.getPrice());
                lblQtyOnHand.setText(foodItem.getQty());


            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            try {
                Bear bear = BearRepo.SearchById(id);
                lblDescription.setText(bear.getName()+" Bear");
                lblUnitPrice.setText(bear.getPrice());
                lblQtyOnHand.setText(bear.getQty());

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }



    }

    private Boolean checkIdsFoodOrBear(String id) {
        char _1st = id.charAt(0);

        if (_1st == 'F'){
            return true;
        } else {
            return false;
        }

    }

    @FXML
    public void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);
    }


    public void txtCusTelOnAction(ActionEvent actionEvent) {
        String tel = txtCusTel.getText();

        try {
            Customer customer = CustomerRepo.GetCustomer(tel);
            lblCustomerId.setText(customer.getId());
            lblCustomerName.setText(customer.getName());

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public static String DeliveryOrderId = null;
//    public static String DeliveryOrderId = "O3";  // delete this

    public boolean isCanLoadDeliveryPage = false;
//    public boolean isCanLoadDeliveryPage = true; // delete this
    public void AddToDeliveryOnAction(ActionEvent actionEvent) {
        DeliveryOrderId = lblOrderId.getText();
        if (isCanLoadDeliveryPage){
            AnchorPane deliveryForm = null;
            try {
                deliveryForm = FXMLLoader.load(this.getClass().getResource("/View/Delivery_Form.fxml"));
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            centerNode.getChildren().clear();
            centerNode.getChildren().add(deliveryForm);
        } else {
            new Alert(Alert.AlertType.ERROR, "Can Not Load Delivery Form").show();
        }
    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        customerTel = txtCusTel.getText();
        AnchorPane customerPane = null;
        try {
            customerPane = FXMLLoader.load(this.getClass().getResource("/View/Customer_Form.fxml"));
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        centerNode.getChildren().clear();
        centerNode.getChildren().add(customerPane);
    }
    public static String customerTel = null;

    public void btnClearOnAction(ActionEvent actionEvent) {
        ClearFields();
    }

    private void ClearFields() {
        getCurrentOrderId();
        lblCustomerName.setText("");
        lblCustomerId.setText("");
        txtCusTel.setText("");
//        methn error ekk enw
       cmbFoodItemId.setValue("");

        lblDescription.setText("");
        lblUnitPrice.setText("");
        lblQtyOnHand.setText("");
        tblOrderCart.refresh();
        isCanLoadDeliveryPage = false;

    }
}
