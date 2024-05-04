package lk.ijse.ShehaniRestaurant.Model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OderDetail {
    private String OrderId;
    private String FoodId;
    private String BearId;
    private String Quantity;
}
