package lk.ijse.jsp.bo.custom.impl;

import lk.ijse.jsp.bo.custom.OrdersBO;
import lk.ijse.jsp.dao.DAOFactory;
import lk.ijse.jsp.dao.custom.OrdersDAO;
import lk.ijse.jsp.dto.OrdersDTO;
import lk.ijse.jsp.entity.CustomEntity;
import lk.ijse.jsp.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersBOImpl implements OrdersBO {
    OrdersDAO ordersDAO = (OrdersDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    @Override
    public ArrayList<OrdersDTO> getAllOrders() throws SQLException, ClassNotFoundException {
        ArrayList<CustomEntity> all = ordersDAO.getAll();
        ArrayList<OrdersDTO> ordersDTOs = new ArrayList<>();

        for (CustomEntity c : all) {
            OrdersDTO ordersDTO = new OrdersDTO(
                    c.getOrderID(),
                    c.getDate(),
                    c.getCusID(),
                    c.getItemID(),
                    c.getDiscount(),
                    c.getTotal()
            );
            ordersDTOs.add(ordersDTO);
        }

        return ordersDTOs;
    }

    @Override
    public boolean saveOrder(OrdersDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateOrder(OrdersDTO dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean deleteOrder(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
