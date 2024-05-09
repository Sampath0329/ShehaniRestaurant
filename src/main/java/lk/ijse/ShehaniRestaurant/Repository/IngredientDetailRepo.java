package lk.ijse.ShehaniRestaurant.Repository;

import lk.ijse.ShehaniRestaurant.DataBaseConnection.DbConnection;
import lk.ijse.ShehaniRestaurant.Model.IngredientDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class IngredientDetailRepo {
    public static boolean Save(IngredientDetail ingredientDetail) throws SQLException {
        String sql = "INSERT INTO SupplierIngredientDetail VALUES (?, ?, ?)";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,ingredientDetail.getSupplierId());
        pstm.setObject(2,ingredientDetail.getIngredientId());
        pstm.setObject(3,ingredientDetail.getDate());

        return pstm.executeUpdate() > 0;
    }

    public static String GetSupplierId(String ingredientId) throws SQLException {
        String sql = "SELECT SupplierId FROM SupplierIngredientDetail WHERE IngredientId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);
        pstm.setObject(1,ingredientId);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return "S9999";
    }

    public static IngredientDetail GetSupplier(String id) throws SQLException {
        String sql = "SELECT * FROM SupplierIngredientDetail WHERE IngredientId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);
        pstm.setObject(1,id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String supId = resultSet.getString(1);
            String ingId = resultSet.getString(2);
            LocalDate date = LocalDate.parse(resultSet.getString(3));

            IngredientDetail ingredientDetail = new IngredientDetail(supId,ingId,date);

            return ingredientDetail;
        }
        return null;
    }

    public static boolean Update(IngredientDetail ingredientDetail) throws SQLException {
        String sql = "UPDATE SupplierIngredientDetail SET SupplierId = ? WHERE IngredientId = ?";

        PreparedStatement pstm =DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,ingredientDetail.getSupplierId());
        pstm.setObject(2,ingredientDetail.getIngredientId());


        return pstm.executeUpdate() > 0;
    }
}
