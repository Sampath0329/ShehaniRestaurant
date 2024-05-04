package lk.ijse.ShehaniRestaurant.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class FoodItem {
    String id;
    String name;
    String desc;
    String price;
    String qty;
    String active;
}
