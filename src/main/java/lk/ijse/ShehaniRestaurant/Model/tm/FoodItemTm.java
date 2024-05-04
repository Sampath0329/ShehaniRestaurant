package lk.ijse.ShehaniRestaurant.Model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

public class FoodItemTm {
    String id;
    String name;
    String desc;
    String price;
    String qty;
    String active;
}
