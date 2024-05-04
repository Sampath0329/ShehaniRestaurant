package lk.ijse.ShehaniRestaurant.Model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class BearTm {
    private String id ;
    private String name;
    private String price;
    private String available;
    private String alcoholContent;
    private String qty;

}
