package lk.ijse.ShehaniRestaurant.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class User {
    private String id;
    private String name;
    private String pw;
    private String NIC;
    private String active;

}
