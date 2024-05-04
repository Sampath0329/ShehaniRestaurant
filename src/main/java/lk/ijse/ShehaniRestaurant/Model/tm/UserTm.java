package lk.ijse.ShehaniRestaurant.Model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UserTm {

    private String id;
    private String name;
    private String pw;
    private String NIC;
    private String active;

}
