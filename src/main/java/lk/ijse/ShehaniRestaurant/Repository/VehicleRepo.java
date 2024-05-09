package lk.ijse.ShehaniRestaurant.Repository;

import lk.ijse.ShehaniRestaurant.DataBaseConnection.DbConnection;
import lk.ijse.ShehaniRestaurant.Model.Vehicle;
import lk.ijse.ShehaniRestaurant.Model.VehicleColour;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VehicleRepo {

    public static List<VehicleColour> GetColour() {
        List<VehicleColour> colourList = new ArrayList<>();

        colourList.add(VehicleColour.RED);
        colourList.add(VehicleColour.BLUE);
        colourList.add(VehicleColour.GREEN);
        colourList.add(VehicleColour.WHITE);

        return colourList;
    }

    public static boolean Save(Vehicle vehicle) throws SQLException {
        String colour = String.valueOf(vehicle.getColor());

        String sql = "INSERT INTO Vehicle VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,vehicle.getPlateNumber());
        pstm.setObject(2,vehicle.getType());
        pstm.setObject(3,colour);
        pstm.setObject(4,vehicle.getAvailability());
        pstm.setObject(5,vehicle.getLicenseDate());
        pstm.setObject(6,"No");

        return pstm.executeUpdate() > 0;
    }

    public static List<Vehicle> GetAll() throws SQLException {
        String sql = "SELECT * FROM Vehicle WHERE Deleted = 'No'";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        List<Vehicle> vehicleList = new ArrayList<>();

        while (resultSet.next()){
            String plateNumber = resultSet.getString(1);
            String type = resultSet.getString(2);
            VehicleColour colour = VehicleColour.valueOf(resultSet.getString(3));
            String availability = resultSet.getString(4);
            LocalDate licenseDate = LocalDate.parse(resultSet.getString(5));

            Vehicle vehicle = new Vehicle(plateNumber,type,colour,availability,licenseDate);
            vehicleList.add(vehicle);
        }
        return vehicleList;
    }

    public static Vehicle SearchByPlateNumber(String plateNumber) throws SQLException {
        String sql = "SELECT * FROM Vehicle WHERE PlateNumber = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,plateNumber);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String plateNum = resultSet.getString(1);
            String typ = resultSet.getString(2);
            VehicleColour colour = VehicleColour.valueOf(resultSet.getString(3));
            String availability = resultSet.getString(4);
            LocalDate lDate = LocalDate.parse(resultSet.getString(5));

            Vehicle vehicle = new Vehicle(plateNum,typ,colour,availability,lDate);

            return vehicle;
        }
        return null;
    }

    public static boolean Update(Vehicle vehicle) throws SQLException {
        String colour = String.valueOf(vehicle.getColor());
        String sql = "UPDATE Vehicle SET Type = ?, Color = ?, Availability = ?, LicenseDate = ?, Deleted = 'No' WHERE PlateNumber = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,vehicle.getType());
        pstm.setObject(2,colour);
        pstm.setObject(3,vehicle.getAvailability());
        pstm.setObject(4,vehicle.getLicenseDate());
        pstm.setObject(5,vehicle.getPlateNumber());

        return pstm.executeUpdate() > 0;
    }

    public static boolean Delete(String plateNumber) throws SQLException {
        String sql = "UPDATE Vehicle SET Deleted = 'Yes' WHERE PlateNumber = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,plateNumber);

        return pstm.executeUpdate() > 0;
    }

    public static List<String> GetPlateNum() throws SQLException {
        String sql = "SELECT PlateNumber FROM Vehicle WHERE Availability = 'Yes' AND  Deleted = 'No'";
        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        ResultSet resultSet= pstm.executeQuery();

        List<String> PlatenumberList = new ArrayList<>();
        while (resultSet.next()) {
            PlatenumberList.add(resultSet.getString(1));
        }
        return PlatenumberList;
    }

    public static boolean Update(String plateNumber) throws SQLException {
        String sql = "UPDATE Vehicle SET Availability = 'No' WHERE PlateNumber = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);
        pstm.setObject(1,plateNumber);

        return pstm.executeUpdate() > 0;
    }
}
