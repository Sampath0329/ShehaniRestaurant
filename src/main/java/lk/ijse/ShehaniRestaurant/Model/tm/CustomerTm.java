package lk.ijse.ShehaniRestaurant.Model.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode

public class CustomerTm {

    private String id;
    private String name;
    private String NIC;
    private String address;
    private String tel;
    private String username;

}
