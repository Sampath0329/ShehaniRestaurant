package lk.ijse.ShehaniRestaurant.DataBaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static Connection connection;

    private DbConnection(){

    }

    static {
        String localHost = "jdbc:mysql://localhost:3306/ShehaniRestaurant";
        String Uname = "root";
        String pw = "Ijse@123";

        try {
            connection = DriverManager.getConnection(localHost,Uname,pw);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
