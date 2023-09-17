package lk.ijse.jsp.bo.custom;

import lk.ijse.jsp.bo.SuperBO;
import lk.ijse.jsp.dto.CustomerDTO;
import lk.ijse.jsp.dto.ItemDTO;
import lk.ijse.jsp.dto.OrdersDTO;
import lk.ijse.jsp.dto.PlaceOrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseOrderBO extends SuperBO {
    boolean purchaseOrder(PlaceOrderDTO dto) throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException;

    boolean checkItemIsAvailable(String code) throws SQLException, ClassNotFoundException;

    boolean checkCustomerIsAvailable(String id) throws SQLException, ClassNotFoundException;

    String generateNewOrderID() throws SQLException, ClassNotFoundException;

    ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

}