package lk.ijse.ShehaniRestaurant.Repository;

import lk.ijse.ShehaniRestaurant.DataBaseConnection.DbConnection;
import lk.ijse.ShehaniRestaurant.Model.Supplier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepo {

    public static boolean Save(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO Supplier VALUES (?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,supplier.getId());
        pstm.setObject(2,supplier.getName());
        pstm.setObject(3,supplier.getTel());
        pstm.setObject(4,"Yes");

        return pstm.executeUpdate() > 0;
    }

    public static List<Supplier> GetAll() throws SQLException {
        String sql = "SELECT * FROM Supplier where Active = 'Yes'";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        List<Supplier> supList = new ArrayList<>();

        ResultSet resultSet =  pstm.executeQuery();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String tel = resultSet.getString(3);

            Supplier supplier = new Supplier(id,name,tel);
            supList.add(supplier);

        }
        return supList;
    }

    public static Supplier searchById(String id) throws SQLException {
        String sql = "SELECT * from Supplier where SupplierId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            return new Supplier(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3));
        }
        return null;
    }

    public static boolean Update(Supplier supplier) throws SQLException {
        String sql = "UPDATE Supplier SET Name = ? , Contact = ?, Active = ? WHERE SupplierId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,supplier.getName());
        pstm.setObject(2,supplier.getTel());
        pstm.setObject(3,"Yes");
        pstm.setObject(4,supplier.getId());

        return pstm.executeUpdate() > 0;
    }


    public static boolean Delete(String id) throws SQLException {
        String sql = "UPDATE Supplier SET Active = 'No' WHERE SupplierId = ?";

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT SupplierId FROM Supplier WHERE Active = 'Yes'";

        List<String> idList = new ArrayList<>();

        PreparedStatement pstm = DbConnection.getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }
}
