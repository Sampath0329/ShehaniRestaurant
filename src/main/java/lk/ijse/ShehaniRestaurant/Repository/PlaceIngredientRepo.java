package lk.ijse.ShehaniRestaurant.Repository;

import javafx.scene.control.Alert;
import lk.ijse.ShehaniRestaurant.DataBaseConnection.DbConnection;
import lk.ijse.ShehaniRestaurant.Model.Ingredient;
import lk.ijse.ShehaniRestaurant.Model.IngredientDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceIngredientRepo {

    public static boolean PlaceIngredient(Ingredient ingredient, IngredientDetail ingredientDetail) throws SQLException {
        Connection connection = DbConnection.getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isSaved = IngredientRepo.Save(ingredient);
            if (isSaved){
                boolean isDetailSaved = IngredientDetailRepo.Save(ingredientDetail);
                if (isDetailSaved){
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public static boolean UpdateIngredient(Ingredient ingredient, IngredientDetail ingredientDetail) throws SQLException {
        Connection connection = DbConnection.getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isUpdated = IngredientRepo.Update(ingredient);
            if (isUpdated){
                boolean isDetailUpdated = IngredientDetailRepo.Update(ingredientDetail);
                if (isDetailUpdated){
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
