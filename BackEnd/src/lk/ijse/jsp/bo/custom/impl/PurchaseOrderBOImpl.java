package lk.ijse.jsp.bo.custom.impl;

import lk.ijse.jsp.bo.custom.PurchaseOrderBO;
import lk.ijse.jsp.dao.DAOFactory;
import lk.ijse.jsp.dao.custom.ItemDAO;
import lk.ijse.jsp.dao.custom.OrderDetailsDAO;
import lk.ijse.jsp.dao.custom.OrdersDAO;
import lk.ijse.jsp.dto.CartDTO;
import lk.ijse.jsp.dto.CustomerDTO;
import lk.ijse.jsp.dto.ItemDTO;
import lk.ijse.jsp.dto.PlaceOrderDTO;
import lk.ijse.jsp.entity.CustomEntity;
import lk.ijse.jsp.entity.Item;
import lk.ijse.jsp.entity.OrderDetails;
import lk.ijse.jsp.listener.MyListener;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {
    OrdersDAO ordersDAO = (OrdersDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    @Override
    public boolean purchaseOrder(PlaceOrderDTO dto) {
        // Transaction
        try (Connection connection = MyListener.pool.getConnection();) {
            connection.setAutoCommit(false);

            CustomEntity orderDetail = new CustomEntity();
            orderDetail.setOrderID(dto.getOrderID());
            orderDetail.setDate(dto.getDate());
            orderDetail.setCusID(dto.getCusID());
            orderDetail.setDiscount(dto.getDiscount());
            orderDetail.setTotal(dto.getTotal());

            // Save order
            boolean isOrderSaved = ordersDAO.save(orderDetail);

            if (isOrderSaved) {
                connection.rollback();
                return false;
            }

            ArrayList<CartDTO> cartArray = dto.getCartArray();
            for (CartDTO c : cartArray) {
                ItemDTO itemDTO = c.getItem();
                String cartItemCode = itemDTO.getCode();
                int cartQty = c.getQty();

                // save order details
                OrderDetails orderDetails = new OrderDetails(dto.getOrderID(), cartItemCode, cartQty);
                boolean isOrderDetailsSaved = orderDetailsDAO.save(orderDetails);
                if (isOrderDetailsSaved) {
                    connection.rollback();
                    return false;
                }

                // update item
                Item item = new Item();
                item.setQty(cartQty);
                item.setCode(cartItemCode);
                boolean isItemUpdated = itemDAO.update2(item);
                if (isItemUpdated) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } catch (Exception e) {
            System.out.println("Connection failed" + e);
            return false;
        }
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean checkItemIsAvailable(String code) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean checkCustomerIsAvailable(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewOrderID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        return null;
    }
}
