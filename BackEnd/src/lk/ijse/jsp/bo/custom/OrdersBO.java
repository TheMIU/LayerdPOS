package lk.ijse.jsp.bo.custom;

import lk.ijse.jsp.bo.SuperBO;
import lk.ijse.jsp.dto.CustomerDTO;
import lk.ijse.jsp.dto.OrdersDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrdersBO extends SuperBO {
    ArrayList<OrdersDTO> getAllOrders() throws SQLException, ClassNotFoundException;

    boolean saveOrder(OrdersDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateOrder(OrdersDTO dto) throws SQLException, ClassNotFoundException;

    boolean deleteOrder(String id) throws SQLException, ClassNotFoundException;
}
