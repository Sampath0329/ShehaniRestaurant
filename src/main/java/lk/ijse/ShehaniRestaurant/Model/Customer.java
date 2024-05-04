package lk.ijse.ShehaniRestaurant.Model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode

public class Customer {
    private String id;
    private String name;
    private String NIC;
    private String address;
    private String tel;
    private String username;



    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", NIC='" + NIC + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", username='" + username + '\'' +
                '}';
    }


}
