package lk.ijse.ShehaniRestaurant.Repository;

import lk.ijse.ShehaniRestaurant.DataBaseConnection.DbConnection;
import lk.ijse.ShehaniRestaurant.Model.FullTimeEmployee;
import lk.ijse.ShehaniRestaurant.Model.tm.FullTimeEmployeTm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FullTimeEmployeeRepo {

    public static Boolean Save(FullTimeEmployee fullTimeEmployee) throws SQLException {
        String sql = "INSERT INTO FullTimeEmployee VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,fullTimeEmployee.getFullTimeEmployeeId());
        pstm.setObject(2,fullTimeEmployee.getName());
        pstm.setObject(3,fullTimeEmployee.getAddress());
        pstm.setObject(4,fullTimeEmployee.getContact());
        pstm.setObject(5,fullTimeEmployee.getFixedSalary());
        pstm.setObject(6,fullTimeEmployee.getHireDate());
        pstm.setObject(7,fullTimeEmployee.getUserId());
        pstm.setObject(8,fullTimeEmployee.getActive());

        return pstm.executeUpdate() > 0;

    }

    public static FullTimeEmployee SearchById(String id) throws SQLException {
        String sql =  "SELECT * FROM FullTimeEmployee WHERE FullTimeEmployeeId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String eId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);
            String salary = resultSet.getString(5);
            String hireDate = resultSet.getString(6);
            String userId = resultSet.getString(7);
            String active = resultSet.getString(8);

            return new FullTimeEmployee(eId,name,address,tel,salary,hireDate,userId,active);
        }
        return null;
    }

    public static Boolean Update(FullTimeEmployee fullTimeEmployee) throws SQLException {
        String sql = "UPDATE FullTimeEmployee SET Name = ?, Address = ?, Contact = ? ,FixedSalary = ?, HireDate = ? ,UserId =? , Active = ? WHERE FullTimeEmployeeId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,fullTimeEmployee.getName());
        pstm.setObject(2,fullTimeEmployee.getAddress());
        pstm.setObject(3,fullTimeEmployee.getContact());
        pstm.setObject(4,fullTimeEmployee.getFixedSalary());
        pstm.setObject(5,fullTimeEmployee.getHireDate());
        pstm.setObject(6,fullTimeEmployee.getUserId());
        pstm.setObject(7,fullTimeEmployee.getActive());
        pstm.setObject(8,fullTimeEmployee.getFullTimeEmployeeId());

        return pstm.executeUpdate() > 0;
    }

    public static List<FullTimeEmployee> GetAll() throws SQLException {
        String sql = "SELECT * FROM FullTimeEmployee WHERE Active = 'Active' ";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<FullTimeEmployee> fullTimeEmployeeList = new ArrayList<>();

        while (resultSet.next()){
            String eId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);
            String salary = resultSet.getString(5);
            String hireDate = resultSet.getString(6);
            String userId = resultSet.getString(7);
            String active = resultSet.getString(8);

            fullTimeEmployeeList.add(new FullTimeEmployee(eId,name, address, tel,salary,hireDate,userId,active));


        }
        return fullTimeEmployeeList;
    }

    public static Boolean Delete(String id) throws SQLException {
        String sql = "UPDATE FullTimeEmployee SET Active = ? WHERE FullTimeEmployeeId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);
        pstm.setObject(1,"Deactivate");
        pstm.setObject(2,id);

        return pstm.executeUpdate() > 0;
    }
}
