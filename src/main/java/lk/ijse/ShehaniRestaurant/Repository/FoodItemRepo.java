package lk.ijse.ShehaniRestaurant.Repository;

import lk.ijse.ShehaniRestaurant.DataBaseConnection.DbConnection;
import lk.ijse.ShehaniRestaurant.Model.FoodItem;
import lk.ijse.ShehaniRestaurant.Model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodItemRepo {

    public static Boolean Save(FoodItem foodItem) throws SQLException {
        String sql = "INSERT INTO NonAlcoholFoodItem VALUES (?, ?, ?, ?, ? , ?)";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,foodItem.getId());
        pstm.setObject(2,foodItem.getName());
        pstm.setObject(3,foodItem.getDesc());
        pstm.setObject(4,foodItem.getPrice());
        pstm.setObject(5,foodItem.getQty());
        pstm.setObject(6,foodItem.getActive());

        return pstm.executeUpdate() > 0;
    }

    public static List<FoodItem> GetAll() throws SQLException {
        String sql = "SELECT * FROM NonAlcoholFoodItem WHERE Active = 'Active' ";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<FoodItem> foodItemList = new ArrayList<>();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String desc = resultSet.getString(3);
            String price = resultSet.getString(4);
            String qty = resultSet.getString(5);
            String active = resultSet.getString(6);

            foodItemList.add(new FoodItem(id,name, desc, price,qty,active));


        }
        return foodItemList;
    }

    public static Boolean Delete(String id) throws SQLException {
         String sql = "UPDATE NonAlcoholFoodItem SET Active = ? WHERE FoodId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);
        pstm.setObject(1,"Deactivate");
        pstm.setObject(2,id);

        return pstm.executeUpdate() > 0;
    }

    public static Boolean Update(FoodItem foodItem) throws SQLException {
        String sql = "UPDATE NonAlcoholFoodItem SET Name = ?, Description = ?, Price = ? ,AvailabilityQty = ?, Active = ? WHERE FoodId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);


        pstm.setObject(1,foodItem.getName());
        pstm.setObject(2,foodItem.getDesc());
        pstm.setObject(3,foodItem.getPrice());
        pstm.setObject(4,foodItem.getQty());
        pstm.setObject(5,foodItem.getActive());
        pstm.setObject(6,foodItem.getId());

        return pstm.executeUpdate() > 0;

    }

    public static FoodItem SearchById(String id) throws SQLException {

        String sql =  "SELECT * FROM NonAlcoholFoodItem WHERE FoodId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String fid = resultSet.getString(1);
            String name = resultSet.getString(2);
            String deac = resultSet.getString(3);
            String price = resultSet.getString(4);
            String qty = resultSet.getString(5);
//            String Active = resultSet.getString(5);

            FoodItem foodItem = new FoodItem(fid,name,deac, price,qty,"Active");

            return foodItem;
        }
        return null;
    }

    public static List<String> GetIds() throws SQLException {

        String sql = "SELECT FoodId FROM NonAlcoholFoodItem WHERE AvailabilityQty > 0 AND  Active = 'Active'";
        ResultSet resultSet = DbConnection.getConnection().prepareStatement(sql)
                .executeQuery();

        List<String> foodId = new ArrayList<>();
        while (resultSet.next()) {
            foodId.add(resultSet.getString(1));
        }
        return foodId;
    }


    public static boolean CheckFoodItemOrBear(List<OrderDetail> orderDetailList) throws SQLException {
        for (OrderDetail orderDetail : orderDetailList){
            if ('F' == orderDetail.getFoodId().charAt(0)){
                boolean isFoodUpdated = Update(orderDetail);
                if (!isFoodUpdated){
                    return false;
                }
            } else {
                boolean isBearUpdated = BearRepo.Update(orderDetail);
                if (!isBearUpdated){
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean Update(OrderDetail orderDetail) throws SQLException {
        String sql = "UPDATE NonAlcoholFoodItem SET AvailabilityQty = AvailabilityQty - ? WHERE FoodId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);


        pstm.setObject(1,orderDetail.getQty());
        pstm.setObject(2,orderDetail.getFoodId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean isAvailable(String foodId, int qty) throws SQLException {
        String sql = "SELECT AvailabilityQty FROM NonAlcoholFoodItem WHERE FoodId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,foodId);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            int dbQty = Integer.parseInt(resultSet.getString(1));
            if (dbQty >= qty){
                return true;
            }
        }
        return false;
    }

    public static int getCurrentId() throws SQLException {
        String sql = "SELECT * FROM NonAlcoholFoodItem";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        int foodItemCount = 0;
        while (resultSet.next()){
            ++foodItemCount;
        }
        return foodItemCount;
    }
}
