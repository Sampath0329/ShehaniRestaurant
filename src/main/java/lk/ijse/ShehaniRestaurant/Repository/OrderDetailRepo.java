package lk.ijse.ShehaniRestaurant.Repository;


import lk.ijse.ShehaniRestaurant.DataBaseConnection.DbConnection;
import lk.ijse.ShehaniRestaurant.Model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailRepo {
    public static boolean Save(List<OrderDetail> orderDetailList) throws SQLException {
        for (OrderDetail orderDetail : orderDetailList) {
            if ('F' != orderDetail.getFoodId().charAt(0)){
                boolean isSaved = FoodItemsave(orderDetail);
                if(!isSaved) {
                    return false;
                }
            } else {
                boolean isSaved = BearItemsave(orderDetail);
                if(!isSaved) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean BearItemsave(OrderDetail orderDetail) throws SQLException {
        String sql = "INSERT INTO OrderFoodItemDetail VALUES(?, ?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getConnection()
                .prepareStatement(sql);

        pstm.setString(1, orderDetail.getOrderId());
        pstm.setString(2, orderDetail.getFoodId());
        pstm.setString(3, null);
        pstm.setInt(4, orderDetail.getQty());
        pstm.setDouble(5, orderDetail.getUnitPrice());

        return pstm.executeUpdate() > 0;    //false ->  |

    }

    private static boolean FoodItemsave(OrderDetail orderDetail) throws SQLException {
        String sql = "INSERT INTO OrderFoodItemDetail VALUES(?, ?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getConnection()
                .prepareStatement(sql);

        pstm.setString(1, orderDetail.getOrderId());
        pstm.setString(2, null);
        pstm.setString(3, orderDetail.getFoodId());
        pstm.setInt(4, orderDetail.getQty());
        pstm.setDouble(5, orderDetail.getUnitPrice());

        return pstm.executeUpdate() > 0;    //false ->  |

    }


}
