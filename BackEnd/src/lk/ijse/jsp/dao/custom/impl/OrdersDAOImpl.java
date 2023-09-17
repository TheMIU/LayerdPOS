package lk.ijse.jsp.dao.custom.impl;

import lk.ijse.jsp.dao.custom.OrdersDAO;
import lk.ijse.jsp.entity.CustomEntity;
import lk.ijse.jsp.listener.MyListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersDAOImpl implements OrdersDAO {

    @Override
    public ArrayList<CustomEntity> getAll() {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("SELECT\n" +
                    "    o.orderID,\n" +
                    "    o.date,\n" +
                    "    o.customerID,\n" +
                    "    GROUP_CONCAT(oi.itemID ORDER BY oi.itemID) AS itemsIDs,\n" +
                    "    o.discount,\n" +
                    "    o.total\n" +
                    "FROM orders o\n" +
                    "         JOIN order_items oi ON o.orderID = oi.orderID\n" +
                    "GROUP BY o.orderID;");
            ResultSet rst = pstm.executeQuery();

            ArrayList<CustomEntity> allOrders = new ArrayList<>();
            while (rst.next()) {
                String orderID = rst.getString(1);
                String date = rst.getString(2);
                String customerID = rst.getString(3);
                String itemsIDs = rst.getString(4);
                String discount = rst.getString(5);
                String total = rst.getString(6);

                CustomEntity orderDetail = new CustomEntity();
                orderDetail.setOrderID(orderID);
                orderDetail.setDate(date);
                orderDetail.setCusID(customerID);
                orderDetail.setItemID(itemsIDs);
                orderDetail.setDiscount(Integer.parseInt(discount));
                orderDetail.setTotal(Double.parseDouble(total));

                allOrders.add(orderDetail);
            }

            return allOrders;

        } catch (SQLException e) {
            System.out.println("Connection failed");
            return null;
        }
    }

    @Override
    public boolean save(CustomEntity dto) {
        System.out.println(dto);

        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO orders (orderID, date, customerID, discount, total) VALUES (?, ?, ?, ?, ?)");
            pstm.setString(1, dto.getOrderID());
            pstm.setString(2, dto.getDate());
            pstm.setString(3, dto.getCusID());
            pstm.setInt(4, dto.getDiscount());
            pstm.setDouble(5, dto.getTotal());

            if (pstm.executeUpdate() <= 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Connection failed"+e);
            return false;
        }
    }

    @Override
    public boolean update(CustomEntity dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update2(CustomEntity dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public CustomEntity search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }
}
