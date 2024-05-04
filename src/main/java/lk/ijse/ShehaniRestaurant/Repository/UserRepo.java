package lk.ijse.ShehaniRestaurant.Repository;

import lk.ijse.ShehaniRestaurant.DataBaseConnection.DbConnection;
import lk.ijse.ShehaniRestaurant.Model.Customer;
import lk.ijse.ShehaniRestaurant.Model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepo {

    public static Boolean Saved(User user) throws SQLException {

        String sql = "INSERT INTO User VALUE (?, ?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);
        pstm.setObject(1,user.getId());
        pstm.setObject(2,user.getName());
        pstm.setObject(3,user.getPw());
        pstm.setObject(4,user.getNIC());
        pstm.setObject(5,"Active");


        return pstm.executeUpdate() > 0;
    }

    public static User SearchById(String id) throws SQLException {
        String sql =  "SELECT * FROM User WHERE UserId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String UserId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String Pw = resultSet.getString(3);
            String NIC = resultSet.getString(4);
            String Active = resultSet.getString(5);

            User user = new User(UserId,name,Pw,NIC,Active);

            return user;
        }
        return null;
    }

    public static Boolean update(User user) throws SQLException {
        String sql = "UPDATE User SET Name = ?, Password = ?, NIC = ? , Active = ? WHERE UserId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,user.getName());
        pstm.setObject(2,user.getPw());
        pstm.setObject(3,user.getNIC());
        pstm.setObject(4,user.getActive());
        pstm.setObject(5,user.getId());


        return pstm.executeUpdate() > 0;
    }

    public static List<User> getAll() throws SQLException {
        String sql = "SELECT * FROM User WHERE Active = 'Active' ";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<User> userList = new ArrayList<>();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String pw = resultSet.getString(3);
            String NIC = resultSet.getString(4);
            String active = resultSet.getString(5);

            userList.add(new User(id,name,pw,NIC,active));


        }
        return userList;
    }

    public static Boolean Delete(String id) throws SQLException {
        String sql = "UPDATE User SET Active = ? WHERE UserId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);
        pstm.setObject(1,"Deactivate");
        pstm.setObject(2,id);

        return pstm.executeUpdate() > 0;
    }
}
