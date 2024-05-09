package lk.ijse.ShehaniRestaurant.Util;

import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFieldValid(lk.ijse.ShehaniRestaurant.Util.TextField textField, String text){
//        System.out.println(textField);
        String filed = "";

        switch (textField){
            case Name -> filed = "([a-zA-Z\\s]+){3,}" ;
            case NIC -> filed = "(^[0-9]{9}[x|X|v|V]|[0-9]{12})$";
            case Address -> filed = "^([A-z0-9]|[-/,. @+]|\\\\s){4,}$";
            case Contact -> filed = "^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$";
        }
        Pattern pattern = Pattern.compile(filed);


        if (text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;
    }


    public static boolean setTextColor(lk.ijse.ShehaniRestaurant.Util.TextField location, TextField textField) {
//        System.out.println(location);
        if (Regex.isTextFieldValid(location, textField.getText())) {
            textField.setStyle("-fx-text-fill: green; -fx-border-color: green;");

            return true;
        } else {
            textField.setStyle("-fx-text-fill: red; -fx-border-color: red;");
            return false;
        }
    }


}
