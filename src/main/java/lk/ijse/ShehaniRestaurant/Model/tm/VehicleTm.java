package lk.ijse.ShehaniRestaurant.Model.tm;

import lk.ijse.ShehaniRestaurant.Model.VehicleColour;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor

public class VehicleTm {
    private String PlateNumber;
    private String Type;
    private VehicleColour Color;
    private String Availability;
    private LocalDate LicenseDate;
}
