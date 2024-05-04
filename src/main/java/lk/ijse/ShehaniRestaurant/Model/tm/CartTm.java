package lk.ijse.ShehaniRestaurant.Model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartTm {
    private String foodId;
    private String description;
    private int qty;
    private double unitPrice;
    private double total;
    private JFXButton btnRemove;
}
