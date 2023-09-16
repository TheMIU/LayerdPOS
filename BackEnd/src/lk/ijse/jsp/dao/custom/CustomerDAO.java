package lk.ijse.jsp.dao.custom;

import lk.ijse.jsp.dao.CrudDAO;
import lk.ijse.jsp.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer,String> {
    public ArrayList<Customer> getAllCustomersByAddress(String address)throws ClassNotFoundException, SQLException;
}
