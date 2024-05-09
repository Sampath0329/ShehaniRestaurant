package lk.ijse.ShehaniRestaurant.Model.tm;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeliveryTm {
    private String DeliveryId;
    private String Description;
    private String Address;
    private String PlateNumber;
}
