package lk.ijse.ShehaniRestaurant.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class PartTimeEmployee {
    private String PartTimeEmployeeId;
    private String Name;
    private String Address;
    private String Contact;
    private String WorkingHour;
    private String PerHourSalary;
    private String HireDate;
    private String UserId;
    private String Active;
}
