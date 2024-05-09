package lk.ijse.ShehaniRestaurant.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDate;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Vehicle {
    private String PlateNumber;
    private String Type;
    private VehicleColour Color;
    private String Availability;
    private LocalDate LicenseDate;

}
