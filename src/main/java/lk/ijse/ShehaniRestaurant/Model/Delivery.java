package lk.ijse.ShehaniRestaurant.Model;

import javafx.fxml.FXML;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Delivery {
    private String DeliveryId;
    private String Description;
    private String Address;
    private String PlateNumber;
}
