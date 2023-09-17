package lk.ijse.jsp.dao.custom.impl;

import lk.ijse.jsp.dao.custom.OrderDetailsDAO;
import lk.ijse.jsp.entity.OrderDetails;
import lk.ijse.jsp.listener.MyListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(OrderDetails dto) {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO order_items (orderID, itemID, qty) VALUES (?, ?, ?)");
            pstm.setString(1, dto.getOrderID());
            pstm.setString(2, dto.getItemID());
            pstm.setInt(3, dto.getQty());

            if (pstm.executeUpdate() <= 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Connection failed");
            return false;
        }
    }

    @Override
    public boolean update(OrderDetails dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update2(OrderDetails dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetails search(String s) throws SQLException, ClassNotFoundException {
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
