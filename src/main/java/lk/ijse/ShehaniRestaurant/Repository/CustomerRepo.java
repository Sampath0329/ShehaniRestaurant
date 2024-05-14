package lk.ijse.ShehaniRestaurant.Repository;


import lk.ijse.ShehaniRestaurant.DataBaseConnection.DbConnection;
import lk.ijse.ShehaniRestaurant.Model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {

    public static Boolean save(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customer VALUES (?, ?, ?, ?, ? , ?, ?)";

        Connection connection = DbConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,customer.getId());
        pstm.setObject(2,customer.getName());
        pstm.setObject(3,customer.getAddress());
        pstm.setObject(4,customer.getTel());
        pstm.setObject(5,customer.getUsername());
        pstm.setObject(6,customer.getNIC());
        pstm.setObject(7,"Yes");


        return pstm.executeUpdate() > 0;
    }

    public static List<Customer> getAll() throws SQLException {
        String sql = "SELECT * FROM Customer WHERE Active = 'Yes'";
        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Customer> cusList = new ArrayList<>();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);
            String userId = resultSet.getString(5);
            String NIC = resultSet.getString(6);

            Customer customer = new Customer(id,name,NIC,address,tel,userId);
            cusList.add(customer);
        }

        return cusList;
    }

    public static Customer searchById(String id) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE CustomerId = ?";

        PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement(sql);

        preparedStatement.setObject(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            String cusId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tel = resultSet.getString(4);
            String userId = resultSet.getString(5);
            String NIC = resultSet.getString(6);

            Customer customer = new Customer(cusId,name,NIC,address,tel,userId);

            return customer;
        }

        return null;
    }

    public static Boolean updated(Customer customer) throws SQLException {
        String sql = "UPDATE Customer SET Name = ?, Address = ?, Contact = ?, UserId = ?, NIC = ?, Active = 'Yes' WHERE CustomerId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,customer.getName());
        pstm.setObject(2,customer.getAddress());
        pstm.setObject(3,customer.getTel());
        pstm.setObject(4,customer.getUsername());
        pstm.setObject(5,customer.getNIC());
        pstm.setObject(6,customer.getId());

        return pstm.executeUpdate()>0;
    }

    public static Boolean Delete(String id) throws SQLException {
        String sql = "UPDATE Customer SET Active = 'No' WHERE CustomerId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;

    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT CustomerId FROM Customer WHERE Active = 'Yes'";
        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }

    public static List<String> GetCustomerTel() throws SQLException {
        String sql = "SELECT Contact FROM Customer WHERE Active = 'Yes'";
        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        List<String> telList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String tel = resultSet.getString(1);
            telList.add(tel);
        }
        return telList;
    }

    public static Customer GetCustomer(String tel) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE Contact = ? AND Active = 'Yes'";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,tel);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tbTel = resultSet.getString(4);
            String username = resultSet.getString(5);
            String NIC = resultSet.getString(6);

            Customer customer = new Customer(id,name,address,tbTel,username,NIC);

            return customer;
        }
        return null;
    }
    public static Customer GetCustomerIdAndName(String NIC) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE NIC = ? AND Active = 'Yes'";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,NIC);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String tbTel = resultSet.getString(4);
            String username = resultSet.getString(5);
            String dbNIC = resultSet.getString(6);

            Customer customer = new Customer(id,name,address,tbTel,username, dbNIC);

            return customer;
        }
        return null;
    }


    public static int GetCustomerCount() throws SQLException {
        String sql = "SELECT *  FROM Customer;";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        int customerCount = 0;
        while (resultSet.next()){
            ++customerCount;

        }
        return customerCount;
    }


    public static List<String> GetCustomerNIC() throws SQLException {
        String sql = "SELECT NIC FROM Customer WHERE Active = 'Yes'";
        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        List<String> NICList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String NIC = resultSet.getString(1);
            NICList.add(NIC);
        }
        return NICList;
    }


}
