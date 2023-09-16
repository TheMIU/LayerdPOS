package lk.ijse.jsp.bo.custom.impl;

import lk.ijse.jsp.bo.custom.PurchaseOrderBO;
import lk.ijse.jsp.dto.CustomerDTO;
import lk.ijse.jsp.dto.ItemDTO;
import lk.ijse.jsp.dto.OrdersDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {
    @Override
    public boolean purchaseOrder(OrdersDTO dto) throws SQLException, ClassNotFoundException {
        return false;
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
