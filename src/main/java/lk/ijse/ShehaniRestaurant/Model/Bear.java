package lk.ijse.ShehaniRestaurant.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Bear {
    private String id ;
    private String name;
    private String price;
    private String available;
    private String alcoholContent;
    private String qty;

}
