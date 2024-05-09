package lk.ijse.ShehaniRestaurant.Repository;

import lk.ijse.ShehaniRestaurant.DataBaseConnection.DbConnection;
import lk.ijse.ShehaniRestaurant.Model.Ingredient;
import lk.ijse.ShehaniRestaurant.Model.Supplier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IngredientRepo {

    public static String GetCurrentId() throws SQLException {
        String sql = "SELECT IngredientId FROM Ingredient ORDER BY IngredientId DESC LIMIT 1";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String ingredientId = resultSet.getString(1);

            return ingredientId;
        }
        return null;
    }

    public static boolean Save(Ingredient ingredient) throws SQLException {
        String sql = "INSERT INTO Ingredient VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,ingredient.getIngredientId());
        pstm.setObject(2,ingredient.getName());
        pstm.setObject(3,ingredient.getEXP_Date());
        pstm.setObject(4,ingredient.getQty());
        pstm.setObject(5,ingredient.getUnit());
        pstm.setObject(6,ingredient.getPrice());
        pstm.setObject(7,"No");

        return pstm.executeUpdate() > 0;
    }

    public static List<Ingredient> GetAll() throws SQLException {
        List<Ingredient> ingredientList = new ArrayList<>();
        String sql = "SELECT * FROM Ingredient WHERE Deleted = 'No'";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String ingredientId = resultSet.getString(1);
            String name = resultSet.getString(2);
            LocalDate date = LocalDate.parse(resultSet.getString(3));
            int qty = Integer.parseInt(resultSet.getString(4));
            String unit = resultSet.getString(5);
            double price = Double.parseDouble(resultSet.getString(6));

            Ingredient ingredient = new Ingredient(ingredientId,name,date,qty,unit,price);

            ingredientList.add(ingredient);
        }
        return ingredientList;
    }

    public static Ingredient SearchByIngredientId(String ingredientId) throws SQLException {
        String sql = "SELECT * FROM Ingredient WHERE IngredientId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,ingredientId);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
             String id = resultSet.getString(1);
             String name = resultSet.getString(2);
             LocalDate EXP_Date = LocalDate.parse(resultSet.getString(3));
             int qty = Integer.parseInt(resultSet.getString(4));
             String unit = resultSet.getString(5);
             double price = Double.parseDouble(resultSet.getString(6));

             Ingredient ingredient = new Ingredient(id,name,EXP_Date,qty,unit,price);

             return ingredient;
        }
        return null;
    }

    public static boolean Update(Ingredient ingredient) throws SQLException {
        String sql = "UPDATE Ingredient SET Name = ?, EXP_Date = ?, Quantity = ?, Unit = ?, UnitPrice = ?, Deleted = 'No' WHERE IngredientId = ?";

        PreparedStatement pstm =DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,ingredient.getName());
        pstm.setObject(2,ingredient.getEXP_Date());
        pstm.setObject(3,ingredient.getQty());
        pstm.setObject(4,ingredient.getUnit());
        pstm.setObject(5,ingredient.getPrice());
        pstm.setObject(6,ingredient.getIngredientId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean Delete(String ingredientId) throws SQLException {
        String sql = "UPDATE Ingredient SET Deleted = 'Yes' WHERE IngredientId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,ingredientId);

        return pstm.executeUpdate() > 0;
    }
}
