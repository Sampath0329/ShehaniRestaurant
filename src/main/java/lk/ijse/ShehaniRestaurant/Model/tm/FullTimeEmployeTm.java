package lk.ijse.ShehaniRestaurant.Model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class FullTimeEmployeTm {
    private String FullTimeEmployeeId;
    private  String Name;
    private  String Address;
    private  String Contact;
    private  String FixedSalary;
    private  String HireDate;
    private  String UserId;
    private  String Active;
}
