package lk.ijse.ShehaniRestaurant.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class FullTimeEmployee {
    private String FullTimeEmployeeId;
    private  String Name;
    private  String Address;
    private  String Contact;
    private  String FixedSalary;
    private  String HireDate;
    private  String UserId;
    private  String Active;

}
