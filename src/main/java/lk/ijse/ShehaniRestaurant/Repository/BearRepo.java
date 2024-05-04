package lk.ijse.ShehaniRestaurant.Repository;

import lk.ijse.ShehaniRestaurant.DataBaseConnection.DbConnection;
import lk.ijse.ShehaniRestaurant.Model.Bear;
import lk.ijse.ShehaniRestaurant.Model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BearRepo {
    public static Boolean Save(Bear bear) throws SQLException {

        String sql = "INSERT INTO AlcoholFoodItem VALUES (?, ?, ?, ?, ? , ?)";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,bear.getId());
        pstm.setObject(2,bear.getName());
        pstm.setObject(3,bear.getPrice());
        pstm.setObject(4,bear.getAvailable());
        pstm.setObject(5,bear.getAlcoholContent());
        pstm.setObject(6,bear.getQty());

        return pstm.executeUpdate() > 0;
    }

    public static List<Bear> GetAll() throws SQLException {
        String sql = "SELECT * FROM AlcoholFoodItem WHERE Availability = 'Yes' ";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<Bear> bearList = new ArrayList<>();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String price = resultSet.getString(3);
            String availability = resultSet.getString(4);
            String alcoholContent = resultSet.getString(5);
            String qty = resultSet.getString(6);

            bearList.add(new Bear(id,name,price,availability,alcoholContent,qty));


        }
        return bearList;
    }

    public static Bear SearchById(String id) throws SQLException {
        String sql =  "SELECT * FROM AlcoholFoodItem WHERE BearId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String bid = resultSet.getString(1);
            String name = resultSet.getString(2);
            String price = resultSet.getString(3);
            String availability = resultSet.getString(4);
            String alcoholCon = resultSet.getString(5);
            String qty = resultSet.getString(6);

            Bear bear = new Bear(bid,name, price,availability,alcoholCon,qty);

            return bear;
        }
        return null;
    }

    public static Boolean Update(Bear bear) throws SQLException {
        String sql = "UPDATE AlcoholFoodItem SET Name = ?, Price = ?, Availability = ? ,AlcoholContent = ?, AvailableQty = ? WHERE BearId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);


        pstm.setObject(1,bear.getName());
        pstm.setObject(2,bear.getPrice());
        pstm.setObject(3,bear.getAvailable());
        pstm.setObject(4,bear.getAlcoholContent());
        pstm.setObject(5,bear.getQty());
        pstm.setObject(6,bear.getId());

        return pstm.executeUpdate() > 0;
    }

    public static Boolean Delete(String id) throws SQLException {
        String sql = "UPDATE AlcoholFoodItem SET Availability = ? WHERE BearId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);
        pstm.setObject(1,"Delete");
        pstm.setObject(2,id);

        return pstm.executeUpdate() > 0;
    }

    public static List<String> getIds() throws SQLException {

        String sql = "SELECT BearId FROM AlcoholFoodItem WHERE AvailableQty > 0 AND  Availability = 'Yes'";
        ResultSet resultSet = DbConnection.getConnection().prepareStatement(sql)
                .executeQuery();

        List<String> bearId = new ArrayList<>();
        while (resultSet.next()) {
            bearId.add(resultSet.getString(1));
        }
        return bearId;
    }

    public static boolean Update(OrderDetail orderDetail) throws SQLException {
        String sql = "UPDATE AlcoholFoodItem SET AvailableQty = AvailableQty - ? WHERE BearId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);


        pstm.setObject(1,orderDetail.getQty());
        pstm.setObject(2,orderDetail.getFoodId());

        return pstm.executeUpdate() > 0;

    }
}
