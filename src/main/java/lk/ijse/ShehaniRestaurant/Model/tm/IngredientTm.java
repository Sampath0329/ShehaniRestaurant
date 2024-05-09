package lk.ijse.ShehaniRestaurant.Model.tm;

import lk.ijse.ShehaniRestaurant.Model.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientTm  {
    private String ingredientId;
    private String name;
    private LocalDate EXP_Date;
    private int qty;
    private String unit;
    private double price;
    private String supplierId;
}
