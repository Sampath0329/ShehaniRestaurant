package lk.ijse.ShehaniRestaurant.Repository;

import lk.ijse.ShehaniRestaurant.DataBaseConnection.DbConnection;
import lk.ijse.ShehaniRestaurant.Model.FullTimeEmployee;
import lk.ijse.ShehaniRestaurant.Model.PartTimeEmployee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartTimeEmployeeRepo {
    public static Boolean Save(PartTimeEmployee partTimeEmployee) throws SQLException {
        String sql = "INSERT INTO PartTimeEmployee VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,partTimeEmployee.getPartTimeEmployeeId());
        pstm.setObject(2,partTimeEmployee.getName());
        pstm.setObject(3,partTimeEmployee.getAddress());
        pstm.setObject(4,partTimeEmployee.getContact());
        pstm.setObject(5,partTimeEmployee.getWorkingHour());
        pstm.setObject(6,partTimeEmployee.getPerHourSalary());
        pstm.setObject(7,partTimeEmployee.getHireDate());
        pstm.setObject(8,partTimeEmployee.getUserId());
        pstm.setObject(9,partTimeEmployee.getActive());

        return pstm.executeUpdate() > 0;
    }

    public static List<PartTimeEmployee> GetAll() throws SQLException {
        String sql = "SELECT * FROM PartTimeEmployee WHERE Active = 'Active' ";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<PartTimeEmployee> partTimeEmployeeList = new ArrayList<>();

        while (resultSet.next()){
            String eId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);
            String workingHour = resultSet.getString(5);
            String perHourSalary = resultSet.getString(6);
            String hireDate = resultSet.getString(7);
            String userId = resultSet.getString(8);
            String active = resultSet.getString(9);

            partTimeEmployeeList.add(new PartTimeEmployee(eId,name, address, tel, workingHour, perHourSalary,hireDate,userId,active));


        }
        return partTimeEmployeeList;
    }

    public static PartTimeEmployee SearchById(String id) throws SQLException {
        String sql =  "SELECT * FROM PartTimeEmployee WHERE PartTimeEmployeeId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String eId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);
            String workingHour = resultSet.getString(5);
            String perHourSalary = resultSet.getString(6);
            String hireDate = resultSet.getString(7);
            String userId = resultSet.getString(8);
            String active = resultSet.getString(9);


            return new PartTimeEmployee(eId,name,address,tel,workingHour, perHourSalary,hireDate,userId,active);
        }
        return null;
    }

    public static Boolean Update(PartTimeEmployee partTimeEmployee) throws SQLException {
        String sql = "UPDATE PartTimeEmployee SET Name = ?, Address = ?, Contact = ? ,WorkingHour = ?,PerHourSalary = ?, HireDate = ? ,UserId =? , Active = ? WHERE PartTimeEmployeeId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,partTimeEmployee.getName());
        pstm.setObject(2,partTimeEmployee.getAddress());
        pstm.setObject(3,partTimeEmployee.getContact());
        pstm.setObject(4,partTimeEmployee.getWorkingHour());
        pstm.setObject(5,partTimeEmployee.getPerHourSalary());
        pstm.setObject(6,partTimeEmployee.getHireDate());
        pstm.setObject(7,partTimeEmployee.getUserId());
        pstm.setObject(8,partTimeEmployee.getActive());
        pstm.setObject(9,partTimeEmployee.getPartTimeEmployeeId());

        return pstm.executeUpdate() > 0;
    }

    public static Boolean Delete(String id) throws SQLException {
        String sql = "UPDATE PartTimeEmployee SET Active = ? WHERE PartTimeEmployeeId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);
        pstm.setObject(1,"Deactivate");
        pstm.setObject(2,id);

        return pstm.executeUpdate() > 0;
    }
}
